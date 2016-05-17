package com.ejemplo.googlemapsv2.mapas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.ejemplo.googlemapsv2.R;
import com.ejemplo.googlemapsv2.json.DirectionsJSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import net.proyecto.sigti.R;
//import net.proyecto.sigti.contenga.Contenedora;
//import net.proyecto.sigti.notifica.MainActivityNotificacion;
//import net.proyecto.sigti.notifica.MyReceiver;
//import net.proyecto.sigti.notifica.Util;

/**
 * Created by choqu_000 on 22/09/2015.
 */
public class MapaRuta extends FragmentActivity implements LocationListener {
    GoogleMap mGoogleMap;
    ArrayList<LatLng> mMarkerPoints;
    double mLatitude = 0;
    double mLongitude = 0;
    private static final int ALARM_REQUEST_CODE = 1;

    //Equipo Registrado
    public String id_gcm_Google_Messeging;
    Context context;
    private String User_name;
    static final String TAG = "pavan";
    static final String RED = "Interferencia";

    //private Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_activity_main);

        context = getApplicationContext();
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Initializing
            mMarkerPoints = new ArrayList<LatLng>();

            // Getting reference to SupportMapFragment of the activity_main
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting Map for the SupportMapFragment
            mGoogleMap = fm.getMap();

            // Enable MyLocation Button in the Map
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
            mGoogleMap.setMyLocationEnabled(true);


            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            //String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            //Location location = locationManager.getLastKnownLocation(provider);

            //if (location != null) {
            //    onLocationChanged(location);
            //}

            //locationManager.requestLocationUpdates(provider, 20000, 0, this);

            // Setting onclick event listener for the map
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    // Already map contain destination location
                    if (mMarkerPoints.size() > 1) {

                        FragmentManager fm = getSupportFragmentManager();
                        mMarkerPoints.clear();
                        mGoogleMap.clear();
                        LatLng startPoint = new LatLng(mLatitude, mLongitude);
                        drawMarker(startPoint);
                    }

                    drawMarker(point);

                    // Checks, whether start and end locations are captured
                    if (mMarkerPoints.size() >= 2) {
                        LatLng origin = mMarkerPoints.get(0);
                        LatLng dest = mMarkerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });
        }
        /*
        if(estaRegistrado(context)) {
            //establecerAlarmaClickMasMinutos(7);
            cargarNotificacionCantidaddeOrden();
        }else{
            startActivity(new Intent(MapaRuta.this, MainActivity.class));
            finish();
        }
        */

    }

    /*
    //Metodo en la cual  esta o no registrado
    public boolean estaRegistrado(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        User_name = prefs.getString(Util.USER_NAME, "");
        String DatonombreTecCelular = "Jose";
        String datoRegistrogmc_id_google_messeging = "APA91bHSdgAJ9m1PvSAutUwXD5GbbsGG-UyrYxRQ_t8akgvdPR5Q0OJkCJMJNukcanGFSTMqVZ2mDNKgupD9t2cDr-x2tZhGDqmlKuWYCQw7_brIryMgQ0zEd6NA7zZGQTyd-BDCCT-G";
        id_gcm_Google_Messeging = prefs.getString(Util.PROPERTY_REG_ID, "");
        String gcm_id_elec = id_gcm_Google_Messeging;
        if (id_gcm_Google_Messeging.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return false;
        }
        if(id_gcm_Google_Messeging.equals(datoRegistrogmc_id_google_messeging)){
            Log.i(TAG, "Registration not found.");
            return true;
        }

        if(User_name.equals(DatonombreTecCelular)){
            Log.i(TAG, "Registration not found.");
            return true;
        }


        return false;
    }

    //
    private SharedPreferences getGCMPreferences(Context context) {
        // Esta aplicacion muestra persiste el ID de registro en las preferencias compartidas
        // Como se almacena el ID de registro en su aplicacion depende de usted.
        return getSharedPreferences(MainActivityNotificacion.class.getSimpleName(),Context.MODE_PRIVATE);
        //return getSharedPreferences(VistaFormularioEjecuciones.class.getSimpleName(), Context.MODE_PRIVATE);
    }
    //
    private void cargarNotificacionCantidaddeOrden(){
        Intent intent = new Intent(MapaRuta.this, Contenedora.class);
        startActivity(intent);
    }

    //Medoto para cantidad de carga
    private void establecerAlarmaClickMasMinutos(int i) {
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

//         (Modo no acoplado con un componente, ver AndroidManifest.xml)
//         Intent        intent  = new Intent("es.carlos_garcia.tutoriales.android.alarmmanager");

        Intent intent  = new Intent(this, MyReceiver.class);
        //Intent        intent  = new Intent(this, miResibimiento.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + i * 1000, pIntent);

        showAlert();
    }

    */

    //Metodo para mostrar el metodo o mensaje de ingresado o autulizado
    public void showAlert(){
        MapaRuta.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapaRuta.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    //Metodo de obtener direcciones
    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    /**
     * A class to download data from Google Directions URL
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Directions in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {

                String[] datoJson = jsonData;

                if(datoJson[0]!= ""){

                    jObject = new JSONObject(jsonData[0]);


                    DirectionsJSONParser parser = new DirectionsJSONParser();

                    // Starts parsing data
                    routes = parser.parse(jObject);
                }else {
                    Toast.makeText(context, "Error de Conexion", Toast.LENGTH_LONG).show();
                }


            } catch (ArrayIndexOutOfBoundsException e){
                Log.d("JSON Parser", "Error parsing data ");

            } catch (Exception e) {
                //Log.e("JSON Parser", "Error parsing data ");
                Log.d("JSON Parser", "Error parsing data ");
                //e.printStackTrace();
                //showErrorVistaInternetIntermitente();
                System.out.print(e.toString());
            }

            return routes;

        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            try{



            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.BLUE);

            }


            // Drawing polyline in the Google Map for the i-th route
            mGoogleMap.addPolyline(lineOptions);

            }catch (Exception e){
                Log.d("JSON Parser", "Error parsing data ");
                showErrorVistaInternetIntermitente();
            }
        }
    }


    private void drawMarker(LatLng point) {
        mMarkerPoints.add(point);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(point);

        /**
         * For the start location, the color of marker is GREEN and
         * for the end location, the color of marker is RED.
         */
        if (mMarkerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (mMarkerPoints.size() == 2) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        // Add new marker to the Google Map Android API V2
        mGoogleMap.addMarker(options);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Draw the marker, if destination location is not set
        if (mMarkerPoints.size() < 2) {

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            LatLng point = new LatLng(mLatitude, mLongitude);

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

            drawMarker(point);
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    //Metodo de error en internet
    private void showErrorVistaInternetIntermitente(){
        MapaRuta.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapaRuta.this);
                builder.setTitle("SIGTI.");
                builder.setMessage("No hay Conexion.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
}
