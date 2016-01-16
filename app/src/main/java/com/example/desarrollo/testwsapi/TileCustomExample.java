package com.example.desarrollo.testwsapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by DESARROLLO on 05/01/16.
 */
public class TileCustomExample {

    private Bitmap foto;
    private String url;
    private Context contex;
    private Double x, y;


    public TileCustomExample(String url, Context contex, double x, double y) {
        this.url = url;
        this.contex = contex;
        this.x = x;
        this.y = y;

    }

    public void getImagen() {
        new DescargaFoto(this.url).execute();
    }

    private class DescargaFoto extends AsyncTask<String, Void, Bitmap> {

        String url;


        public DescargaFoto(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {

            String urlDisplay = url;
            Bitmap bm = null;
            try {
                InputStream iS = new java.net.URL(urlDisplay).openStream();
                bm = BitmapFactory.decodeStream(iS);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            foto = bitmap;

            int nx = x.intValue();
            int ny = y.intValue();

            File root[] = contex.getExternalFilesDirs("/map/18/" + nx);
            File cachePath = new File(root[1].getAbsolutePath() + "/" + ny + ".jpeg");
            try {

                cachePath.createNewFile();
                FileOutputStream os = new FileOutputStream(cachePath);
                foto.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Log.wtf("imagen", foto.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
