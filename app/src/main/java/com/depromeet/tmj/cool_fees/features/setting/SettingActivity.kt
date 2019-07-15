package com.depromeet.tmj.cool_fees.features.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import com.depromeet.tmj.cool_fees.MainActivity
import com.depromeet.tmj.cool_fees.R
import com.depromeet.tmj.cool_fees.common.base.BaseActivity
import com.depromeet.tmj.cool_fees.features.shared.TYPE_STAND
import com.depromeet.tmj.cool_fees.features.shared.TYPE_WALL
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
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
        tb_wall_type.isChecked = true
        tb_stand_type.isChecked = false
    }

    override fun checkStandType() {
        tb_wall_type.isChecked = false
        tb_stand_type.isChecked = true
    }

    override fun setWatt(watt: Int) {
        et_watt.setText(watt.toString())
    }

    override fun goToMainActivity() {
        startActivity(MainActivity.getCallingIntent(this))
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
    }

    override fun finishActivity() {
        finish()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
    }

    private fun bindPresenter() {
        presenter = SettingPresenter(this)
        lifecycle.addObserver(presenter)
    }

    private fun initUi() {
        compositeDisposable.add(tb_wall_type.clicks()
                .subscribe {
                    toggleSelector(tb_wall_type)
                    et_watt.setText(WATT_WALL_TYPE)
                }
        )

        compositeDisposable.add(tb_stand_type.clicks()
                .subscribe {
                    toggleSelector(tb_stand_type)
                    et_watt.setText(WATT_STAND_TYPE)
                })

        compositeDisposable.add(btn_confirm.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .map {
                    if (tb_wall_type.isChecked) TYPE_WALL
                    else TYPE_STAND
                }
                .subscribe { type ->
                    presenter.onClickConfirm(type, et_watt.text.toString().toInt())
                })

        compositeDisposable.add(et_watt.textChanges()
                .subscribe { watt ->
                    if (watt.isNotEmpty()) {
                        if (watt.toString().toInt() > 5000) {
                            tv_warning.visibility = View.VISIBLE
                            btn_confirm.isEnabled = false
                        } else {
                            tv_warning.visibility = View.GONE
                            btn_confirm.isEnabled = true
                        }
                    }
                })

        compositeDisposable.add(cl_info.clicks()
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe { showInfoBottomSheet() })
    }

    private fun showInfoBottomSheet() {
        InfoBottomSheet.newInstance().show(supportFragmentManager, InfoBottomSheet.TAG)
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
