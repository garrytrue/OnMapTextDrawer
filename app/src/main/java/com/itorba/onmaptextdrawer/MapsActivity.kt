package com.itorba.onmaptextdrawer

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var drawer: TextDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        drawer = TextDrawer()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Create bitmap for marker
        val styler = getTextStyler()

        val bitmap = drawer.drawText("My Awesome Marker", styler)

        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val markerOptions = getMarkerOptions(sydney, bitmapDescriptor, styler)
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun getTextStyler(): TextStyler {
        val textStyler = TextStyler()
        textStyler.setColor(Color.BLUE)
        textStyler.setAnchor(TextStyler.TextAnchor.RIGHT)
        textStyler.setOpacity(0.8F)
        textStyler.setSize(60)
        textStyler.setMaxWidth(700)
        textStyler.setRotation(0F)
        textStyler.setOffset(Point(80, 0))
        return textStyler
    }

    private fun getMarkerOptions(latLng: LatLng, icon: BitmapDescriptor, styler: TextStyler): MarkerOptions =
            MarkerOptions()
                    .position(latLng)
                    .icon(icon)
                    .rotation(styler.getRotation())
                    .anchor(0.5F, 0.5F)
                    .zIndex(4.258F)


}
