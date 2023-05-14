package us.ak_tech.aktmileagetracker.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.TripsRepository
import java.util.UUID


class TripDetailViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    var a = 0

    private val _tripId = MutableLiveData<UUID>()
    var tripId: LiveData<UUID> = _tripId

    private val tripRepository = TripsRepository.get()

    private val _trip: MutableStateFlow<Trip?> = MutableStateFlow(null)
    val trip: StateFlow<Trip?> = _trip.asStateFlow()

    fun setTripId(id: UUID) {
        _tripId.value = id
    }

    fun setText(msg: String) {
        _text.value = msg
    }

}