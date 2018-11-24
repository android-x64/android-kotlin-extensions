package com.android.kotlin.extensions

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

private const val FORMAT_DATE = "dd.MM.yy"
private const val FORMAT_DATE_WEEK_DAY = "EEEE"
private const val FORMAT_DATE_DAY_MONTH = "d MMM"


private fun Calendar.resetTimeToMin(): Calendar {
    set(Calendar.HOUR_OF_DAY, getActualMinimum(Calendar.HOUR_OF_DAY))
    set(Calendar.MINUTE, getActualMinimum(Calendar.MINUTE))
    set(Calendar.SECOND, getActualMinimum(Calendar.SECOND))
    set(Calendar.MILLISECOND, getActualMinimum(Calendar.MILLISECOND))
    return this
}

fun Date.toCalendarWithMinTime(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.resetTimeToMin()
}

fun Long.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    return cal
}

@JvmOverloads
fun Calendar.toHumanDate(context: Context, format: String = FORMAT_DATE_DAY_MONTH): String {
    return when {
        isToday() -> context.getString(R.string.today)
        isYesterday() -> context.getString(R.string.yesterday)
        else -> SimpleDateFormat(format).format(time)
    }
}

infix fun Calendar.isSameDayAs(cal: Calendar): Boolean {
    return get(Calendar.ERA) == cal.get(Calendar.ERA) &&
            get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
            get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
}

fun Date.isToday(): Boolean {
    return toCalendarWithMinTime() isSameDayAs Calendar.getInstance()
}

fun Calendar.isToday(): Boolean {
    return this isSameDayAs Calendar.getInstance()
}

fun Calendar.isYesterday(): Boolean {
    val yesterday = Calendar.getInstance()
    yesterday.roll(Calendar.DAY_OF_MONTH, -1)
    return this isSameDayAs yesterday
}

fun Calendar.isWithinWeekRange(): Boolean {
    val maxDaysAgo = 7
    val todayYear = Calendar.getInstance().get(Calendar.YEAR)
    val todayDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
    val year = get(Calendar.YEAR)
    val day = get(Calendar.DAY_OF_YEAR)
    val dayOfMonth = get(Calendar.DAY_OF_MONTH)
    val isDecember = get(Calendar.MONTH) == Calendar.DECEMBER

    return if (todayYear == year) {
        abs(todayDay - day) < maxDaysAgo
    } else if (abs(todayYear - year) > 1 || !isDecember || todayDay >= maxDaysAgo) {
        false
    } else {
        val daysTillNewYear = 31 - dayOfMonth
        todayDay + daysTillNewYear < maxDaysAgo
    }
}

fun Date.formatSectionDate(context: Context): String {
    val c = toCalendarWithMinTime()
    return when {
        c.isToday() -> context.getString(R.string.today)
        c.isYesterday() -> context.getString(R.string.yesterday)
        c.isWithinWeekRange() -> SimpleDateFormat(FORMAT_DATE_WEEK_DAY, Locale.getDefault()).format(c.time)
        else -> SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(c.time)
    }
}