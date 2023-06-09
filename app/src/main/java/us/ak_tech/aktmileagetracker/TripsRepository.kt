package us.ak_tech.aktmileagetracker

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.database.TripDatabase
import java.util.*


private const val DB_NAME = "trip-database.db"

class TripsRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val db: TripDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TripDatabase::class.java,
            DB_NAME
        )
        .build()

    suspend fun getTrips(): List<Trip> = db.tripDao().getTrips()
    suspend fun getTrip(id: UUID): Trip = db.tripDao().getTrip(id)

    fun updateTrip(trip: Trip) {
        coroutineScope.launch {
            db.tripDao().updateTrip(trip)
        }
    }

    suspend fun addTrip(trip: Trip) {
        db.tripDao().addTrip(trip)
    }

    suspend fun deleteTrip(tripId: UUID) {
        db.tripDao().deleteTrip(tripId = tripId)
    }

    companion object {
        private var INSTANCE: TripsRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TripsRepository(context)
            }
        }

        fun get(): TripsRepository {
            return INSTANCE ?: throw IllegalStateException("crimeRepository must be initialized.")
        }
    }
}