package com.depromeet.tmj.cool_fees

import android.app.Application

class CoolFeesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE: CoolFeesApplication

        fun getGlobalApplicationContext(): CoolFeesApplication = INSTANCE
    }
}