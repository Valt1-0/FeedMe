package com.example.feedme.util

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(longDate: Long?): Date? {
        return longDate?.times(1000)?.let { Date(it) }
    }
}