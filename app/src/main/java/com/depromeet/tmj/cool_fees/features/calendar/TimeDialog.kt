package com.depromeet.tmj.cool_fees.features.calendar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.depromeet.tmj.cool_fees.R
import kotlinx.android.synthetic.main.fragment_time_dialog.*


class TimeDialog : DialogFragment() {
    private lateinit var listener: Listener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNumberPicker()
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
    }

    companion object {
        const val TAG = "TimeDialog"

        fun newInstance(): TimeDialog {
            return TimeDialog()
        }
    }
}
