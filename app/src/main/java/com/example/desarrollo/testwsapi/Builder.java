package com.example.desarrollo.testwsapi;

import android.text.StaticLayout;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DESARROLLO on 24/11/15.
 */
public class Builder {

    public static Ruta obtenerRuta(String data){

        Ruta rtTemp = new Ruta();
        ArrayList<String> pasosTemp = new ArrayList<String>();

        if(!data.equalsIgnoreCase("")){
            JSONObject JSON;
            try{

                JSON = new JSONObject(data);
                JSONArray rutas = JSON.optJSONArray("routes");
                JSONObject objeto = rutas.getJSONObject(0);
                JSONArray legs = objeto.getJSONArray("legs");
                JSONObject objt = legs.getJSONObject(0);

                rtTemp.setDistancia(objt.optJSONObject("distance").optString("value"));
                rtTemp.setDuracion(objt.optJSONObject("duration").optString("value"));
                rtTemp.setDireccionFin(objt.optString("end_address"));
                rtTemp.setDireccionIni(objt.optString("start_address"));
                rtTemp.setPolyLine(objeto.optJSONObject("overview_polyline").optString("points"));

                JSONArray pasos = objt.getJSONArray("steps");

                for (int i = 0;i<pasos.length();i++ ){
                    JSONObject ps = pasos.getJSONObject(i);
                    pasosTemp.add(ps.optString("html_instructions").replaceAll("\\<.*?>"," "));
                }

                rtTemp.setIntrucciones(pasosTemp);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return  rtTemp;
    }

}
