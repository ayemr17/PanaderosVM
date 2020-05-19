package com.example.panaderosvm.utils.permisos

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.lang.Exception

class PermisosUbicacion {

    var fusedLocationClient : FusedLocationProviderClient? = null
    var activity : Activity? = null
    var ctx : Context? = null

    val fineLocationPermission = android.Manifest.permission.ACCESS_FINE_LOCATION
    val coarseLocationPermission = android.Manifest.permission.ACCESS_COARSE_LOCATION
    val REQUEST_PERMISSION_CODE_LOCATION = 1234
    var locationRequest : LocationRequest? = null
    var callback : LocationCallback? = null

    // livedata para pasar info a las vistas de forma limpia
    var lastLocation : MutableLiveData<Location> = MutableLiveData()
    var actualLocation : MutableLiveData<Location> = MutableLiveData()

    fun checkPermissionLocation(activity: AppCompatActivity) : Boolean {
        var isActiva = false
        this.activity = activity
        this.ctx = activity
        fusedLocationClient = FusedLocationProviderClient(ctx as Activity)

        if (okPermission(fineLocationPermission) && okPermission(coarseLocationPermission)) {
            isActiva = true
            /*catchLocationcontinua()*/
        } else {
            solicitarPermiso(fineLocationPermission, REQUEST_PERMISSION_CODE_LOCATION)
        }
        return isActiva
    }

    //TODO: region para pedir permisos de forma generica pasandole los valores nesesarios
    private fun okPermission(permiso : String): Boolean {
        val isFineLocation = ctx?.let {
            ActivityCompat.checkSelfPermission(
                it, permiso
            )
        } == PackageManager.PERMISSION_GRANTED
        return isFineLocation
    }

    private fun solicitarPermiso(permiso : String, code : Int) {
        val ctxProvide = activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, permiso) }
        if (ctxProvide == true) {
            // mandamos msj con explicacion de porque necesitamos el permiso (ya que lo rechazo)
            sendPermission(permiso, code)
        } else {
            sendPermission(permiso, code)
        }
    }

    private fun sendPermission(permisos : String, code : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.requestPermissions(arrayOf(permisos), code)
        }
    }
    // TODO: Fin region pedir permisos generica ------------------------------------


    fun initLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    //TODO: capturar ultima ubicacion del telefono (no necesariamente tiene que ser la actual)
    fun catchLocation() {
        activity?.let {
            fusedLocationClient?.lastLocation?.addOnSuccessListener(
                it) { location ->
                if (location != null) {
                    // TODO: guardar los datos en un room o sharedpreference (FALTA)

                    //mientras, lo devolvemos en la funcion al dato para poder ser usado
                    lastLocation.postValue(location)
                }
            }
        }
    }

    //TODO: conseguir ubicacion actual con actualizacion luego de iniciar el servicio
    fun catchLocationcontinua() {
        callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (locationResult != null) {
                    for (ubicacion in locationResult.locations) {
                        actualLocation.postValue(ubicacion)
                        Toast.makeText(activity, ubicacion.latitude.toString() + " - " + ubicacion.longitude.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, callback, null)
    }

    //TODO: es para frenar el servicio de actualizacion de ubicacion si la app se detiene
    fun stopCatckLocationContinua() {
        try {
            fusedLocationClient?.removeLocationUpdates(callback)
        } catch (e : Exception) {
            //Toast.makeText(ctx, "paso por el error del stopcatchLocation()", Toast.LENGTH_SHORT).show()
        }
    }

}