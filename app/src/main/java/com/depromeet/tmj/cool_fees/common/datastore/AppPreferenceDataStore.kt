package com.depromeet.tmj.cool_fees.common.datastore

import com.depromeet.tmj.cool_fees.CoolFeesApplication

class AppPreferenceDataStore: SharedPreferencesDataStore(CoolFeesApplication.getGlobalApplicationContext()) {

    fun putAirType(value: String) {
        this.putString(KEY_AIR_CONDITIONAL_TYPE, value)
    }

    fun getAireType(): String {
        return this.getString(KEY_AIR_CONDITIONAL_TYPE, "")
    }

    fun isFirstLaunch(): Boolean {
        return getBoolean(KEY_IS_FIRST_TIME_LAUNCH, true)
    }

    fun putFirstLaunch(isFirstTime: Boolean) {
        putBoolean(KEY_IS_FIRST_TIME_LAUNCH, isFirstTime)
    }

    companion object {
        private const val KEY_AIR_CONDITIONAL_TYPE = "KEY_AIR_CONDITIONAL_TYPE"
        private const val KEY_IS_FIRST_TIME_LAUNCH = "KEY_IS_FIRST_TIME_LAUNCH"
    }
}