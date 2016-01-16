package com.example.desarrollo.testwsapi;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geometry.Point;

/**
 * Created by DESARROLLO on 05/01/16.
 */
public class ConvertTileCoordinate {

    private LatLng coordenada;
    private int zoom;
    private static int TITLE_SIZE;
    private LatLng latLng;

    public ConvertTileCoordinate(LatLng coordenada,int zoom,int TITLE_SIZE){
        this.coordenada = coordenada;
        this.zoom = zoom;
        this.TITLE_SIZE = TITLE_SIZE;
    }
    private android.graphics.Point project(LatLng coordenada){

        Double siny = Math.sin(coordenada.latitude * Math.PI / 180);

        siny = Math.min(Math.max(siny,-0.9999),0.9999);

        int x = (int) ( TITLE_SIZE * (0.5 + coordenada.longitude/360));
        int y = (int) ( TITLE_SIZE * (0.5 - Math.log((1 + siny)/(1-siny)) / (4*Math.PI)));

        android.graphics.Point punto = new android.graphics.Point(x,y);

        return  punto;
    }

    public android.graphics.Point getTileCoordinate(){

        int scale = 1 << zoom;
        android.graphics.Point worldCoordinate = project(coordenada);

        int x = (int) Math.floor(worldCoordinate.x * scale / TITLE_SIZE);
        int y = (int) Math.floor(worldCoordinate.y * scale / TITLE_SIZE);

        Double lng = tileLng(x, zoom);
        Double lat = tileLat(y,zoom);

        this.latLng = new LatLng(lat,lng);

        android.graphics.Point punto = new android.graphics.Point(x,y);
        return  punto;
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

    public LatLng getLatLng(){
        getTileCoordinate();
        return this.latLng;
    }


}
