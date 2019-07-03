package com.applandeo.materialcalendarview;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.Calendar;

/**
 * This class represents an event of a day. An instance of this class is returned when user click
 * a day cell. This class can be overridden to make calendar more functional. A list of instances of
 * this class can be passed to CalendarView object using setEvents() method.
 * <p>
 * Created by Mateusz Kornakiewicz on 23.05.2017.
 */

public class EventDay {
    private Calendar mDay;
    private int useMinutes;

    public EventDay(Calendar day) {
        mDay = day;
    }


    public EventDay(Calendar day, int usingTime) {
        DateUtils.setMidnight(day);
        this.mDay = day;
        this.useMinutes = usingTime;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public int getUseMinutes() {
        return useMinutes;
    }

    public Calendar getCalendar() {
        return mDay;
    }

    public void setUseMinutes(int useMinutes) {
        this.useMinutes = useMinutes;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof EventDay) {
            return ((EventDay) obj).mDay.equals(this.mDay);
        }
        return false;
    }
}
