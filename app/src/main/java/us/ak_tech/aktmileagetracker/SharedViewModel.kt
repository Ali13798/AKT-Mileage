package us.ak_tech.aktmileagetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {
    private val _tripId = MutableLiveData<UUID>()
    var tripId: LiveData<UUID> = _tripId

    fun setTripId(id: UUID) {
        _tripId.value = id
    }
}