package com.moree.countdownrecycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_countdown.view.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var mCountTimeIndex: Int = 0
    private var mCountTimeItem: HomeBean? = null
    var adapter: SimpleAdapter<HomeBean>? = null
    val targetTime = 1590744029
    private val homeBeans = initList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = object : SimpleAdapter<HomeBean>(this, R.layout.item_countdown) {
            override fun cBindViewHolder(holder: VH, data: HomeBean, position: Int) {
                holder.itemView.tv_.text = data.text
            }
        }
        rv_home.adapter = adapter
        adapter?.setData(homeBeans)
        initCountTime()
    }

    private fun initCountTime() {
        //60秒倒计时
        val countDownTimer = object : CountDownTimer(60000L, 1000L) {
            override fun onFinish() {
                Log.d("倒计时", "倒计时结束")
                mCountTimeItem?.text = "抱歉，活动结束了"
                adapter?.notifyItemChanged(mCountTimeIndex)
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d("倒计时", "onTick${millisUntilFinished / 1000}")
                mCountTimeItem?.text = "还有${millisUntilFinished / 1000}结束"
                adapter?.notifyItemChanged(mCountTimeIndex)
            }
        }
        countDownTimer.start()
    }

    private fun initList(): ArrayList<HomeBean> {
        val list = arrayListOf<HomeBean>()
        for (index in 0..20) {
            if (index == 10) {//第十个Item 为倒计时条目
                val element = HomeBean("倒计时")
                this.mCountTimeItem = element
                this.mCountTimeIndex = index
                list.add(element)
            } else {
                list.add(HomeBean("样式${index}"))
            }
        }
        return list
    }

    data class HomeBean(var text: String)
}

