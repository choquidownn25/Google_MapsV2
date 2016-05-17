package net.proyecto.sigti.utilidad;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import net.proyecto.sigti.datos.OrdennoReparada;

/**
 * Created by choqu_000 on 22/07/2015.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    //Atetributos
    //private int nombreOrdenPasada;
    private OrdennoReparada ordennoReparada;
    private  int xypos;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "Evento : \n" + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
        ordennoReparada = new OrdennoReparada();
        ordennoReparada.setTipoOrden(position);
        ordennoReparada.getTipoOrden();
        int reporcion = position;
        getPosicion(reporcion);
    }

    public int getPosicion(int poscion){
        xypos = poscion;

        return xypos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getPosiciones() {
        return xypos;
    }
}

