package com.task.questionare

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.task.questionare.adapters.InfoPagePagerAdapter
import com.task.questionare.data.model.CardInfo
import com.task.questionare.data.model.Data
import com.task.questionare.data.rest.InfoService
import com.task.questionare.interfaces.UpdateMainUI
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), UpdateMainUI {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service = retrofit.create(InfoService::class.java)

    private var dataLength = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadCards()

        swiperefresh.setOnRefreshListener {
            loadCards()
        }
    }

    /*
        loadCards() method will receive the data from the api call
    */
    @SuppressLint("CheckResult")
    private fun loadCards() {
        getCards()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ cardInfo ->
                displayCards(cardInfo.data)
                dataLength = cardInfo.data.size
            }, { error ->
                Toast.makeText(applicationContext, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
    }

    /*
        displayCards() method will create cards and display accordingly
    */
    private fun displayCards(data: List<Data>) {
        val fragmentAdapter = InfoPagePagerAdapter(supportFragmentManager, data)
        viewpager_main.adapter = fragmentAdapter
    }


    /*
        getCards() method will call api to get the data
    */
    private fun getCards(): Single<CardInfo> {

        return Single.create { observer ->

            service.getPosts().enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    swiperefresh.isRefreshing = false
                    val cardInfo = Gson().fromJson(
                        response.body()!!.string().substring(1),
                        CardInfo::class.java
                    )
                    observer.onSuccess(cardInfo)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    observer.onError(t)
                }
            })

        }
    }

    /*
           updateUi() method will update the progress bar based on the progress.
    */
    override fun updateUi(cN: Int) {
        determinateBar!!.progress = cN
    }

}

