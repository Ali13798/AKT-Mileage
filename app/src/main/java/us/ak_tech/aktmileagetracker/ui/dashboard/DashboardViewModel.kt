package us.ak_tech.aktmileagetracker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.Coordinate
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.TripsRepository
import java.time.LocalDateTime
import java.util.*

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val coordinates = mutableListOf<Coordinate>()
    val trips = mutableListOf<Trip>()
    val tripsRepository = TripsRepository.get()

    init {
        viewModelScope.launch {
            trips += loadTrips()
        }
    }

    suspend fun addTrip(trip: Trip) {
        tripsRepository.addTrip(trip)
    }

    suspend fun loadTrips(): MutableList<Trip> {
        delay(100)
        val resultTrips = mutableListOf<Trip>()
        for (i in 0..5) {
            val resultCoords = mutableListOf<Coordinate>()
            val id = UUID.randomUUID()
            for (j in 0..5) {
                val coordinate = Coordinate(
                    id,
                    j,
                    (i + 5) * (j + 2) * 1.0,
                    (i + 10) + (j * 2) * 2.0
                )
                resultCoords += coordinate
            }
            resultTrips += Trip(
                id,
                resultCoords,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(115),
                true
            )
        }
        return resultTrips
//        return tripsRepository.getTrips()
    }
}