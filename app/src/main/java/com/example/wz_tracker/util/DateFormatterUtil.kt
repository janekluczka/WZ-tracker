package com.example.wz_tracker.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateFormatterUtil {

    private const val SECONDS_IN_MINUTE = 60
    private const val MINUTES_IN_HOUR = 60
    private const val HOURS_IN_DAY = 24

    private val dateFormatter = SimpleDateFormat("EEE dd.MM.yyyy", Locale.ENGLISH)
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    init {
        timeFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.timeZone = TimeZone.getDefault()
    }

    fun getTimePassed(date: Long): String {
        val timeDifference = getTimeDifference(date)
        return returnTimePassed(timeDifference)
    }

    private fun getTimeDifference(date: Long) = System.currentTimeMillis() - date * 1000

    private fun returnTimePassed(timeDifference: Long): String {
        val timeInSeconds = calculateTimeInSeconds(timeDifference)
        if (isLessThanMinute(timeInSeconds)) {
            return "Now"
        }
        val timeInMinutes = calculateTimeInMinutes(timeDifference)
        if (isLessThanHour(timeInMinutes)) {
            return buildTimePassed(timeInMinutes, "minute")
        }
        val timeInHours = calculateTimeInHours(timeDifference)
        if (isLessThanDay(timeInHours)) {
            return buildTimePassed(timeInHours, "hour")
        }
        val timeInDays = calculateTimeInDays(timeDifference)
        if (isLessThanWeek(timeInDays)) {
            return buildTimePassed(timeInDays, "day")
        }
        val timeInWeeks = calculateTimeInWeeks(timeDifference)
        return buildTimePassed(timeInWeeks, "week")
    }

    private fun calculateTimeInSeconds(timeDifference: Long) =
        TimeUnit.MILLISECONDS.toSeconds(timeDifference)

    private fun isLessThanMinute(seconds: Long) = seconds < SECONDS_IN_MINUTE

    private fun calculateTimeInMinutes(timeDifference: Long) =
        TimeUnit.MILLISECONDS.toMinutes(timeDifference)

    private fun isLessThanHour(minutes: Long) = minutes < MINUTES_IN_HOUR

    private fun calculateTimeInHours(timeDifference: Long) =
        TimeUnit.MILLISECONDS.toHours(timeDifference)

    private fun isLessThanDay(hours: Long) = hours < HOURS_IN_DAY

    private fun calculateTimeInDays(timeDifference: Long) =
        TimeUnit.MILLISECONDS.toDays(timeDifference)

    private fun isLessThanWeek(days: Long) = days < 7

    private fun calculateTimeInWeeks(timeDifference: Long) =
        (TimeUnit.MILLISECONDS.toDays(timeDifference) / 7)

    fun getFormattedDate(date: Long): String = dateFormatter.format(Date(date * 1000).time)

    fun getFormattedTime(date: Long): String = timeFormatter.format(Date(date * 1000).time)

    private fun buildTimePassed(amount: Long, type: String): String =
        when (amount) {
            1L -> "$amount $type ago"
            else -> "$amount $type" + "s ago"
        }

}
