package com.ejemplo.googlemapsv2.mapas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.ejemplo.googlemapsv2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import net.proyecto.sigti.R;
import com.ejemplo.googlemapsv2.json.MarkerJSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by choqu_000 on 24/09/2015.
 */
public class MapaPosicion extends FragmentActivity {

    //Atributos
    GoogleMap googleMap;

    //crear la vista
    protected void onCreate(Bundle savedIstanceState) {
        super.onCreate(savedIstanceState);
        setContentView(R.layout.mapa_activity_posicion);
        //Obteniendo soporte a referencia
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //Creando el GoogleMap para el suppor frament
        googleMap = fragment.getMap();
        //Habilitando el botom de google maps
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        //Obtenga el evento onclikevent listener para el google maps
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //Adicionamos el marked
                addMarker(latLng);
                sendToServer(latLng);
            }
        });
        new RetrieveTask().execute();
    }
    //Metodo donde colocamoe el market
    private void addMarker(LatLng latLng){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latLng.latitude + ", " + latLng.longitude);
        googleMap.addMarker(markerOptions);

    }

    //Metodo deon invoca el el envio peticion del servidor
    private void sendToServer(LatLng latLng){
        new SaveTask().execute(latLng);
    }

    //Subproceso de fondo para guardar la ubicacion en quitar servidor MySQL
    private class SaveTask extends AsyncTask<LatLng, Void, Void> {
        @Override
        protected Void doInBackground(LatLng... params) {
            String lat = Double.toString(params[0].latitude);
            String lng = Double.toString(params[0].longitude);
            //String strUrl = "http://192.168.1.3/location_marker_mysql/save.php";
            String strUrl = "http://192.168.1.124/rutas/save.php";
            URL url = null;
            try {
                url = new URL(strUrl);

                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        connection.getOutputStream());

                outputStreamWriter.write("lat=" + lat + "&lng="+lng);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                InputStream iStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";

                while( (line = reader.readLine()) != null){
                    sb.append(line);
                }

                reader.close();
                iStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    // Background task to retrieve locations from remote mysql server
    private class RetrieveTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            String strUrl = "http://192.168.1.3/location_marker_mysql/retrieve.php";
            URL url = null;
            StringBuffer sb = new StringBuffer();
            try {
                url = new URL(strUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream iStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
                String line = "";
                while( (line = reader.readLine()) != null){
                    sb.append(line);
                }

                reader.close();
                iStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }

    }

    // Proceso hilo del lugar del JSON de los datos recuperados de MySQL server
    private class ParserTask extends AsyncTask<String, Void, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String,String>> doInBackground(String... params) {
            MarkerJSONParser markerParser = new MarkerJSONParser();
            JSONObject json = null;
            try {
                json = new JSONObject(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //List<HashMap<String, String>> markersList = markerParser.parse(json);
            List<HashMap<String, String>> markersList = markerParser.lugar(json);
            return markersList;
        }

        //Metodo para su ejecucion asingcrona
        protected void onPostExecute(List<HashMap<String, String>> result) {
            for(int i=0; i<result.size();i++){
                HashMap<String, String> marker = result.get(i);
                LatLng latlng = new LatLng(Double.parseDouble(marker.get("lat")), Double.parseDouble(marker.get("lng")));
                addMarker(latlng);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
//Metodo para la el Market
