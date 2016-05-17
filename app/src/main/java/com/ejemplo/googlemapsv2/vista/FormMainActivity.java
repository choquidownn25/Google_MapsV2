package com.ejemplo.googlemapsv2.vista;

/**
 * Created by choqu_000 on 17/05/2016.
 */
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import net.proyecto.sigti.R;
import com.ejemplo.googlemapsv2.R;
import com.ejemplo.googlemapsv2.interfaces.OnTaskCompleted;
import com.ejemplo.googlemapsv2.mapas.MyMapFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by choqu_000 on 25/03/2016.
 * Clase para el formulario
 */
public class FormMainActivity  extends ActionBarActivity implements OnTaskCompleted {
    //private static String APP_TAG = "APIRutasGoogle";
    MyMapFragment myMapFrag;
    MainFragment myMainFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //scoetContentView(R.layout.activity_main);
        setContentView(R.layout.ruta_mapa_activity_main);
        //Bloquear orientación de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (savedInstanceState == null) {
            myMainFrag = new MainFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container, myMainFrag);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Elemento de la barra de acción de la manija hace clic aquí. La barra de acción
        // Manipulador automáticamente los clics en el botón Inicio / Arriba, siempre
        // Como se especifica una actividad principal en AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_back) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(List<List<HashMap<String, String>>> listLatLong) {
        if (listLatLong != null && listLatLong.size() > 0) {
            myMapFrag= new MyMapFragment();
            myMapFrag.setRoutes(listLatLong);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, myMapFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Toast.makeText(this, "Oops!, No se logro determinar ruta ;(", Toast.LENGTH_LONG).show();
        }

    }
}

