package com.example.panaderosvm.utils

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.example.panaderosvm.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class Mapas(mapa : GoogleMap?, ctx : Context) {
    //TODO: definimos marcadores globales para poder ser cargados en onMapReady con las coordenadas creadas y en onMapClick poder ser usados
    var marcadorUno: Marker? = null
    var marcadorDos: Marker? = null
    var marcadorTres: Marker? = null

    var mMap : GoogleMap? = null
    var context : Context? = null

    private var listaMarcadores: ArrayList<Marker>? = null

    init {
        this.mMap = mapa
        this.context = ctx
    }

    fun habilitarBoton() {
        //TODO: activo boton para que me lleve el mapa a mi ubicacion
        mMap?.isMyLocationEnabled = true
        mMap?.uiSettings?.isMyLocationButtonEnabled = true
    }

    fun marcadoresEstaticos() {
        val marker1 = LatLng(-35.0, -83.0)
        val marker2 = LatLng(-34.4, -84.9)
        val marker3 = LatLng(-34.0, -64.5)

        marcadorUno = mMap?.addMarker(
            MarkerOptions()
                .position(marker1)
                //cambiamos color del icono
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                //cambiamos opacidad y agremos transparencia
                .alpha(0.3f)
                .title("Prueba 1")
                .snippet("Esto es para ver una descripcion mas detallada")
        )
        marcadorUno?.tag = 0
        marcadorDos = mMap?.addMarker(
            MarkerOptions()
                .position(marker2)
                //cambiamos color del icono
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                //cambiamos opacidad y agremos transparencia
                .alpha(0.6f)
                .title("Prueba 2")
                .snippet("Esto es para ver una descripcion mas detallada")
        )
        marcadorDos?.tag = 1
        marcadorTres = mMap?.addMarker(
            MarkerOptions()
                .position(marker3)
                //cambiamos color del icono
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                //cambiamos opacidad y agremos transparencia
                .alpha(0.9f)
                //cambiar la imagen q se usa para el marcador (si o si formato Bitmap, no vector ni nada)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone))
                .title("Prueba 3")
                .snippet("Esto es para ver una descripcion mas detallada")
        )
        marcadorTres?.tag = 2
    }

    fun dibujarLineas() {
        //TODO: aca estamos creando una polilinea y estamos haciendo que aparezca en el mapa
        var coordenadas = PolylineOptions()
            .add(LatLng(-36.234234, -64.234434))
            .add(LatLng(-36.237677, -64.998787))
            .add(LatLng(-36.666545, -64.345433))
            .add(LatLng(-36.35443, -64.35454))
            .pattern(arrayListOf<PatternItem>(Dot(), Gap(20f)))
            .color(Color.CYAN)
            .width(30f)

        mMap?.addPolyline(coordenadas)

        //TODO: aca estamos creando un poligono y haciendo que aparezca en el mapa
        var coordenadas2 = PolygonOptions()
            .add(LatLng(-34.234234, -64.234434))
            .add(LatLng(-34.237677, -64.998787))
            .add(LatLng(-34.666545, -64.345433))
            .add(LatLng(-34.35443, -64.35454))
            .strokePattern(arrayListOf(Dash(10f), Gap(20f)))
            .strokeColor(Color.RED)
            .fillColor(Color.GREEN)
            .strokeWidth(10f)

        mMap?.addPolygon(coordenadas2)

        //TODO: aca estamos creando un circulo y estamos haciendo que aparezca en el mapa
        var coordenadas3 = CircleOptions()
            .center(LatLng(-50.234234, -64.234434))
            .radius(200.0)

        mMap?.addCircle(coordenadas3)
    }

    fun prepararMarcadores(home : LatLng?) {
        listaMarcadores = ArrayList()

        mMap?.setOnMapLongClickListener { location ->
            mMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    //cambiamos color del icono
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    //cambiamos opacidad y agremos transparencia
                    .alpha(0.3f)
            )?.let {
                listaMarcadores?.add(
                    it
                )
            }
            listaMarcadores?.last()?.isDraggable = true
            val coordenadas =
                listaMarcadores?.last()?.position?.let {
                    LatLng(it.latitude,
                        it.longitude
                    )
                }

            //val origen = "origin=" + home?.latitude + "," + home?.longitude +"&"
            //val destino = "destination=" + coordenadas?.latitude + "," + coordenadas?.longitude + "&"
            //val parametros = origen + destino + "sensor=false&mode=driving&key=" + context?.getString(
            //   R.string.google_maps_key)

            //Log.e("URL", "https://maps.googleapis.com/maps/api/directions/json?" + parametros)
            //cargarUrl("https://maps.googleapis.com/maps/api/directions/json?" + parametros)
        }
    }

    //TODO: vamos a generar la consulta a api de google para marcar ruta entre dos puntos
    /*fun cargarUrl(url: String) {
        val queue = Volley.newRequestQueue(context)

        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            Log.e("HTTP", response)
        }, Response.ErrorListener { })

        queue.add(solicitud)
    }*/
}