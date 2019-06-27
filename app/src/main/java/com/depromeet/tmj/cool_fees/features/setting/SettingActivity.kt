package com.depromeet.tmj.cool_fees.features.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ToggleButton
import com.depromeet.tmj.cool_fees.R
import com.depromeet.tmj.cool_fees.common.base.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.concurrent.TimeUnit

class SettingActivity : BaseActivity(), SettingView {
    private lateinit var presenter: SettingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        bindPresenter()
        initUi()
    }

    override fun checkWallType() {
        tb_wall_type.performClick()
    }

    override fun checkStandType() {
        tb_stand_type.performClick()
    }

    private fun bindPresenter() {
        presenter = SettingPresenter(this)
        lifecycle.addObserver(presenter)
    }

    private fun initUi() {
        compositeDisposable.add(tb_wall_type.clicks()
                .subscribe {
                    toggleSelector(tb_wall_type)
                    tv_watt_value.text = WATT_WALL_TYPE
                }
        )

        compositeDisposable.add(tb_stand_type.clicks()
                .subscribe {
                    toggleSelector(tb_stand_type)
                    tv_watt_value.text = WATT_STAND_TYPE
                })

        compositeDisposable.add(btn_confirm.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .map {
                    if (tb_wall_type.isChecked) SettingPresenter.TYPE_WALL
                    else SettingPresenter.TYPE_STAND
                }
                .subscribe(presenter::onClickConfirm))
    }

    private fun toggleSelector(toggleButton: ToggleButton) {
        when (toggleButton.id) {
            R.id.tb_wall_type -> {
                tb_wall_type.isChecked = true
                tb_stand_type.isChecked = false
            }
            R.id.tb_stand_type -> {
                tb_wall_type.isChecked = false
                tb_stand_type.isChecked = true
            }
        }
    }

    companion object {
        private const val WATT_WALL_TYPE = "600"
        private const val WATT_STAND_TYPE = "1800"

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java)
        }
    }
}
