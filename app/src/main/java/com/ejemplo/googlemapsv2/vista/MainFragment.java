package com.ejemplo.googlemapsv2.vista;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.googlemapsv2.R;
import com.ejemplo.googlemapsv2.json.ManageGoogleRoutes;

/**
 * Created by choqu_000 on 25/03/2016.
 */

public class MainFragment extends Fragment {
    private static String APP_TAG = "APIRutasGoogle";
    private TextView _txtPointA;
    private TextView _txtPointB;
    private Button _btnCalcRoute;
    private FormMainActivity mainAct;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainAct = (FormMainActivity) activity;
    }

    //Metodo inicial para encotrar las rutas
    public void setupUI(View rootView) {
        _txtPointA = (TextView) rootView.findViewById(R.id.textPointA);
        _txtPointA.setText("ALMACEN LA 14 DE CALIMA - Calle 70, Calima, Valle del Cauca");
        _txtPointB = (TextView) rootView.findViewById(R.id.textPointB);
        _txtPointB.setText("Universidad San Buenaventura Cali, Barrio Pance, Valle del Cauca");
        _btnCalcRoute = (Button) rootView.findViewById(R.id.btnCalcRoute);
        //Le damos click al botom
        _btnCalcRoute.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(APP_TAG, "_btnCalcRoute was clicked");
                String pointA = _txtPointA.getText().toString(); //Lado A
                String pointB = _txtPointB.getText().toString(); //Lado B
                //Comparacion
                if (pointA != null && pointA != "" && pointB != null && pointB != "") {
                    new ManageGoogleRoutes(MainFragment.this.mainAct).execute(pointA, pointB);
                } else {
                    Toast.makeText(MainFragment.this.mainAct, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
