package us.ak_tech.aktmileagetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Coordinates")
data class Coordinate(
    @PrimaryKey
    val tripId: UUID,
    val index: Int,
    val lon: Double,
    val lat: Double
)