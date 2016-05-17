package net.proyecto.sigti.datos;

/**
 * Created by choqu_000 on 24/01/2016.
 */
public class DatosFoto {

    private String cedula;
    private String cliente;
    private String foto;
    private String fechacreacion;
    private float latitud;
    private float longitud;
    private String hora;
    private int id_orden_servicio;
    private int numero_wo;
    private String nombre;
    private String gcm_regid;

    //Constructor
    public DatosFoto(){

    }

    //Encapsulamiento/Propiedad
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getId_orden_servicio() {
        return id_orden_servicio;
    }

    public void setId_orden_servicio(int id_orden_servicio) {
        this.id_orden_servicio = id_orden_servicio;
    }

    public int getNumero_wo() {
        return numero_wo;
    }

    public void setNumero_wo(int numero_wo) {
        this.numero_wo = numero_wo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGcm_regid() {
        return gcm_regid;
    }

    public void setGcm_regid(String gcm_regid) {
        this.gcm_regid = gcm_regid;
    }
}
