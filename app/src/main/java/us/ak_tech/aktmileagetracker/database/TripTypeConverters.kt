package us.ak_tech.aktmileagetracker.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import us.ak_tech.aktmileagetracker.Coordinate
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

    @TypeConverter
    fun fromCoordinateList(value: List<Coordinate>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Coordinate>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCoordinateList(value: String): List<Coordinate> {
        val gson = Gson()
        val type = object : TypeToken<List<Coordinate>>() {}.type
        return gson.fromJson(value, type)
    }
}