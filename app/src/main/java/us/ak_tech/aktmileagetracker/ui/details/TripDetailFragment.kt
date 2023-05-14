package us.ak_tech.aktmileagetracker.ui.details

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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import us.ak_tech.aktmileagetracker.R
import us.ak_tech.aktmileagetracker.Trip
import us.ak_tech.aktmileagetracker.databinding.FragmentTripDetailsBinding

class TripDetailFragment : Fragment(), OnMapReadyCallback, LocationListener {
    private var _binding: FragmentTripDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: TripDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel: TripDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.tripId)
    }

    private var map: GoogleMap? = null
    private var isLocationPermissionGranted = false

    private var lastKnownLocation: Location? = null
    private lateinit var locationManager: LocationManager

    var coords: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tripDetailViewModel =
            ViewModelProvider(this).get(TripDetailViewModel::class.java)

        _binding = FragmentTripDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        tripDetailViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.textNotifications.text = "TESTING"
        if (tripDetailViewModel.a == 0) tripDetailViewModel.setText("WHAT")
        tripDetailViewModel.a++
        Log.i("THISISMYTAG", tripDetailViewModel.text.value.toString())
        Log.i("THISISMYTAG", tripDetailViewModel.a.toString())

        val mapFragment = SupportMapFragment.newInstance()
        parentFragmentManager
            .beginTransaction()
            .add(R.id.mapView2, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimeDetailViewModel.trip.collect { trip ->
                    trip?.let {
                        updateUi(it)
                    }
                }
            }
        }

    }

    private fun updateUi(trip: Trip) {
        binding.apply {
            if (text4.text.toString() != trip.id.toString()) {
                text4.text = trip.id.toString()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
//        getLocationPermission()
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            isLocationPermissionGranted = true
            getLocation()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        isLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    isLocationPermissionGranted = true
                    getLocation()
                }
            }
            else -> {
                Toast.makeText(
                    activity,
                    "Permission Denied. Location permission required.",
                    Toast.LENGTH_SHORT
                ).show()
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        coords = LatLng(location.latitude, location.longitude)
        binding.textNotifications.text =
            "Latitude: " + location.latitude + " , Longitude: " + location.longitude

        map?.addMarker(
            MarkerOptions()
                .position(coords!!)
                .title("Marker")
        )
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(coords!!, 10.0f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}