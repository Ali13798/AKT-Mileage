package us.ak_tech.aktmileagetracker.database

import androidx.room.*
import us.ak_tech.aktmileagetracker.Trip
import java.util.*

@Dao
interface TripDao {
    @Query("SELECT * FROM Trips")
    suspend fun getTrips(): List<Trip>

    @Query("SELECT * FROM Trips WHERE id=(:id)")
    suspend fun getTrip(id: UUID): Trip

    @Update
    suspend fun updateTrip(trip: Trip)

    @Insert
    suspend fun addTrip(trip: Trip)

    @Query("DELETE FROM Trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: UUID)
}