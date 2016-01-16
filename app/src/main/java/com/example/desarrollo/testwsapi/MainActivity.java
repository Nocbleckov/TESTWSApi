package com.example.desarrollo.testwsapi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.geometry.Point;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    ImageView imageView;
    TextView nombre,apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        imageView = (ImageView)findViewById(R.id.imagenRandom);
        nombre = (TextView)findViewById(R.id.nombre);
        apellido = (TextView)findViewById(R.id.apellido);
        Log.wtf("map", map.toString());

        // Here, thisActivity is the current activity

        map.getMapAsync(new OnMapReadyCallback() {
            private GoogleMap mMap;

            @Override
            public void onMapReady(GoogleMap googleMap) {

                //new PeticionBackGround().execute();

                //TileCustomExample tile = new TileCustomExample("https://maps.googleapis.com/maps/api/staticmap?maptype=satellite&center=19.457528461729723,-99.10354614257813&zoom=18&size=256x256&key=AIzaSyCN9dweEHH0yQXVVLyuCTxa_Es1Vk0gzJY",getBaseContext());
                //tile.getImagen();

                mMap = googleMap;
                mMap.setMyLocationEnabled(true);
                // Add a marker in Sydney and move the camera
                /*LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(19.44733299164789, -99.10334245708623)));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(18));

                pruevaRuntime();


                android.graphics.Point tileCoordenada = new ConvertTileCoordinate(new LatLng(19.4570, -99.1033), 18, 256).getTileCoordinate();

                int x = tileCoordenada.x;
                int y = tileCoordenada.y;

                LatLng tileLatLng = new ConvertTileCoordinate(new LatLng(19.4570, -99.1033), 18, 256).getLatLng();

                /*for(int w = 0 ; w<= 7;w++){

                }

                for(int z = 0;z<=7;z++){

                }*/

                /*for(int w = 0; w<10;w++){
                    String av = "http://mt0.google.com/vt/lyrs=y&hl=es&x=%d&y=%d&z=%d&scale=4&s=Galileo";
                    String urlx = String.format(av, 58904+w, 116627,18);
                    new TileCustomExample(urlx,getApplicationContext(),58904+w, 116627).getImagen();
                    for (int z = 0; z<10;z++){
                        String urly = String.format(av,58904+w,116627+z,18);
                        new TileCustomExample(urly,getApplicationContext(),58904+w,116627+z).getImagen();
                    }
                }*/
                //Log.wtf("URL",url);


                /*for(int i = 0;i<3;i++){
                    LatLng newTileLatLng = new ConvetTileToLatLng(x+i,y,18).getCoordenada();
                    Log.wtf("COORDX+",newTileLatLng.toString()+" "+(x+i)+","+y);
                }
                for(int i = 0;i<3;i++){
                    LatLng newTileLatLng = new ConvetTileToLatLng(x-i,y,18).getCoordenada();
                    Log.wtf("COORDX-",newTileLatLng.toString()+" "+(x-i)+","+y);
                }


                for(int i = 0;i<3;i++){
                    LatLng newTileLatLng = new ConvetTileToLatLng(x,y+i,18).getCoordenada();
                    Log.wtf("COORDY+",newTileLatLng.toString());
                }
                for(int i = 0;i<3;i++){
                    LatLng newTileLatLng = new ConvetTileToLatLng(x,y-i,18).getCoordenada();
                    Log.wtf("COORDY-",newTileLatLng.toString());
                }*/


                Log.wtf("TILE", tileCoordenada.toString());
                Log.wtf("LATLNG", tileLatLng.toString());

                Log.wtf("PROjection", "" + mMap.getProjection().getVisibleRegion());


                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                /*TileOverlayOptions a = new TileOverlayOptions();
                CustomMapTileProvider b  =  new CustomMapTileProvider(getResources().getAssets(),getBaseContext());

                a.tileProvider(b);

                mMap.addTileOverlay(a);

                /*Polyline line = mMap.addPaolyline(new PolylineOptions()
                        .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                        .width(5)
                        .color(Color.RED));*/


            }


            class PeticionBackGround extends AsyncTask<String, String, String> {

                Ruta a;

                @Override
                protected String doInBackground(String... params) {
                    LatLng origen = new LatLng(19.44733299164789, -99.10334245708623);
                    LatLng destino = new LatLng(19.443043439835886, -99.09842865017094);

                    Request peticion = new Request(origen, destino, "driving", "AIzaSyCN9dweEHH0yQXVVLyuCTxa_Es1Vk0gzJY");


                    try {
                        Conexion con = new Conexion("https://maps.googleapis.com/maps/api/directions/json?");
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
                    Polyline line = mMap.addPolyline(new PolylineOptions().addAll(a.getPuntos()).width(5).color(Color.RED));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(19.44733299164789, -99.10334245708623)));
                }
            }


        });

    }


    @TargetApi(Build.VERSION_CODES.M)
    public void pruevaRuntime() {
        int permisosEscritura = checkSelfPermission(android.Manifest.permission_group.STORAGE);
        if (permisosEscritura != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        guardarObjeto();
    }


    public void guardarObjeto() {
        try {
            ObjtPrueba obj = new ObjtPrueba("Daniel", "Lopez", 12);
            obj.setFoto(getResources().getAssets());
            File root[] = getBaseContext().getExternalFilesDirs("/objeto");
            File archivo = new File(root[0].getAbsolutePath() + "/objeto.senda");
            archivo.createNewFile();
            //FileOutputStream fOs = getBaseContext().openFileOutput(archivo.getAbsolutePath(), MODE_PRIVATE);
            FileOutputStream fOs = new FileOutputStream(archivo,true);
            ObjectOutputStream oOs = new ObjectOutputStream(fOs);

            oOs.writeObject(obj);
            oOs.close();
            fOs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarObjeto(){
        try{
            File root[] = getBaseContext().getExternalFilesDirs("/objeto");
            File archivo = new File(root[0].getAbsolutePath()+"/objeto.senda");
            //FileInputStream f_Is = getBaseContext().openFileInput(archivo.getAbsolutePath());
            FileInputStream f_Is = new FileInputStream(archivo);
            ObjectInputStream o_iS = new ObjectInputStream(f_Is);

            ObjtPrueba objt = (ObjtPrueba)o_iS.readObject();
            f_Is.close();
            o_iS.close();

            byte[] byteArray = objt.getByteArray();
            int tamaño = byteArray.length;
            Bitmap foto = BitmapFactory.decodeByteArray(byteArray,0,tamaño);
            imageView.setImageBitmap(foto);
            nombre.setText(objt.getNombre());
            apellido.setText(objt.getApellido());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    guardarObjeto();
                    cargarObjeto();
                } else {
                    Toast.makeText(MainActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
