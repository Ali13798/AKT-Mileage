package us.ak_tech.aktmileagetracker.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.databinding.FragmentTripListItemBinding
import java.time.Duration
import java.time.format.DateTimeFormatter


class TripHolder(
    val binding: FragmentTripListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(trip: Trip) {
        val dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val duration = Duration.between(trip.startDate, trip.endDate)
        val tvDateText =
            if (duration.toHours() < 24.0 && trip.endDate.hour > trip.startDate.hour)
                trip.startDate.format(dateFormatter)
            else
                "${trip.startDate.format(dateFormatter)} - ${trip.endDate.format(dateFormatter)}"

        binding.tvDate.text = tvDateText
        binding.tvStartTime.text = trip.startDate.format(hourFormatter)
        binding.tvEndTime.text = trip.endDate.format(hourFormatter)
        binding.tvDistance.text = trip.coordinates[0].toString()
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "$tvDateText clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


class TripListAdapter(private val trips: List<Trip>) : RecyclerView.Adapter<TripHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentTripListItemBinding.inflate(inflater, parent, false)
        return TripHolder(binding)
    }

    override fun getItemCount() = trips.size

    override fun onBindViewHolder(holder: TripHolder, position: Int) {
        val trip = trips[position]
        holder.apply {
            holder.bind(trip)
        }
    }
}