package com.depromeet.tmj.cool_fees.common.datastore

import com.depromeet.tmj.cool_fees.CoolFeesApplication

class AppPreferenceDataStore: SharedPreferencesDataStore(CoolFeesApplication.getGlobalApplicationContext()) {

    fun putAirType(value: String) {
        this.putString(KEY_AIR_CONDITIONAL_TYPE, value)
    }

    fun getAireType(): String {
        return this.getString(KEY_AIR_CONDITIONAL_TYPE, "")
    }

    companion object {
        private const val KEY_AIR_CONDITIONAL_TYPE = "KEY_AIR_CONDITIONAL_TYPE"
    }
}