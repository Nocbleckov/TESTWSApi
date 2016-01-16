package com.example.desarrollo.testwsapi;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by DESARROLLO on 23/11/15.
 */
public class Request {

    private String mode,key;
    private HashMap<String,String> parametros;
    private LatLng origin,destination;


    Request(LatLng origin, LatLng destination, String mode, String key){

        parametros = new HashMap<String,String>();
        this.origin = origin;
        this.destination = destination;
        this.mode = mode;
        this.key = key;

    }


    public LatLng getOrigin() {
        return origin;
    }

    public LatLng getDestination() {
        return destination;
    }

    public String getMode() {
        return mode;
    }

    public String getKey() {
        return key;
    }

    public HashMap<String,String> getParametros(){

        parametros.put("origin",origin.latitude+","+origin.longitude);
        parametros.put("destination",destination.latitude+","+origin.longitude);
        parametros.put("mode",mode);
        parametros.put("key",key);
        parametros.put("language","es");

        return this.parametros;
    }

}
