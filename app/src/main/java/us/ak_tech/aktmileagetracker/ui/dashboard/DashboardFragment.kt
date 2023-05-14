package us.ak_tech.aktmileagetracker.ui.dashboard

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.Coordinate
import us.ak_tech.aktmileagetracker.R
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.databinding.FragmentDashboardBinding
import us.ak_tech.aktmileagetracker.ui.details.TripDetailViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var trip: Trip
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding =
            FragmentDashboardBinding.inflate(inflater, container, false)
        binding.rcvTrips.layoutManager = LinearLayoutManager(context)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val trips = dashboardViewModel.loadTrips()
                binding.rcvTrips.adapter = TripListAdapter(trips) { tripId ->
                    val sharedViewModel: TripDetailViewModel by activityViewModels()
                    sharedViewModel.setText("TEST")
                    findNavController().navigate(R.id.show_crime_details)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_dashboard, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_trip -> {
                showNewTrip()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewTrip() {
        viewLifecycleOwner.lifecycleScope.launch {
            val id = UUID.randomUUID()
            val newTrip = Trip(
                id,
                MutableList(1, init = {
                    Coordinate(id, 0, 7.0, 17.0)
                }),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                true,
            )
            dashboardViewModel.addTrip(newTrip)
            findNavController().navigate(
                DashboardFragmentDirections.showCrimeDetails(newTrip.id)
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}