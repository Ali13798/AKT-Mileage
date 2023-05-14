package us.ak_tech.aktmileagetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import us.ak_tech.aktmileagetracker.Coordinate
import us.ak_tech.aktmileagetracker.Trip

@Database(entities = [Trip::class, Coordinate::class], version = 1)
@TypeConverters(TripTypeConverters::class)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}