package us.ak_tech.aktmileagetracker.ui.details

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.TripsRepository
import java.util.UUID


class TripDetailViewModel(tripIdDb: UUID) : ViewModel() {

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

    init {
        viewModelScope.launch {
            _trip.value = tripRepository.getTrip(id = tripIdDb)
        }
    }

    fun setTripId(id: UUID) {
        _tripId.value = id
    }

    fun setText(msg: String) {
        _text.value = msg
    }
}

class CrimeDetailViewModelFactory(private val tripIdDb: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TripDetailViewModel(tripIdDb) as T
    }
}