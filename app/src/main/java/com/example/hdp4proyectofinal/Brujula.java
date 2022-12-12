package com.example.hdp4proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class Brujula extends AppCompatActivity implements SensorEventListener {

    ImageView imageView;
    float[] mGravity = new float[3];
    float[] mGeomagnetic = new float[3];
    float azimuth = 0f;
    float correctAzimuth = 0f;
    SensorManager mSensorManager;
    private Sensor magnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brujula);

        imageView = findViewById(R.id.compass);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!=null)
        {
            magnetic=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }else{
            String mensaje="El sensor no esta presente";
        }

        btnRegresar.setOnClickListener(v -> {
            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha = 0.97f;
        synchronized (this){
            if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            {
                mGravity [0] = alpha*mGravity[0] + (1-alpha)*sensorEvent.values[0];
                mGravity [1] = alpha*mGravity[1] + (1-alpha)*sensorEvent.values[1];
                mGravity [2] = alpha*mGravity[2] + (1-alpha)*sensorEvent.values[2];
            }

            if (sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            {
                mGeomagnetic [0] = alpha*mGeomagnetic[0] + (1-alpha)*sensorEvent.values[0];
                mGeomagnetic [1] = alpha*mGeomagnetic[1] + (1-alpha)*sensorEvent.values[1];
                mGeomagnetic [2] = alpha*mGeomagnetic[2] + (1-alpha)*sensorEvent.values[2];
            }

            float R[] = new float [9];
            float I[] = new float [9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if (success)
            {
                float orientation[] = new float [3];
                SensorManager.getOrientation(R,orientation);
                azimuth = (float)Math.toDegrees(orientation[0]);
                azimuth=(azimuth+360)%360;

                Animation anim = new RotateAnimation(-correctAzimuth, -azimuth, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                correctAzimuth= azimuth;

                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                imageView.startAnimation(anim);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}