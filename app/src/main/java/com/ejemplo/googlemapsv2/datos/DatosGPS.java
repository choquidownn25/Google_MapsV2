package net.proyecto.sigti.datos;

/**
 * Created by choqu_000 on 25/01/2016.
 */
public class DatosGPS {

    private String horas;
    private String fecha;
    private String lat;
    private String lon;
    private float latitud;
    private float longitud;
    private String rehoras;
    private String cedula;
    private String gcm_regid;
    private String email;
    private String name;

    //Cosntructor
    public DatosGPS(){

    }
    //Encapsulamiento


    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getRehoras() {
        return rehoras;
    }

    public void setRehoras(String rehoras) {
        this.rehoras = rehoras;
    }

    public String getCedula() {
        return cedula;
    }

    public String setCedula(String cedula) {
        this.cedula = cedula;
        return cedula;
    }

    public String getGcm_regid() {
        return gcm_regid;
    }

    public void setGcm_regid(String gcm_regid) {
        this.gcm_regid = gcm_regid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
