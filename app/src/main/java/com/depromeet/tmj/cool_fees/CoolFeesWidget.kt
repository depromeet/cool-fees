package com.depromeet.tmj.cool_fees

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.applandeo.materialcalendarview.utils.DateUtils
import com.depromeet.tmj.cool_fees.common.datastore.AppPreferenceDataStore
import com.depromeet.tmj.cool_fees.features.main.calculateFee
import com.depromeet.tmj.cool_fees.model.Usage
import io.realm.Realm
import java.text.DecimalFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class CoolFeesWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
                context: Context, appWidgetManager: AppWidgetManager,
                appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget)
            views.setTextViewText(R.id.tv_live_fee,
                    String.format(context.getString(R.string.format_monthly_fee),
                            Calendar.getInstance().get(Calendar.MONTH) + 1))

            val totalUsageTime = getMonthlyUsageTime(Calendar.getInstance())
            views.setTextViewText(R.id.tv_total_hours, (totalUsageTime / 60).toString())
            views.setTextViewText(R.id.tv_total_minutes, (totalUsageTime % 60).toString())

            views.setTextViewText(R.id.tv_fee, DecimalFormat("#,###")
                    .format(calculateFee(Calendar.getInstance(), getElectronicUsage(totalUsageTime))))

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getElectronicUsage(totalUsageTime: Int): Double {
            return ((AppPreferenceDataStore().getWatt() * totalUsageTime/60) / 1000).toDouble()
        }

        private fun getMonthlyUsageTime(inputCalendar: Calendar): Int {
            DateUtils.setMidnight(inputCalendar)
            var totalTime = 0
            val firstCalendar = Calendar.getInstance().apply {
                timeInMillis = inputCalendar.timeInMillis
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val lastCalendar = Calendar.getInstance().apply {
                timeInMillis = inputCalendar.timeInMillis
                set(Calendar.DAY_OF_MONTH, inputCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            }
            val usageList = Realm.getDefaultInstance().where(Usage::class.java)
                    .between("time", firstCalendar.timeInMillis, lastCalendar.timeInMillis).findAll()
            if (usageList.isNotEmpty()) {
                for (usage in usageList) {
                    totalTime += usage.usingTime
                }
            }

            return totalTime
        }
    }
}

