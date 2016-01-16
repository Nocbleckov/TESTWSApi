package com.example.desarrollo.testwsapi;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by DESARROLLO on 05/01/16.
 */
public class ConvetTileToLatLng {

    private double x , y , zoom ;
    private LatLng coordenada;

    public ConvetTileToLatLng(double x, double y , double zoom){

        this.x = x;
        this.y = y;
        this.zoom = zoom;
        Double lat = tileLat(y, zoom);
        Double lng = tileLng(x,zoom);

        lat = Math.abs(lat);

        coordenada = new LatLng(lat,lng);
    }


    private Double tileLng(double x,double zoom){
        Double lng = (x/Math.pow(2,zoom)*360-180);
        return lng;
    }

    private Double tileLat(double y,double zoom){

        Double n = Math.PI-2*Math.PI*y/Math.pow(2,zoom);

        Double lat =(180/Math.PI*Math.atan(0.5*(Math.exp(n)-Math.exp(-n))));
        return lat;
    }

    public LatLng getCoordenada(){
        return this.coordenada;
    }

}
