package us.ak_tech.aktmileagetracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import us.ak_tech.aktmileagetracker.Coordinate
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.TripsRepository
import java.time.LocalDateTime
import java.util.UUID

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val tripsRepository = TripsRepository.get()
    var isTripRecording = false
    var coordinateIndex = 0
    var coordinates = mutableListOf<Coordinate>()
    var tripId: UUID? = null
    var trip: Trip? = null
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null
    var isBusinessTrip = false

    var allTrips: List<Trip>? = null
    var totalMilesDrivenForBusiness = 0.0
    var totalMilesDrivenForPersonal = 0.0
    var totalMilesDriven = 0.0


    suspend fun loadTrips(): List<Trip> {
        return tripsRepository.getTrips()
    }

    suspend fun addTrip(trip: Trip) {
        tripsRepository.addTrip(trip)
    }
}