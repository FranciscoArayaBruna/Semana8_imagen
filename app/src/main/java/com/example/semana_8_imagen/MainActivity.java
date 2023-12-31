package com.example.semana_8_imagen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;  // Importación para Matrix
import android.graphics.drawable.BitmapDrawable;  // Importación para BitmapDrawable
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private ImageView imagen;
    private Button descargarImagen;
    private Button rotarImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = findViewById(R.id.imagen);
        descargarImagen = findViewById(R.id.descargarImagen);
        rotarImagen = findViewById(R.id.rotarImagen);

        descargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        final Bitmap bitmap = loadImageFromNetwork("https://th.bing.com/th/id/R.e57fc3f7e366f7d99fb8a530d3df4a64?rik=7HIE1eHlgPVZvQ&riu=http%3a%2f%2ftechcyton.com%2fwp-content%2fuploads%2f2016%2f06%2fGTA-6-Grand-Theft-Auto.jpg&ehk=IOqQu0UjjqPtqx5jeBwRrKgecx4%2fL7ErfDjMH1XuLzs%3d&risl=&pid=ImgRaw&r=0.jpg");
                        imagen.post(new Runnable() {
                            @Override
                            public void run() {
                                imagen.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });

        rotarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage();
            }
        });
    }

    private Bitmap loadImageFromNetwork(String url) {
        Bitmap bitmap = null;

        try {
            URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void rotateImage() {
        if (imagen.getDrawable() != null) {
            final Bitmap originalBitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            final Matrix matrix = new Matrix();
            matrix.postRotate(90);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
                    imagen.post(new Runnable() {
                        @Override
                        public void run() {
                            imagen.setImageBitmap(rotatedBitmap);
                        }
                    });
                }
            }).start();
        }
    }
}