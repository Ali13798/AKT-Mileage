package us.ak_tech.aktmileagetracker.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.databinding.FragmentTripListItemBinding


class TripHolder(
    val binding: FragmentTripListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(trip: Trip) {
        binding.textView2.text = trip.date.toString()
        binding.textView9.text = trip.coordinates[0].toString()
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${trip.date.toString()} clicked!",
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