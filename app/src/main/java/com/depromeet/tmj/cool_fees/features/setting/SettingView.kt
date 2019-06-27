package com.depromeet.tmj.cool_fees.features.setting

import com.depromeet.tmj.cool_fees.common.base.BaseView

interface SettingView : BaseView {
    fun checkWallType()

    fun checkStandType()

    fun finish()
}