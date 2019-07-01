package com.depromeet.tmj.cool_fees.common.datastore

import android.content.Context
import android.content.SharedPreferences

open class SharedPreferencesDataStore {
    private lateinit var sharedPreference: SharedPreferences

    constructor(context: Context) {
        this.sharedPreference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreference.getString(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String, defaultValue: Boolean) {
        sharedPreference.edit().putBoolean(key, defaultValue).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreference.getInt(key, defaultValue)
    }

    fun putInt(key: String, defaultValue: Int) {
        sharedPreference.edit().putInt(key, defaultValue).apply()
    }
}