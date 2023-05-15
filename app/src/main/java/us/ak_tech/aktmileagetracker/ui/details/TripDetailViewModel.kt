package us.ak_tech.aktmileagetracker.ui.details

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.TripsRepository
import java.util.*


class TripDetailViewModel(tripId: UUID) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


    private val tripRepository = TripsRepository.get()

    private val _trip: MutableStateFlow<Trip?> = MutableStateFlow(null)
    val trip: StateFlow<Trip?> = _trip.asStateFlow()

    init {
        viewModelScope.launch {
            _trip.value = tripRepository.getTrip(id = tripId)
        }
    }

    fun updateTrip(onUpdate: (Trip) -> Trip) {
        _trip.update { oldTrip ->
            oldTrip?.let {
                onUpdate(it)
            }
        }
    }

    fun setText(msg: String) {
        _text.value = msg
    }

    override fun onCleared() {
        super.onCleared()
        trip.value?.let { tripRepository.updateTrip(it) }
    }
}

class TripDetailViewModelFactory(private val tripId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TripDetailViewModel(tripId) as T
    }
}