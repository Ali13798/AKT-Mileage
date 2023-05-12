package us.ak_tech.aktmileagetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity
data class Trip(
    @PrimaryKey val id: UUID,
    val coordinates: MutableList<Coordinate>,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isForBusiness: Boolean
)