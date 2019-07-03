package com.depromeet.tmj.cool_fees

import android.app.Application
import io.realm.Realm

class CoolFeesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Realm.init(this)
    }

    companion object {
        private lateinit var INSTANCE: CoolFeesApplication

        fun getGlobalApplicationContext(): CoolFeesApplication = INSTANCE
    }
}