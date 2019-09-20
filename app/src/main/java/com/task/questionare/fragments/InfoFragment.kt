package com.task.questionare.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.task.questionare.MainActivity
import com.task.questionare.R
import com.task.questionare.data.model.Data


class InfoFragment : Fragment() {
    private var position: Data? = null
    private var iTv: TextView? = null
    private var iD: TextView? = null
    private var listLength: Int? = null

    fun setInfoPosition(p: Data, listLength: Int?) {
        this.position = p
        this.listLength = listLength
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        iTv = view.findViewById(R.id.infoTextView)
        iD = view.findViewById(R.id.cardCumber)

        iD!!.text = "# " + position!!.id
        iTv!!.text = position!!.text

        (activity as MainActivity).updateUi(((position!!.id.toDouble() / listLength!!.toDouble()) * 100).toInt())

        return view
    }

}
