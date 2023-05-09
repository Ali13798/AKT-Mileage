package us.ak_tech.aktmileagetracker

import java.util.Date
import java.util.UUID

data class Trip(
    val id: UUID,
    val coordinates: MutableList<Coordinate>,
    val date: Date,
    val isForBusiness: Boolean
)