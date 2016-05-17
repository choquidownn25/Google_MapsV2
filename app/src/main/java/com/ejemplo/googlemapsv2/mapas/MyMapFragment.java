package com.ejemplo.googlemapsv2.mapas;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplo.googlemapsv2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

//import net.proyecto.sigti.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import samplems.kickflip.io.com.pruebamapa.R;

/**
 * Created by choqu_000 on 25/03/2016.
 * Clase del mapa
 */

@SuppressWarnings("MissingPermission")
public class MyMapFragment extends Fragment {

    private GoogleMap mMap;
    private List<List<HashMap<String, String>>> routes;

    public MyMapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUpMapIfNeeded();
        return rootView;
    }

    private void setUpMapIfNeeded() {
        // Configuramos el objeto GoogleMaps con valores iniciales.
        if (this.mMap == null) {

            //Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
            this.mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
            if (this.mMap != null) {
                // El objeto GoogleMap ha sido referenciado correctamente
                //ahora podemos manipular sus propiedades

                //Seteamos el tipo de mapa
                this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //Activamos la capa o layer MyLocation
                this.mMap.setMyLocationEnabled(true);

                //Disbujamos la ruta
                drawRoutes(this.routes);

            }
        }
    }

    public GoogleMap getMyMap() {
        setUpMapIfNeeded();
        return this.mMap;
    }

    public List<List<HashMap<String, String>>> getRoutes() {
        return routes;
    }

    public void setRoutes(List<List<HashMap<String, String>>> routes) {
        this.routes = routes;
    }

    private void drawRoutes(List<List<HashMap<String, String>>> result) {
        LatLng center = null;
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;

        setUpMapIfNeeded();

        // Traversing through all the routes
        for(int i=0;i<result.size();i++){
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for(int j=0;j<path.size();j++){
                HashMap<String,String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                if (center == null) {
                    //Obtengo la primera coordenada para centrar el mapa en la misma.
                    center = new LatLng(lat, lng);
                }

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(5);
            lineOptions.color(Color.BLUE);
        }

        // Drawing polyline in the Google Map for the i-th route
        mMap.addPolyline(lineOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 13));
    }
}