package com.depromeet.tmj.cool_fees.features.calendar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.applandeo.materialcalendarview.EventDay
import com.depromeet.tmj.cool_fees.R
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_time_dialog.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class TimeDialog : DialogFragment() {
    private lateinit var listener: Listener
    private val compositeDisposable = CompositeDisposable()
    private var calendar = Calendar.getInstance()
    private var useMinutes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initNumberPicker()
    }

    fun setListener(listener: (Int) -> Unit) {
        this.listener = object : Listener {
            override fun onClickOk(useMinutes: Int) {
                listener(useMinutes)
            }
        }
    }

    private fun initArgs() {
        arguments?.run {
            calendar.timeInMillis = getLong(ARG_TIME, 0)
            useMinutes = getInt(ARG_USE_MINUTE, 0)
        }
    }

    private fun initUi() {
        setTitleDate()
        setUseMinutes()
        compositeDisposable.add(btn_cancel.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe { dismissAllowingStateLoss() })

        compositeDisposable.add(btn_ok.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe {
                    // 데이터 베이스에 저장
                    useMinutes = np_hour.value * 60 + np_minute.value
                    listener.onClickOk(useMinutes)
                    dismissAllowingStateLoss()
                })
    }

    private fun setTitleDate() {
        tv_date.text = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(calendar.time)
    }

    private fun setUseMinutes() {
        np_hour.value = useMinutes / 60
        np_minute.value = useMinutes % 60
    }

    private fun initNumberPicker() {
        np_hour.minValue = 0
        np_hour.maxValue = 23
        np_hour.wrapSelectorWheel = false

        np_minute.minValue = 0
        np_minute.maxValue = 59
        np_minute.wrapSelectorWheel = false
    }

    interface Listener {
        fun onClickOk(useMinutes: Int)
    }

    companion object {
        const val TAG = "TimeDialog"
        private const val ARG_TIME = "ARG_TIME"
        private const val ARG_USE_MINUTE = "ARG_USE_MINUTE"

        fun newInstance(eventDay: EventDay): TimeDialog {
            return TimeDialog().apply {
                arguments = Bundle().apply {
                    putLong(ARG_TIME, eventDay.calendar.timeInMillis)
                    putInt(ARG_USE_MINUTE, eventDay.useMinutes)
                }
            }
        }
    }
}
