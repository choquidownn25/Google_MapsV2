package com.ejemplo.googlemapsv2.json;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import com.ejemplo.googlemapsv2.interfaces.OnTaskCompleted;
import com.ejemplo.googlemapsv2.utilidad.Panel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by choqu_000 on 25/03/2016.
 * Clase json para el mapa
 */

public class ManageGoogleRoutes extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    public static String APP_TAG = "ManageGoogleRoutes ";
    public static String END_POINT = "http://maps.googleapis.com/maps/api/directions/json?";
    Context context;
    Activity activity;
    private Panel panel;
    private OnTaskCompleted listener;

    public ManageGoogleRoutes(OnTaskCompleted listener){
        this.listener=listener;
    }



    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... params) {
        return getRutasGoogle(params[0], params[1]);
    }

    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        if (result != null) {
            listener.onTaskCompleted(result);
        }//Aca sacamos el mensaje de Error por que no hay Conexion
    }

    private List<List<HashMap<String, String>>> getRutasGoogle(String pointA, String pointB) {
        //Se define el objeto URL
        JSONObject jsonObj = null;
        String  reqtUrl = null;
        BufferedReader in = null;
        List<List<HashMap<String, String>>> routes = null;

        try {
			/*http://maps.googleapis.com/maps/api/directions/json?
			 * origin=Calle Aribau, 185
			 * destination=Calle Industria, 104
			 * sensor=false
			 * mode=transit
			url = new URL(END_POINT);*/

            //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
            HttpClient httpClient = new DefaultHttpClient();

            //Configuramos los parametos que vamos a enviar con la peticion HTTP POST
            List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
            params.add(new BasicNameValuePair("origin", pointA));
            params.add(new BasicNameValuePair("destination", pointB));
            params.add(new BasicNameValuePair("sensor", "false"));
            params.add(new BasicNameValuePair("mode", "driving"));

            String strParams = URLEncodedUtils.format(params, "utf-8");
            reqtUrl = END_POINT + strParams;
            //Creamos objeto para armar peticion de tipo HTTP GET
            HttpGet getReq = new HttpGet(reqtUrl);
            //getReq.setHeader("Content-type", "application/json");

            //Se ejecuta el envio de la peticion y se espera la respuesta de la misma.
            HttpResponse response = httpClient.execute(getReq);
            Log.w(APP_TAG, response.getStatusLine().toString());

            //Obtengo el contenido de la respuesta en formato InputStream Buffer y la paso a formato String
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }

            String strJSON = sb.toString();
            jsonObj = new JSONObject(strJSON);
            routes  = parse(jsonObj);
            /*String encodedPoints = directionsResult.routes.get(0).overviewPolyLine.points;
    		latLngs = PolyUtil.decode(encodedPoints);*/
            Log.v(APP_TAG, strJSON);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {

                    in.close();
                    //
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return routes;
    }

    //Metodo para las rutas
    public List<List<HashMap<String,String>>> parse(JSONObject jObject){
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }
        return routes;
    }

    /**
     * Method to decode polyline points
     * Cotesía de: jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

}