package com.example.ik_2dm3.proyectoupv;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class SacarFotos extends Activity {
    public Button Sacar;
    public ImageView Imagenes;
    public Uri file;
    Uri image_uri;
    private ContentValues ContentValues;
    public static final int PERMISSION_CODE=1000;
    public int IMAGE_CAPTURE_CODE;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacar_fotos);

        Sacar = findViewById(R.id.BotonSacarFoto);
        Imagenes = findViewById(R.id.Imajenes);
        Sacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                   if (checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED||
                           checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                   PackageManager.PERMISSION_DENIED){
                        String[]permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                       //Permiso deducido
                       takePicture();
                   }
                }
                else{
                    takePicture();

                }
            }
        });

    }

    public void takePicture() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NuevaImagen");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion de la imajen");
        image_uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);

        startActivityForResult(cameraIntent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Imagenes.setImageURI(image_uri);
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0]==
                    PackageManager.PERMISSION_GRANTED){
                takePicture();
            }
                else {
                    Toast.makeText(this, "Sin Permisso", Toast.LENGTH_LONG);
                }
            }
        }

}
}
