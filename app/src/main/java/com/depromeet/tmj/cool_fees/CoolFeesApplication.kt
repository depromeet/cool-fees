package com.depromeet.tmj.cool_fees

import android.app.Application
import com.google.android.gms.ads.MobileAds
import io.realm.Realm

class CoolFeesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Realm.init(this)
        initAdmob()
    }

    private fun initAdmob() {
        MobileAds.initialize(this, getString(R.string.admob_app_id))
    }

    companion object {
        private lateinit var INSTANCE: CoolFeesApplication

        fun getGlobalApplicationContext(): CoolFeesApplication = INSTANCE
    }
}