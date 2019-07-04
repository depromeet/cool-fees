package com.depromeet.tmj.cool_fees.features.calendar

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.depromeet.tmj.cool_fees.common.base.BasePresenter
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore
import com.depromeet.tmj.cool_fees.features.shared.TYPE_STAND
import com.depromeet.tmj.cool_fees.features.shared.TYPE_WALL

class CalendarPresenter(private val view: CalendarView) : BasePresenter() {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        setBackground()
    }

    private fun setBackground() {
        when (AppPreferenceDataStore().getAirType()) {
            TYPE_WALL -> {
                view.setWallBackground()
            }
            TYPE_STAND -> {
                view.setStandBackground()
            }
        }
    }
}