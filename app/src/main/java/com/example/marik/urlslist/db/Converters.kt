package com.example.marik.urlslist.db

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun toUUID(value: String?): UUID? {
        return if (value == null) null else UUID.fromString(value)
    }

    @TypeConverter
    fun toString(value: UUID?): String? {
        return value?.toString()
    }
}