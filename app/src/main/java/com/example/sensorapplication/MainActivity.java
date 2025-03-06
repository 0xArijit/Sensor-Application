package com.example.sensorapplication;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SensorManager sensorManager;
    SensorEventListener sensorEventListener;
    Sensor light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView=this.findViewById(R.id.text1);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

        light=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                textView.setText(String.valueOf(event.values[0]));
                int greyShades=(int)event.values[0];
                if (greyShades>255)
                    greyShades=255;
                textView.setTextColor(Color.rgb(255-greyShades,255-greyShades,255-greyShades));
                textView.setBackgroundColor(Color.rgb(greyShades,greyShades,greyShades));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Toast.makeText(MainActivity.this,"accuracy changed", Toast.LENGTH_SHORT).show();

            }
        };
        sensorManager.registerListener(sensorEventListener,light,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}