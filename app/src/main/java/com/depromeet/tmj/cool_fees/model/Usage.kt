package com.depromeet.tmj.cool_fees.model

import com.applandeo.materialcalendarview.EventDay
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Usage() : RealmObject() {
    @PrimaryKey
    var time: Long = 0
    var usingTime: Int = 0

    constructor(time: Long, usingTime: Int) : this() {
        this.time = time
        this.usingTime = usingTime
    }

    constructor(eventDay: EventDay) : this() {
        this.time = eventDay.calendar.timeInMillis
        this.usingTime = eventDay.useMinutes
    }
}