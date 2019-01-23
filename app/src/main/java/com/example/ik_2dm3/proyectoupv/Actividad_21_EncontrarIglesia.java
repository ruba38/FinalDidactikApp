package com.example.ik_2dm3.proyectoupv;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Actividad_21_EncontrarIglesia extends AppCompatActivity {
 public ImageView juegoMap1;
    Lienzo fondo;
    RelativeLayout layout1;
    boolean encontrado = false;
    Button btnComprobar,btnCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_21__encontrar_iglesia);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    layout1     = (RelativeLayout) findViewById(R.id.layout1);
        fondo = new Lienzo(this,null);
        layout1.addView(fondo);
        juegoMap1 = findViewById(R.id.juegoMap1);

        //COMPROBAR
        btnCamara = (Button) findViewById(R.id.btnCamara);
        btnCamara.setVisibility(View.INVISIBLE);
        btnComprobar = (Button) findViewById(R.id.btnComprobar2);
        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wi>=220 && wi<=500 && he>=210 && he<=400){
                    encontrado=true;
                }else{encontrado=false;}
                // pr.setText(String.valueOf(event.getX()) + "x" + String.valueOf(event.getY())+"tamaño:"+wi+"/"+he+"cord:"+wi*(c1x1/100)+"-"+wi*(c1x2/100)+"/"+he*(c1y1/100)+"-"+he*(c1y2/100));
                /*Context context = getApplicationContext();
                CharSequence text = "w" + wi + " / h" + he +"----"+encontrado;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/

               if (encontrado){
                   btnCamara.setVisibility(View.VISIBLE);
                   repintar();
                   Context context = getApplicationContext();
                   CharSequence text = "BAIIIII!!!";
                   int duration = Toast.LENGTH_SHORT;

                   Toast toast = Toast.makeText(context, text, duration);
                   toast.show();
                repintar();
               }else{
                Context context = getApplicationContext();
                CharSequence text = "EZ EZ EZZZZZ!!!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();}
                repintar();
            }
        });
        //CAMARA

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent SacarFonto= new Intent(getBaseContext(), SacarFotos.class);
                    //SacarFonto.putExtra("idPuntoJuego",idPuntoJuego);
                    //Camara
                    startActivityForResult(SacarFonto, 31);
                    finish();

            }
        });
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    float he;
    float wi;
    @SuppressLint("ClickableViewAccessibility")
    protected void onStart() {
        super.onStart();
        juegoMap1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wi= event.getX();
                he= event.getY();

                invalidateOptionsMenu();

                repintar();

                return true;
            }
        });
    }
    public void repintar(){
        layout1.removeView(fondo);
        layout1.addView(fondo);
    }
class Lienzo extends View {

    Boolean isRunning=false,isLockMode=false;
    Context gameContext;
    Thread myThread=null;

    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        gameContext=context;
    }
boolean primeravez=false;
    protected void onDraw(Canvas canvas) {
        if (primeravez) {
            float ancho = wi;
            float alto = he;
            Paint pincel1 = new Paint();

            if (encontrado) {
                pincel1.setARGB(255, 0, 255, 0);
            } else {
                pincel1.setARGB(255, 255, 0, 0);
            }
            encontrado = false;
            pincel1.setStyle(Paint.Style.STROKE);
            pincel1.setStrokeWidth(20);
            canvas.drawCircle(ancho, alto, 115, pincel1);
        }else {
            primeravez = true;
        }
    }


}}

