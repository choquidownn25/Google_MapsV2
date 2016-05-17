package com.ejemplo.googlemapsv2.utilidad;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by choqu_000 on 26/03/2016.
 */
public class Panel extends Activity{

    Activity activity;
    Context context;
    static final String RED = "Interferencia";
    public Panel(Context context){
    this.context=context;
    }

    public void Paso(Context context){
        //startActivity(new Intent(activity.this, ToastPersonalizado.class));
        //ToastPersonalizado toastPersonalizado = new ToastPersonalizado();
        //Intent intent = new Intent(context.getApplicationContext(), ToastPersonalizado.class);
        if (context==null){
            Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_LONG).show();
        }

    }
}
