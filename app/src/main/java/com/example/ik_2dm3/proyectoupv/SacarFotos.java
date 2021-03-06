package com.example.ik_2dm3.proyectoupv;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class SacarFotos extends AppCompatActivity {
    public ImageView bottonCamara;
    private Dialog back;
    private Button atras, salir;
    public Button bottonOk;
    public ImageView Imagenes;
    public Uri image_uri;
    private ContentValues ContentValues;
    public static final int PERMISSION_CODE=1000;
    private Dialog pistaPopup;
    private TextView idTextViewPista;
    private Button idBtnCerrarPista;
    public int IMAGE_CAPTURE_CODE;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int idPuntoJuego;
    //DATAS
    Date d = new Date();
    CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacar_fotos);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
        Imagenes = findViewById(R.id.Imajenes);
        bottonOk = findViewById(R.id.buttonOk);
        bottonOk.setVisibility(View.VISIBLE);
        back = new Dialog(this);
        back.setContentView(R.layout.atras);
        back.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        atras= (Button) back.findViewById(R.id.botreniciar);
        salir= (Button) back.findViewById(R.id.botsalir);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.dismiss();
                Intent i = new Intent(getBaseContext(), Presentaciones.class );
                i.putExtra("idPuntoJuego",idPuntoJuego);
                startActivityForResult(i, 10);
                finish();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.dismiss();

                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                //Permiso deducido
                takePicture();
            }
        } else {
            takePicture();

        }
        bottonCamara= findViewById(R.id.BottonCamara);
        bottonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        bottonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Z= new Intent(getBaseContext(),horidata.class);
                Z.putExtra("idPuntoJuego",idPuntoJuego);
                startActivityForResult(Z, 120);

            }
        });

    }

    public void takePicture() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "tiritirti");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion de la imajen");
        values.put(MediaStore.Images.Media.DATA,getExternalStorageDirectory()+"/Pictures/Dia"+Comgerfecha()+"Hora"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND)+".jpg");
        image_uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);

        startActivityForResult(cameraIntent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100){
            Imagenes.setImageURI(image_uri);
        }
        if(requestCode==120){
            if(resultCode==RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }else{
                finish();
            }
        }
    }
    

    protected void OnRequestCode() {

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
public String Comgerfecha() {
    Date c = Calendar.getInstance().getTime();
    System.out.println("Current time => " + c);

    SimpleDateFormat df = new SimpleDateFormat("dddMMMyyyy");
    String formattedDate = df.format(c);
    return formattedDate;
    }
    @Override
    public void onBackPressed() {
        //your code when back button pressed
        back.show();
    }
    }


