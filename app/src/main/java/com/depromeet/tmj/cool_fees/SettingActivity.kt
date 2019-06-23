package com.depromeet.tmj.cool_fees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.depromeet.tmj.cool_fees.common.base.BaseActivity

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, this::class.java)
    }
}
