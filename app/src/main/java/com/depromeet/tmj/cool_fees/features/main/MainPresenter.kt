package com.depromeet.tmj.cool_fees.features.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.applandeo.materialcalendarview.utils.DateUtils
import com.depromeet.tmj.cool_fees.common.base.BasePresenter
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore
import com.depromeet.tmj.cool_fees.features.shared.TYPE_STAND
import com.depromeet.tmj.cool_fees.features.shared.TYPE_WALL
import com.depromeet.tmj.cool_fees.model.Usage
import io.realm.Realm
import java.util.*

class MainPresenter(private val view: MainView) : BasePresenter() {
    private var totalUsageTime = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        setBackground()
    }

    fun onViewCreated() {
        totalUsageTime = getMonthlyUsageTime(Calendar.getInstance())
        view.setTotalUsageTime(totalUsageTime)
        view.setTotalFee(calculateFee(Calendar.getInstance(), getElectronicUsage()))
    }

    fun setUserVisibleHint() {
        totalUsageTime = getMonthlyUsageTime(Calendar.getInstance())
        view.setTotalUsageTime(totalUsageTime)
        view.setTotalFee(calculateFee(Calendar.getInstance(), getElectronicUsage()))
    }

    private fun getElectronicUsage(): Double {
        return ((AppPreferenceDataStore().getWatt() * totalUsageTime/60) / 1000).toDouble()
    }

    private fun setBackground() {
        when (AppPreferenceDataStore().getAirType()) {
            TYPE_STAND -> {
                view.setStandBackground()
            }
            TYPE_WALL -> {
                view.setWallBackground()
            }
        }
    }

    private fun getMonthlyUsageTime(inputCalendar: Calendar): Int {
        DateUtils.setMidnight(inputCalendar)
        var totalTime = 0
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
        if (usageList.isNotEmpty()) {
            for (usage in usageList) {
                totalTime += usage.usingTime
            }
        }

        return totalTime
    }
}