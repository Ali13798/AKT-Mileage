package us.ak_tech.aktmileagetracker

import java.time.LocalDateTime
import java.util.*

data class Trip(
    val id: UUID,
    val coordinates: MutableList<Coordinate>,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isForBusiness: Boolean
)