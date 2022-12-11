package com.example.hdp4proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Gravedad extends AppCompatActivity implements SensorEventListener {
    private TextView valor_x, valor_y, valor_z;
    private SensorManager sensorManager;
    private Sensor gravity;
    private AudioManager amanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravedad);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //opcional, es para testear el proyecto

        amanager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        valor_x=findViewById(R.id.textView_x);
        valor_y=findViewById(R.id.textView_y);
        valor_z=findViewById(R.id.textView_z);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);



        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!=null)
        {
            gravity=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }else{
            String mensaje="El sensor no esta presente";
            valor_x.setText(mensaje);
        }

        btnRegresar.setOnClickListener(v -> {
            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
        });
    }

    @Override //este es el lmetodo que dira el valor de cada uno
    public void onSensorChanged(SensorEvent sensorEvent) {
        String ValorX = sensorEvent.values[0]+" m/s2";
        String ValorY = sensorEvent.values[1]+" m/s2";
        String ValorZ = sensorEvent.values[2]+" m/s2";
        valor_x.setText(ValorX);
        valor_y.setText(ValorY);
        valor_z.setText(ValorZ);

        if(sensorEvent.values[2]<9.7) //significa que esta boca abajo
        {
            //se cambiara el color a verde y se pondra en modo vibracion
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            amanager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else{
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            amanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!=null)
            sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!=null)
            sensorManager.unregisterListener(this, gravity);
    }
}