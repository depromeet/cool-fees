package com.depromeet.tmj.cool_fees.features.setting

import com.depromeet.tmj.cool_fees.common.base.BasePresenter
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore

class SettingPresenter(val view: SettingView): BasePresenter() {

    private fun setFirstLaunchDisable() {
        AppPreferenceDataStore().putFirstLaunch(false)
    }
}