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

    suspend fun loadTrips(): List<Trip> {
        return tripsRepository.getTrips()
    }
}