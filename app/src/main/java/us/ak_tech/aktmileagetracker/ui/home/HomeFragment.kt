package us.ak_tech.aktmileagetracker.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.Coordinate
import us.ak_tech.aktmileagetracker.R
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.databinding.FragmentHomeBinding
import us.ak_tech.aktmileagetracker.ui.details.TAG
import java.time.LocalDateTime
import java.util.*

class HomeFragment : Fragment(), LocationListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var locationManager: LocationManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            btnStart.setOnClickListener {
                toggleRecording()
            }
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
    }

    private fun toggleRecording() {
        if (homeViewModel.isTripRecording)
            stopRecording()
        else
            startRecording()
    }

    private fun startRecording() {
        binding.btnStart.text = getString(R.string.stop_trip)
        homeViewModel.apply {
            isTripRecording = true
            coordinateIndex = 0
            tripId = UUID.randomUUID()
            startDate = LocalDateTime.now()
        }
        getLocation()
    }

    private fun stopRecording() {
        binding.btnStart.text = getString(R.string.start_trip)
        homeViewModel.apply {
            isTripRecording = false
            endDate = LocalDateTime.now()

            if (coordinateIndex == 0) {
                reset()
                return
            }
            var newTrip = Trip(tripId!!, coordinates, startDate!!, endDate!!, isBusinessTrip)
            trip = newTrip
            viewLifecycleOwner.lifecycleScope.launch {
                homeViewModel.addTrip(newTrip)
            }
        }
        reset()
    }

    fun reset() {
        homeViewModel.apply {
            tripId = null
            startDate = null
            endDate = null
            isBusinessTrip = false
            coordinateIndex = 0
            coordinates = mutableListOf<Coordinate>()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopRecording()
        _binding = null
    }

    override fun onLocationChanged(location: Location) {
        if (!homeViewModel.isTripRecording) return

        if (homeViewModel.tripId == null) {
            homeViewModel.tripId = UUID.randomUUID()
        }

        var id = homeViewModel.tripId!!
        val coordinate =
            Coordinate(id, homeViewModel.coordinateIndex++, location.longitude, location.latitude)
        Log.d(TAG, coordinate.toString())

        homeViewModel.coordinates += coordinate
    }

    private fun getLocation() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5f, this)
    }


}