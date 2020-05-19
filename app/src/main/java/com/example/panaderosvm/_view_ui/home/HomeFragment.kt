package com.example.panaderosvm._view_ui.home

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.panaderosvm.R
import com.example.panaderosvm.utils.Mapas
import com.example.panaderosvm.utils.permisos.PermisosUbicacion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mMap: GoogleMap
    var permisosUnicacion: PermisosUbicacion =
        PermisosUbicacion()
    var mapsClass : Mapas? = null
    var location: Location? = null
    var home : LatLng? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initObservers()

        //homeViewModel.cargarListaDummyPueblos()
    }

    private fun initObservers() {
        permisosUnicacion.lastLocation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { list ->
                list?.let { ox ->

                    // Add a marker in Sydney and move the camera
                    if (home == null) {
                        home = LatLng(ox.latitude, ox.longitude)
                        mMap.addMarker(MarkerOptions()
                            .position(home!!)
                            .title("Estas aqui")
                            .snippet("Esta es tu ultima ubicacion conocida"))
                    } else {
                        home = LatLng(ox.latitude, ox.longitude)
                        mMap.addMarker(MarkerOptions()
                            .position(home!!)
                            .title("Estas aqui")
                            .snippet("Esta es tu ultima ubicacion conocida"))
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(home))

                }
            })

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
        }
        mapsClass = activity?.applicationContext?.let { Mapas(mMap, it) }

        if (permisosUnicacion.checkPermissionLocation(activity  as AppCompatActivity)) {
            mapsClass?.habilitarBoton()
            //permisosUnicacion.initLocationRequest()
            permisosUnicacion.catchLocation()
            //permisosUnicacion.catchLocationcontinua()
        }

        //mapsClass?.marcadoresEstaticos()

        initListeners()

        //mapsClass?.prepararMarcadores(home)

        //mapsClass?.dibujarLineas()
    }

    private fun initListeners() {
        mMap.setOnMarkerClickListener {
            Toast.makeText(
                activity?.applicationContext,
                it?.title.toString(),
                Toast.LENGTH_SHORT
            ).show()

            return@setOnMarkerClickListener true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            //TODO: vemos si el codigo del permiso aceptado corresponde al de la ubicacion
            permisosUnicacion.REQUEST_PERMISSION_CODE_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // obtenemos la ubicacion
                    mapsClass?.habilitarBoton()
                    permisosUnicacion.catchLocation()
                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "No diste el permiso para acceder a la ubicacion",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}
