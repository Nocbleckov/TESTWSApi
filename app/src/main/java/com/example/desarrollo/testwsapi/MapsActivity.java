package com.example.desarrollo.testwsapi;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

/**
 * Created by DESARROLLO on 25/11/15.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5)
                .color(Color.RED));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51.5,-0.1)));

    }

    public List<LatLng> json(){

        //new PeticionBackGround().execute();

        List<LatLng> puntos = PolyUtil.decode("o~sdC`lmoR^jA`Ae@J?F?bBaBbEoDmBmK}@{Eu@wDw@_FoAwGsDqSeA}F_BgIASk@cD@KAM@QWoAs@iDc@yBFSTWRYR?lBEvBBxBD`DDnCDPSzAa@nGwAjD{@vCq@`Ba@fB_@}@uE}BoL_AsEhEaA|@tEKBsGxAoBb@cFnAkDv@iE|@k@qCUaAjBa@~A_@~@vEiE|@qKbCiEdAqDx@y@qEjD}@lEcAdEaAdEaAm@aDs@qDk@wCWsApAK~BYnE]DAp@jD`E{@DApKcChEcA_@iB^hBZdBQDkEfAwInBiFjAsBj@mBd@qDv@Ou@i@mCcE`Av@bEuKdCiDt@{A^e@N_@sBzA[BNBHJJTzAtCw@t@Qz@vE~@tEqDx@y@qE_@qB[eBtBk@t@QRfAfBdJqDx@y@qE_@qB_@{BYsA@E?C?G?GhBe@x@QRlAg                                                                                                                              ");

        return puntos;
    }

    public class PeticionBackGround extends AsyncTask<String,String,String> {

        Ruta a;

        @Override
        protected String doInBackground(String... params) {
            LatLng origen = new LatLng(19.44733299164789,-99.10334245708623);
            LatLng destino = new LatLng(19.443043439835886,-99.09842865017094);

            Request peticion = new Request(origen,destino,"driving","AIzaSyCN9dweEHH0yQXVVLyuCTxa_Es1Vk0gzJY");


            try {
                Conexion con = new Conexion("c");
                con.setParametros(peticion.getParametros());
                con.executar(Conexion.metodoPeticion.GET);
                int codigo = con.getCodigoRespuesta();
                String respuesta = con.getRespuesta();
                String mensaje = con.getMensaje();

                a = Builder.obtenerRuta(respuesta);
                Log.d("Puntos", a.getPuntos().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
