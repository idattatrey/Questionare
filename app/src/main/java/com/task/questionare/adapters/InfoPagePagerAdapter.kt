package com.task.questionare.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.task.questionare.data.model.Data
import com.task.questionare.fragments.InfoFragment

class InfoPagePagerAdapter(
    fm: FragmentManager,
    data: List<Data>
) : FragmentStatePagerAdapter(fm) {
    var data: List<Data>? = null
    var listLength: Int? = null

    init {
        this.data = data
        this.listLength = data.size
    }

    override fun getItem(position: Int): Fragment {
        val infoFragment = InfoFragment()
        infoFragment.setInfoPosition(data!![position],listLength)
        return infoFragment
    }

    override fun getCount(): Int {
        return data!!.size
    }

}