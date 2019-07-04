package com.depromeet.tmj.cool_fees.features.main

import com.applandeo.materialcalendarview.utils.DateUtils
import java.util.*

private const val DEFAULT_FEE_UNDER = 910
private const val DEFAULT_FEE_MID = 1600
private const val DEFAULT_FEE_UPPER = 7300

private const val FEE_PER_KWH_UNDER = 93.3
private const val FEE_PER_KWH_MID = 187.9
private const val FEE_PER_KHW_UPPER = 280.6

private const val LIMIT_UNDER_SUMMER = 300
private const val LIMIT_UPPER_SUMMER = 450

private const val LIMIT_UNDER = 200
private const val LIMIT_UPPER = 400

fun calculateFee(calendar: Calendar, kWh: Double): Int {
    var fee = 0.0

    if (isSummer(calendar)) {
        fee = when {
            kWh <= LIMIT_UNDER_SUMMER -> DEFAULT_FEE_UNDER + (FEE_PER_KWH_UNDER * kWh)
            kWh > LIMIT_UPPER_SUMMER -> DEFAULT_FEE_UPPER + (FEE_PER_KHW_UPPER * kWh)
            else -> DEFAULT_FEE_MID + (FEE_PER_KWH_MID * kWh)
        }
    } else {
        fee = when {
            kWh <= LIMIT_UNDER -> DEFAULT_FEE_UNDER + (FEE_PER_KWH_UNDER * kWh)
            kWh > LIMIT_UPPER -> DEFAULT_FEE_UPPER + (FEE_PER_KHW_UPPER * kWh)
            else -> DEFAULT_FEE_MID + (FEE_PER_KWH_MID * kWh)
        }
    }

    return fee.toInt()
}

fun isSummer(calendar: Calendar): Boolean {
    val startCalendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, 6)
        set(Calendar.DAY_OF_MONTH, 1)
        DateUtils.setMidnight(this)
    }
    val endCalendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, 8)
        set(Calendar.DAY_OF_MONTH, 1)
        DateUtils.setMidnight(this)
    }

    return (calendar.after(startCalendar) && calendar.before(endCalendar))
}