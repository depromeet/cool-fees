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

    private fun setTitle(calendar: Calendar) {
        tv_monthly_time.text = String.format(getString(R.string.format_monthly_time), calendar.get(Calendar.MONTH) + 1)
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
                setTotalUsageTime(getTotalUsageTime(getMonthlyUsageList(calendar.currentPageDate)))
            }
            fragmentManager?.beginTransaction()
                    ?.add(timeDialog, TimeDialog.TAG)
                    ?.commitAllowingStateLoss()
        }
        calendar.setOnForwardPageChangeListener { nextCalendar ->
            val nextUsageList = getMonthlyUsageList(nextCalendar)

            nextUsageList?.let { useageList ->
                calendar.setEvents(getEventDaysFromUsage(useageList))
            }
            setTotalUsageTime(getTotalUsageTime(nextUsageList))
            setTitle(nextCalendar)
        }
        calendar.setOnPreviousPageChangeListener { preCalendar ->
            val previousUsageList = getMonthlyUsageList(preCalendar)

            previousUsageList?.let { useageList ->
                calendar.setEvents(getEventDaysFromUsage(useageList))
            }
            setTotalUsageTime(getTotalUsageTime(previousUsageList))
            setTitle(preCalendar)
        }

        val currentUsageList = getMonthlyUsageList(calendar.currentPageDate)
        currentUsageList?.let { useageList ->
            calendar.setEvents(getEventDaysFromUsage(useageList))
        }
        setTotalUsageTime(getTotalUsageTime(currentUsageList))
        setTitle(calendar.currentPageDate)
    }

    private fun setTotalUsageTime(totalMinutes: Int) {
        tv_total_hours.text = String.format("%02d", totalMinutes / 60)
        tv_total_minutes.text = String.format("%02d", totalMinutes % 60)
    }

    private fun getTotalUsageTime(usageList: List<Usage>?): Int {
        var sum = 0
        usageList?.let {
            for (usage in it) {
                sum += usage.usingTime
            }
        }
        return sum
    }

    private fun getMonthlyUsageList(inputCalendar: Calendar): List<Usage>? {
        val firstCalendar = Calendar.getInstance().apply {
            timeInMillis = inputCalendar.timeInMillis
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val lastcalendar = Calendar.getInstance().apply {
            timeInMillis = inputCalendar.timeInMillis
            set(Calendar.DAY_OF_MONTH, inputCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        val usageList = Realm.getDefaultInstance().where(Usage::class.java)
                .between("time", firstCalendar.timeInMillis, lastcalendar.timeInMillis).findAll()
        if (usageList.isNotEmpty())
            return usageList
        else
            return null
    }

    private fun getEventDaysFromUsage(useList: List<Usage>): List<EventDay> {
        val eventDays = arrayListOf<EventDay>()

        for (usage in useList) {
            val usageCalendar = Calendar.getInstance().apply {
                timeInMillis = usage.time
            }
            eventDays.add(EventDay(usageCalendar, usage.usingTime))
        }
        return eventDays
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
