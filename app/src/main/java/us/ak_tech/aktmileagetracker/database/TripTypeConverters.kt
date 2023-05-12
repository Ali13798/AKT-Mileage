package us.ak_tech.aktmileagetracker.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class TripTypeConverters {
    @TypeConverter
    fun fromDate(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toDate(secondsSinceEpoch: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(secondsSinceEpoch, 0, ZoneOffset.UTC)
    }
}