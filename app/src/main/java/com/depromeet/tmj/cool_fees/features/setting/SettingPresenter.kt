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
    }

    fun onClickConfirm(type: String) {
        setAirType(type)
        setFirstLaunchDisable()
        view.finish()
    }

    private fun setAirType(type: String) {
        AppPreferenceDataStore().putAirType(type)
    }

    private fun setFirstLaunchDisable() {
        AppPreferenceDataStore().putFirstLaunch(false)
    }

    companion object {
        const val TYPE_WALL = "TYPE_WALL"
        const val TYPE_STAND = "TYPE_STAND"
    }
}