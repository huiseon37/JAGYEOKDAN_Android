package com.example.happylife

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    private val REQUEST_LOCATION_PERMISSION = 1

    private lateinit var placesClient: PlacesClient

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var defaultLocation = LatLng(37.56, 126.97)
    private var locationPermissionGranted = false

    private var lastKnownLocation: Location? = null
    private var likelyPlaceNames: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAddresses: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAttributions: Array<List<*>?> = arrayOfNulls(0)
    private var likelyPlaceLatLngs: Array<LatLng?> = arrayOfNulls(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 저장된 인스턴스 상태에서 위치 및 카메라 위치 검색
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        setContentView(R.layout.activity_maps)

        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // map 생성
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map?.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.current_place_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_get_place) {
            showCurrentPlace()
        }
        return true
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

        defaultLocation = LatLng(37.56, 126.97)

        this.map?.setInfoWindowAdapter(object : InfoWindowAdapter {

            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {

                val infoWindow = layoutInflater.inflate(
                    R.layout.custom_info_contents,
                    findViewById<FrameLayout>(R.id.map), false
                )
                val title = infoWindow.findViewById<TextView>(R.id.title)
                title.text = marker.title
                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet
                return infoWindow
            }
        })

        // 사용자에게 권한 요청
        getLocationPermission()

        // 지도에서 내 위치와 관련된 컨트롤 켬
        updateLocationUI()

        // 장치의 현재 위치를 가져와 지도 위치 설정
        getDeviceLocation()

        // 내 위치 설정
        enableMyLocation()
    }

    private fun getDeviceLocation() {

        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 지도의 카메라 위치를 장치의 현재 위치로 설정
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun showCurrentPlace() {
        if (map == null) {
            return
        }
        if (locationPermissionGranted) {

            val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val request = FindCurrentPlaceRequest.newInstance(placeFields)
            val placeResult = placesClient.findCurrentPlace(request)

            placeResult.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val likelyPlaces = task.result

                    val count =
                        if (likelyPlaces != null && likelyPlaces.placeLikelihoods.size < M_MAX_ENTRIES) {
                            likelyPlaces.placeLikelihoods.size
                        } else {
                            M_MAX_ENTRIES
                        }
                    var i = 0

                    likelyPlaceNames = arrayOfNulls(count)
                    likelyPlaceAddresses = arrayOfNulls(count)
                    likelyPlaceAttributions = arrayOfNulls<List<*>?>(count)
                    likelyPlaceLatLngs = arrayOfNulls(count)

                    for (placeLikelihood in likelyPlaces.placeLikelihoods) {
                        // 사용자에게 표시할 수 있는 장소 목록
                        likelyPlaceNames[i] = placeLikelihood.place.name
                        likelyPlaceAddresses[i] = placeLikelihood.place.address
                        likelyPlaceAttributions[i] = placeLikelihood.place.attributions
                        likelyPlaceLatLngs[i] = placeLikelihood.place.latLng
                        i++
                        if (i > count - 1) {
                            break
                        }
                    }

                    // 사용자에게 가능한 장소 목록을 제공하는 대화 상자를 표시
                    // 선택한 장소에 마커를 표시
                    openPlacesDialog()
                } else {
                }
            }
        } else {
            // 사용자가 권한을 부여하지 않은 경우 -> 기본 마커 추가
            map?.addMarker(
                MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(defaultLocation)
                    .snippet(getString(R.string.default_info_snippet))
            )

            // 사용자에게 권한 요청
            getLocationPermission()
        }
    }

    private fun openPlacesDialog() {
        // 사용자에게 현재 위치를 선택하도록 요청
        val listener =
            DialogInterface.OnClickListener { dialog, which ->
                val markerLatLng = likelyPlaceLatLngs[which]
                var markerSnippet = likelyPlaceAddresses[which]
                if (likelyPlaceAttributions[which] != null) {
                    markerSnippet = """
                    $markerSnippet
                    ${likelyPlaceAttributions[which]}
                    """.trimIndent()
                }

                // 정보 창을 사용하여 선택한 플레이스에 대한 마커 추가
                // 플레이스에 대한 정보 표시
                map?.addMarker(
                    MarkerOptions()
                        .title(likelyPlaceNames[which])
                        .position(markerLatLng!!)
                        .snippet(markerSnippet)
                )

                // 지도 카메라를 마커 위치에 놓음
                map?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        markerLatLng,
                        DEFAULT_ZOOM.toFloat()
                    )
                )
            }

        // 다이얼로그
        AlertDialog.Builder(this)
            .setTitle(R.string.pick_place)
            .setItems(likelyPlaceNames, listener)
            .show()
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
        }
    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // 활동 상태를 저장하기 위한 키
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        private const val M_MAX_ENTRIES = 5
    }
}

    