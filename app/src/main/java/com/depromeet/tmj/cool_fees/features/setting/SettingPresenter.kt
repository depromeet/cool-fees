package com.depromeet.tmj.cool_fees.features.setting

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.depromeet.tmj.cool_fees.common.base.BasePresenter
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore

class SettingPresenter(private val view: SettingView) : BasePresenter() {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        when (AppPreferenceDataStore().getAirType()) {
            TYPE_WALL -> view.checkWallType()
            TYPE_STAND -> view.checkStandType()
            else -> view.checkWallType()
        }
        view.setWatt(AppPreferenceDataStore().getWatt())
    }

    fun onClickConfirm(type: String, watt: Int) {
        setAirType(type)
        setFirstLaunchDisable()
        setWatt(watt)
        view.finish()
    }

    private fun setAirType(type: String) {
        AppPreferenceDataStore().putAirType(type)
    }

    private fun setFirstLaunchDisable() {
        AppPreferenceDataStore().putFirstLaunch(false)
    }

    private fun setWatt(watt: Int) {
        AppPreferenceDataStore().putWatt(watt)
    }

    companion object {
        const val TYPE_WALL = "TYPE_WALL"
        const val TYPE_STAND = "TYPE_STAND"
    }
}