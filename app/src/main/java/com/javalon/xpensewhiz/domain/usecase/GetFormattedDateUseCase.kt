package com.javalon.xpensewhiz.domain.usecase

import android.text.format.DateFormat
import java.util.Date
import javax.inject.Inject

open class GetFormattedDateUseCase @Inject constructor() {
    open operator fun invoke(date: Date): String {
        return getFormattedDate(date)
    }

    private fun getFormattedDate(date: Date): String {
        val dayOfWeek = DateFormat.format("EEE", date)
        val day = DateFormat.format("dd", date)
        val monthAbbr = DateFormat.format("MMM", date)

        return "$dayOfWeek $day, $monthAbbr"
    }
}