package us.ak_tech.aktmileagetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Coordinate(
    @PrimaryKey val tripId: UUID,
    val index: Int,
    val x: Double,
    val y: Double
)