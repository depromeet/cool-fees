package com.depromeet.tmj.cool_fees

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> TaxFragment.newInstance()
            1 -> TaxFragment.newInstance()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }
}