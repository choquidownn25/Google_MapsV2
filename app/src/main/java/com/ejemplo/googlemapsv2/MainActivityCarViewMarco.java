package com.ejemplo.googlemapsv2;

/**
 * Created by choqu_000 on 17/05/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ejemplo.googlemapsv2.adaptador.GridViewAdapter;
import com.ejemplo.googlemapsv2.ui.GridAdapter;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by choqu_000 on 03/02/2016.
 */
public class MainActivityCarViewMarco extends ActionBarActivity {

    //Atributos
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;


    //Atributos
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ImageButton OrdenServicio;
    private ImageButton Auditoria;
    //Creamos la vista de la actividad

    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = sdf.format(c.getTime());

    private float latitudes;
    private float longitudes;

    String regid;
    String msg;

    //public MyReceiver miRecibimiento;

    private static final int ALARM_REQUEST_CODE = 1;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;

    int mPosition = -1;
    String mTitle = "";
    //Atributos
    private String horas;
    private String fecha;
    private String lat;
    private String lon;
    private String latitud;
    private String longitud;
    private String rehoras;
    // Array of strings storing country names
    String[] mCountries ;


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawer ;
    private List<HashMap<String,String>> mList ;
    //private SimpleAdapter mAdapter;
    final private String COUNTRY = "country";
    final private String FLAG = "flag";
    final private String COUNT = "count";
    private String[] titulos;
    private String mActivityTitle;


    Calendar HoraSystem = Calendar.getInstance();
    SimpleDateFormat foratoTiempo = new SimpleDateFormat("hh:mm:ss");
    String strHoradelSistema = foratoTiempo.format(HoraSystem.getTime());

    GoogleCloudMessaging gcm; //Atributo para llamar el servicio Google
    //Creamos la vista de la actividad
    static LocationManager locationManager;
    //static MyLocationListener miLocationListener;
    private LocationManager locManager;
    private LocationListener locListener;
    //private NotificaAlMainActivity objteoMainActivityNotificacion;
    String nombrecel;

    //Equipo Registrado
    public String id_gcm_Google_Messeging;
    Context context;
    private String User_name;
    static final String TAG = "pavan";
    private String gcm_regid;
    private InputStream is;
    //Metodo para crear la vista
    private String Dato_id_usuarioNotificacion;
    private String Dato_Id_Google_Messeging;
    private String Dato_id_Gooogle_email;



    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main_carview_marco);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Intacia de Clase Contex que accedemos recursos
        //objteoMainActivityNotificacion = new NotificaAlMainActivity();
        fecha = strDate;
        context = getApplicationContext();


        //Mensaje
        //new Insertar(GridActiviti.this).execute();
        context = getApplicationContext();


        alName = new ArrayList<>(Arrays.asList("Posicion en el Mapa", "Direciones"));
        alImage = new ArrayList<>(Arrays.asList(R.drawable.mapas_icono, R.drawable.googlemapsicno));



        // llamando el RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // El numero de Columnas
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter(MainActivityCarViewMarco.this, alName, alImage);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }



}
