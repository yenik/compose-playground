package cz.nuanced.composemindframe.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun stringToDate(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(it, formatter)
        }
    }
}