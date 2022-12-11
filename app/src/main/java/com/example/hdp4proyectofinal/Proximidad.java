package com.example.hdp4proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Proximidad extends AppCompatActivity {

    // variable para comunicarme con el dispositivo
    SensorManager sensorManager;
    // Varible para representar el sensor
    Sensor sensor;
    // varible para poder capturar el evento de aproximida
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximidad);

        // creamos una instancia del sensormanager
        sensorManager =(SensorManager)getSystemService(SENSOR_SERVICE);

        // creamos nuestra variable sensor
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // comprobamos su nuestro dispositico cuenta con el sensor
        // finalizamos nuestra Activity
        if (sensor == null) {
            finish();
        }

        // agregamos el evento que va realizar la tarea
        sensorEventListener= new SensorEventListener() {

            // Este metodo nos ayuda a verificar cuuando lo valores
            // del sensor han cabiado
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                // se verifica que la distancia que se tiene al sensor
                // sea el rango maximo
                if(sensorEvent.values[0] < sensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }

                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            }

            // este metodo lo ccupamos cuando la presicion del sensor
            // ha cabiado
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        start();
    }

    // metodo para invocar el sensorManager
    public void start(){
        // tiempo en micro segundos
        sensorManager.registerListener(sensorEventListener,sensor, 2000*1000);
    }

    // metodo para detener al sensor
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }
}