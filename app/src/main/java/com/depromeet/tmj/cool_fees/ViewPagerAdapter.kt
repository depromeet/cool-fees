package com.depromeet.tmj.cool_fees

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.depromeet.tmj.cool_fees.features.main.MainFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private lateinit var fragmentList: List<Fragment>

    override fun getItem(position: Int): Fragment? {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun setFragmentList(fragmentList: List<Fragment>) {
        this.fragmentList = fragmentList
    }
}