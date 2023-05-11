package us.ak_tech.aktmileagetracker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.Coordinate
import us.ak_tech.aktmileagetracker.Trip
import java.util.*

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val coordinates = mutableListOf<Coordinate>()
    val trips = mutableListOf<Trip>()

    init {
        viewModelScope.launch {
            trips += loadTrips()
        }
    }

    suspend fun loadTrips(): MutableList<Trip> {
        delay(100)
        val resultCoords = mutableListOf<Coordinate>()
        val resultTrips = mutableListOf<Trip>()
        for (i in 0..5) {
            val coordinate = Coordinate(
                i,
                (i + 5) * 1.0,
                (i + 10) * 2.0
            )
            resultCoords += coordinate
        }

        for (i in 0..5) {
            resultTrips += Trip(
                UUID.randomUUID(),
                resultCoords,
                Date(),
                true
            )
        }
        return resultTrips
    }
}