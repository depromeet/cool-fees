package com.depromeet.tmj.cool_fees

import android.animation.Animator
import android.os.Bundle
import com.depromeet.tmj.cool_fees.common.base.BaseActivity
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore
import com.depromeet.tmj.cool_fees.features.setting.SettingActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isFirstLaunch()) {
                    goToSettingActivity()
                } else {
                    goToMainActivity()
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }

    private fun isFirstLaunch(): Boolean {
        return AppPreferenceDataStore().isFirstLaunch()
    }

    private fun goToSettingActivity() {
        startActivity(SettingActivity.getCallingIntent(this))
        finish()
    }

    private fun goToMainActivity() {
        startActivity(MainActivity.getCallingIntent(this))
        finish()
    }
}
