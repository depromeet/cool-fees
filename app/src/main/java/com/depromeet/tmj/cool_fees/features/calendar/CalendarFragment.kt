package com.depromeet.tmj.cool_fees.features.calendar

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.tmj.cool_fees.R
import com.depromeet.tmj.cool_fees.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_calendar.*


class CalendarFragment : BaseFragment(), CalendarView {
    private lateinit var listener: Listener
    private lateinit var presenter: CalendarPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendar()
    }

    override fun setStandBackground() {
        iv_stand_type.visibility = View.VISIBLE
        iv_wall_type.visibility = View.GONE
    }

    override fun setWallBackground() {
        iv_stand_type.visibility = View.GONE
        iv_wall_type.visibility = View.VISIBLE
    }

    private fun bindPresenter() {
        presenter = CalendarPresenter(this)
        lifecycle.addObserver(presenter)
    }

    private fun initCalendar() {
        calendar.setOnDayClickListener {
            val timeDialog = TimeDialog.newInstance(it)
            timeDialog.setListener { useMinutes ->
                it.useMinutes = useMinutes
                calendar.updateEvent(it)
            }
            fragmentManager?.beginTransaction()
                    ?.add(timeDialog, TimeDialog.TAG)
                    ?.commitAllowingStateLoss()
        }
    }

    interface Listener {
    }

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }
}
