package com.javalon.xpensewhiz.domain.usecase

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

open class GetDateUseCase @Inject constructor() {
    open operator fun invoke(): String {
        return getDate()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        return SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
    }
}