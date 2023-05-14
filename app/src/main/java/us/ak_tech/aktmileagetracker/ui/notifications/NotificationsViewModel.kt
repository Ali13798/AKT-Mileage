package us.ak_tech.aktmileagetracker.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID


class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    var a = 0

    private val _tripId = MutableLiveData<UUID>()
    var tripId: LiveData<UUID> = _tripId

    fun setTripId(id: UUID) {
        _tripId.value = id
    }

    fun setText(msg: String) {
        _text.value = msg
    }

}