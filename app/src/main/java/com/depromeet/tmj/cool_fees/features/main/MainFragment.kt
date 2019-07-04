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
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainFragment : BaseFragment(), MainView {
    private lateinit var listener: Listener
    private var presenter = MainPresenter(this)
    private val decimalFormat = DecimalFormat("#,###")

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
        presenter.onViewCreated()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            presenter.setUserVisibleHint()
        }
    }

    override fun setStandBackground() {
        iv_stand_type.visibility = View.VISIBLE
        iv_wall_type.visibility = View.GONE
    }

    override fun setWallBackground() {
        iv_stand_type.visibility = View.GONE
        iv_wall_type.visibility = View.VISIBLE
    }

    override fun setTotalUsageTime(usageMinutes: Int) {
        if (tv_total_hours != null && tv_total_minutes != null) {
            tv_total_hours.text = String.format("%02d", usageMinutes / 60)
            tv_total_minutes.text = String.format("%02d", usageMinutes % 60)
        }
    }

    override fun setTotalFee(fee: Int) {
        if (tv_fee != null)
            tv_fee.text = decimalFormat.format(fee)
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
        tv_live_fee.text = String.format(getString(R.string.format_monthly_fee),
                Calendar.getInstance().get(Calendar.MONTH) + 1)

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
