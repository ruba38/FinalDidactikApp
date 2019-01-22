package com.example.ik_2dm3.proyectoupv;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Galeria extends AppCompatActivity {
    //Button btnHome;
    private ListView paradasView;
    //private MyOpenHelper db;
    private Button btnCamara;

    static final int REQ_BTN = 0;
    ImageView imgTakenPic;
    private static final int CAM_REQUEST=1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        Camera.open();
        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //btnHome = (Button) findViewById(R.id.btnHome);
        btnCamara = (Button) findViewById(R.id.Camara);
        imgTakenPic = (ImageView) findViewById(R.id.imageView);
        /*try{
           toImg(data);
        }catch (IOException e){
        }*/
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                //i.putExtra("return-data", true);
                startActivityForResult(i, CAM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == CAM_REQUEST) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTakenPic.setImageBitmap(bitmap);
                String ruta = guardarImagen(this, "prueba", bitmap);
                Log.d("mytag",ruta);
            }
        }
    }

    private String guardarImagen (Context context, String nombre, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);
        File dirImages = cw.getDir("galery", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, "prueba.png");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }


    /*public void toImg(String byteArray) throws IOException {

        byte[] decodedString = Base64.decode(byteArray, Base64.DEFAULT);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Log.d("mytag","BITMAP: "+decodedByte);
        image.setImageBitmap(decodedByte);

    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "Voy hacia atrás!!",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}

