package net.proyecto.sigti.datos;

/**
 * Created by choqu_000 on 11/03/2016.
 */
public class OrdennoReparada {

    //Atributo
    private int tipoOrden;
    public int ingresoOrdenServicios;
    //Constructor
    public OrdennoReparada(){

    }

    //Encapsulamiento
    public int getTipoOrden() {
        return tipoOrden;
    }


    public int setTipoOrden(int tipoOrden) {
        this.tipoOrden = tipoOrden;
        ingresoOrdenServicios = tipoOrden;
        getOtengoNumeroOrden(tipoOrden);
        return tipoOrden;
    }

    public int getOtengoNumeroOrden(int tipoOrden){
        return tipoOrden;
    }

    public int obtengoOrde(){
        getOtengoNumeroOrden(tipoOrden);
        return tipoOrden;
    }
}
