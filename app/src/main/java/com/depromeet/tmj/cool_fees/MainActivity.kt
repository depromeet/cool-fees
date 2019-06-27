package com.depromeet.tmj.cool_fees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.depromeet.tmj.cool_fees.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
    }

    private fun initViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        viewpager.adapter = adapter
        viewpager.setScrollDurationFactor(2.0)
    }

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
