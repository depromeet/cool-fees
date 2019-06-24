package com.depromeet.tmj.cool_fees

import android.os.Bundle
import com.depromeet.tmj.cool_fees.common.base.BaseActivity
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore
import com.depromeet.tmj.cool_fees.features.setting.SettingActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (isFirstLaunch()) {
            goToSettingActivity()
        } else {
            goToMainActivity()
        }
    }

    private fun isFirstLaunch(): Boolean {
        return AppPreferenceDataStore().isFirstLaunch()
    }

    private fun setFirstLaunchDisable() {
        AppPreferenceDataStore().putFirstLaunch(false)
    }

    private fun goToSettingActivity() {
        compositeDisposable.add(Observable.just(0)
                .delay(2, TimeUnit.SECONDS)
                .subscribe {
                    startActivity(SettingActivity.getCallingIntent(this))
                    setFirstLaunchDisable()
                })
    }

    private fun goToMainActivity() {
        compositeDisposable.add(Observable.just(0)
                .delay(2, TimeUnit.SECONDS)
                .subscribe { startActivity(MainActivity.getCallingIntent(this)) })
    }
}
