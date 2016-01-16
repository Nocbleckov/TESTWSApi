package com.example.desarrollo.testwsapi;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by DESARROLLO on 04/01/16.
 */
public class CustomMapTileProvider implements TileProvider {

    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;

    //private AssetManager assetManager;
    private Context context;

    public CustomMapTileProvider(AssetManager assetManager,Context context){
        //this.assetManager = assetManager;
        this.context = context;
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        //y = fixYCoordinate(y,zoom);
        coordenasTiletoLatLng(x,y,zoom);
        byte[] imagen = readTileImage(x,y,zoom);
        return imagen == null ? null : new Tile(TILE_WIDTH,TILE_HEIGHT,imagen);
    }


    public void coordenasTiletoLatLng(int x ,int y,int zoom){
        for(int i = 0;i<3;i++){
            LatLng newTileLatLng = new ConvetTileToLatLng(x+i,y,18).getCoordenada();

            Log.wtf("COORDX+", newTileLatLng.toString() + " " + (x + i) + "," + y);
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
        }
    }

    public byte[] readTileImage(int x, int y,int zoom){

        InputStream in = null;
        ByteArrayOutputStream buffer = null;

        try{

            //in = assetManager.open(getTileFilename(x,y,zoom));
            File file[] = context.getExternalFilesDirs("");
            in = new FileInputStream(file[1].getAbsolutePath()+"/"+getTileFilename(x,y,zoom));
            buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[BUFFER_SIZE];

            while ((nRead = in.read(data,0,BUFFER_SIZE))!= -1){
                buffer.write(data,0,nRead);
            }
            buffer.flush();

            return  buffer.toByteArray();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(in != null)try{in.close();}catch(Exception e){}
            if(buffer != null)try{buffer.close();}catch(Exception e){}
        }

    }

    private String getTileFilename(int x, int y, int zoom) {
        return "map/" + zoom + '/' + x + '/' + y + ".jpeg";
    }

    private int fixYCoordinate(int y, int zoom) {
        int size = 1 << zoom; // size = 2^zoom
        return size - 1 - y;
    }

}
