package com.depromeet.tmj.cool_fees.features.calendar

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applandeo.materialcalendarview.EventDay
import com.depromeet.tmj.cool_fees.R
import com.depromeet.tmj.cool_fees.common.base.BaseFragment
import com.depromeet.tmj.cool_fees.model.Usage
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*


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
                val usage = Usage(it)

                saveUsageToRealm(usage)
                calendar.updateEvent(it)
            }
            fragmentManager?.beginTransaction()
                ?.add(timeDialog, TimeDialog.TAG)
                ?.commitAllowingStateLoss()
        }
        calendar.setOnForwardPageChangeListener { nextCalendar ->
            Log.d(TAG, "Next calendar : " + nextCalendar.time)
            getMonthlyUsageList(nextCalendar)?.let { eventDays ->
                calendar.setEvents(eventDays)
            }
        }
        calendar.setOnPreviousPageChangeListener { preCalendar ->
            Log.d(TAG, "Previous calendar : " + preCalendar.time)
            getMonthlyUsageList(preCalendar)?.let { eventDays ->
                calendar.setEvents(eventDays)
            }
        }

        getMonthlyUsageList(calendar.currentPageDate)?.let { eventDays ->
            calendar.setEvents(eventDays)
        }
    }

    private fun getMonthlyUsageList(inputCalendar: Calendar): List<EventDay>? {
        val firstCalendar = Calendar.getInstance().apply {
            timeInMillis = inputCalendar.timeInMillis
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val lastcalendar = Calendar.getInstance().apply {
            timeInMillis = inputCalendar.timeInMillis
            set(Calendar.DAY_OF_MONTH, inputCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        val useList = Realm.getDefaultInstance().where(Usage::class.java)
            .between("time", firstCalendar.timeInMillis, lastcalendar.timeInMillis).findAll()

        if (useList.size > 0) {
            val eventDays = arrayListOf<EventDay>()

            for (usage in useList) {
                val usageCalendar = Calendar.getInstance().apply {
                    timeInMillis = usage.time
                }
                eventDays.add(EventDay(usageCalendar, usage.usingTime))
            }
            return eventDays
        }
        return null
    }

    private fun saveUsageToRealm(usage: Usage) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            realm.copyToRealmOrUpdate(usage)
        }
    }

    interface Listener {
    }

    companion object {
        const val TAG = "CalendarFragment"

        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }
}
