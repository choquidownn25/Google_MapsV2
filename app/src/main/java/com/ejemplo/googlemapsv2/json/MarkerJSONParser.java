package com.ejemplo.googlemapsv2.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by choqu_000 on 24/09/2015.
 */
public class MarkerJSONParser {
    //REcibe un objetoJson y lo retorna a la lista
    public List<HashMap<String,String>> lugar (JSONObject jsonObject){
        JSONArray jMarkers= null; //Instanciamos
        try {
            //Revisa todos los elementos del markes del array
            jMarkers = jsonObject.getJSONArray("markers");
        }catch (JSONException e){
            e.printStackTrace();
        }
        
        //Invoca getMarkers con el array de json del objeto
        //donde cada json objeto representa un markers
        return getMarkers(jMarkers);
    }

    //Creamos el metodo de lista para obtener nuesto market
    private List<HashMap<String, String>> getMarkers(JSONArray jMarkers) {

        //Declaramos la atributo psandole el market como vector
        int markersCount = jMarkers.length();
        //Intanciamos la lista de esta estructura
        List<HashMap<String, String>>markersList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String>marker = null;
        //Tomando cada marker, para los lugares puestos en la lista
        for (int i = 0; i<markersCount; i++){
            try {
                //llama los getmakeres del objeto json
                marker = getMarker((JSONObject) jMarkers.get(i));
                markersList.add(marker);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return markersList;
    }

    //Metodo para marcar los objetos json
    private HashMap<String, String> getMarker(JSONObject jsonObject) {
        //Mapeo de la inf
        HashMap<String, String>marker = new HashMap<String, String>();

        String lat = "-NA-";
        String lng ="-NA-";

        try {
            //Extraiga la latidud
            if (!jsonObject.isNull("lat")){
                lat=jsonObject.getString("lat");
            }
            if (!jsonObject.isNull("lng")){
                lng = jsonObject.getString("lng");

            }
            marker.put("lat", lat);
            marker.put("lng", lng);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Retorna
        return marker;
    }
}