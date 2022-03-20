package com.javalon.xpensewhiz.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javalon.xpensewhiz.data.local.entity.ExpenseInfo

open class ExpenseConverter {

    @TypeConverter
    fun toString(list: List<ExpenseInfo>) : String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(value: String): List<ExpenseInfo> {
        val type = object: TypeToken<List<ExpenseInfo>>() {}.type
        return Gson().fromJson(value, type)
    }
}