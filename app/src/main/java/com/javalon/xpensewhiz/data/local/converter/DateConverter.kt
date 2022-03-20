package com.javalon.xpensewhiz.data.local.converter

import androidx.room.TypeConverter
import java.util.*

open class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}