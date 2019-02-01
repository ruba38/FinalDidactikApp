package com.example.ik_2dm3.proyectoupv;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad_21_EncontrarIglesia extends AppCompatActivity {
 public ImageView juegoMap1;
    Lienzo fondo;
    RelativeLayout layout1;
    boolean encontrado = false;
    Button btnComprobar,btnCamara;
    private Dialog back;
    private Button atras, salir;
    private int idPuntoJuego;
    private TextView textoPopupGeneral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_21__encontrar_iglesia);
        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);

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

        Dialog popupgeneral= new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(popupgeneral.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        popupgeneral.getWindow().setAttributes(lp);
        popupgeneral.setContentView(R.layout.popup_general);
        textoPopupGeneral= popupgeneral.findViewById(R.id.textoPopupGeneral);

        popupgeneral.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textoPopupGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupgeneral.dismiss();
                if (encontrado){
                    Intent i = new Intent(getBaseContext(), SacarFotos.class );
                    i.putExtra("idPuntoJuego",idPuntoJuego);
                    startActivityForResult(i, 11);
                    popupgeneral.dismiss();


                }else{
                    popupgeneral.dismiss();
                }
            }
        });

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
        he=fondo.getHeight();
        wi=fondo.getWidth();



        /*fondo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        double x1=13.7;
        double x2=18.9;
        double y1=29.2;
        double y2=42;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wi>=(width*(x1/100)) && wi<=(width*(x2/100)) && he>=(height*(y1/100)) && he<=(height*(y2/100))){
                    encontrado=true;
                    popupgeneral.show();
                    comprobar(encontrado);
                }else{
                    encontrado=false;
                    popupgeneral.show();
                    comprobar(encontrado);
                }
                // pr.setText(String.valueOf(event.getX()) + "x" + String.valueOf(event.getY())+"tamaño:"+wi+"/"+he+"cord:"+wi*(c1x1/100)+"-"+wi*(c1x2/100)+"/"+he*(c1y1/100)+"-"+he*(c1y2/100));
                /*Context context = getApplicationContext();
                CharSequence text = "w" + wi + " / h" + he +"----"+encontrado;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/

               if (encontrado){
                   btnCamara.setVisibility(View.VISIBLE);
                   repintar();
                   repintar();
               }else{
                Context context = getApplicationContext();
                }
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
                    SacarFonto.putExtra("idPuntoJuego",idPuntoJuego);
                    startActivityForResult(SacarFonto, 31);
                    finish();

            }
        });
}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Point size = new Point();
            int width =size.x;
            int height = size.y;
            int eventx=Math.round(event.getX());
            int eventy=Math.round(event.getY());

        }


        return super.onTouchEvent(event);
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


}

    @Override
    public void onBackPressed() {
        //your code when back button pressed
        back.show();
    }
    protected void comprobar(boolean encontrado){

        if (encontrado){
            textoPopupGeneral.setText("OSO ONDO");
            textoPopupGeneral.setTextColor(getColor(R.color.vc));

        }else{
            textoPopupGeneral.setText("ZAIATU BERRIRO");
            textoPopupGeneral.setTextColor(getColor(R.color.rc));
        }
    }
}

