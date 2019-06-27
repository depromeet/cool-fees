package com.depromeet.tmj.cool_fees


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.tmj.cool_fees.common.base.BaseFragment
import com.depromeet.tmj.cool_fees.features.setting.SettingActivity
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.fragment_tax.*
import java.util.concurrent.TimeUnit


class TaxFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tax, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        compositeDisposable.add(btn_setting.clicks()
            .throttleFirst(2000, TimeUnit.MILLISECONDS)
            .subscribe { goToSettingActivity() })
    }

    private fun goToSettingActivity() {
        context?.let { context ->
            startActivity(SettingActivity.getCallingIntent(context))
        }
    }

    companion object {
        const val TAG = "TaxFragment"

        fun newInstance(): TaxFragment = TaxFragment()
    }
}
