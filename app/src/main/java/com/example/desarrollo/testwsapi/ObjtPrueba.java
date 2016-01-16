package com.example.desarrollo.testwsapi;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by DESARROLLO on 13/01/16.
 */
public class ObjtPrueba implements Serializable {

    private String nombre,apellido;
    private int id;
    private byte[] byteArray;

    public ObjtPrueba(String nombre,String apellido,int id){
        this.apellido = apellido;
        this.nombre = nombre;
        this.id = id;
    }

    public void setFoto(AssetManager assetManager){
        InputStream in = null;
        Bitmap foto;
        try{
            in = assetManager.open("map/18/58906/116630.png");
            foto = BitmapFactory.decodeStream(in);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG,100,stream);
            byteArray = stream.toByteArray();
            stream.close();
            stream = null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in != null)try{in.close();}catch(Exception e){}
            foto = null;
            in = null;
        }

    }

    public int getId() {
        return id;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public byte[] getByteArray() {
        return byteArray;
    }
}
