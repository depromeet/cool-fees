package com.depromeet.tmj.cool_fees.features.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.tmj.cool_fees.R
import com.depromeet.tmj.cool_fees.common.base.BaseFragment
import com.depromeet.tmj.cool_fees.features.setting.SettingActivity
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit


class MainFragment : BaseFragment(), MainView {
    private lateinit var listener: Listener
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun setStandBackground() {
        iv_stand_type.visibility = View.VISIBLE
        iv_wall_type.visibility = View.GONE
    }

    override fun setWallBackground() {
        iv_stand_type.visibility = View.GONE
        iv_wall_type.visibility = View.VISIBLE
    }

    fun setListener(listener: () -> Unit) {
        this.listener = object : Listener {
            override fun onClickCalendarButton() {
                listener()
            }
        }
    }

    private fun bindPresenter() {
        presenter = MainPresenter(this)
        lifecycle.addObserver(presenter)
    }

    private fun initUi() {
        compositeDisposable.add(btn_setting.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe { goToSettingActivity() })

        compositeDisposable.add(tv_go_calendar.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe { listener.onClickCalendarButton() })
    }

    private fun goToSettingActivity() {
        context?.let { context ->
            startActivity(SettingActivity.getCallingIntent(context))
        }
    }

    interface Listener {
        fun onClickCalendarButton()
    }

    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }
}
