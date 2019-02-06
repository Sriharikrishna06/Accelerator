package com.developer.srihari.accelerator;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    Sensor acceleration;
    TextView net,acclr,name;
    int [] imgs={R.drawable.nanbenda,R.drawable.download};

    float net_acclrn=0.00f;
    int index=0;
    ImageView imgview;

    float current_acceleration=sensorManager.GRAVITY_EARTH;
    float last_acceleration=sensorManager.GRAVITY_EARTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgview=findViewById(R.id.image);
        name=findViewById(R.id.name);
        net=findViewById(R.id.net_acceleration);
        acclr=findViewById(R.id.axis_values);
        imgview.setImageDrawable(getResources().getDrawable(imgs[index]));
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        acceleration=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,acceleration,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values=sensorEvent.values;
        acclr.setText("X: "+values[0]+" Y: "+values[1]+" Z: "+values[2]);
        float magnitude= compurNetacceration(values);
        net.setText("Net: "+magnitude);
        if(magnitude>1000){
            index=(index+1)%(imgs.length);
            imgview.setImageDrawable(getResources().getDrawable(imgs[index]));
            if (index!=0) {
                name.setText("Korangu Payalyee");
            } else {
                name.setText("Nanbenda..");
            }
        }
    }

    private float compurNetacceration(float[] values) {
        float magnitude=(values[0]*values[0])*(values[1]*values[1])*(values[2]*values[2]);
        last_acceleration=current_acceleration;
        current_acceleration=(float)Math.sqrt(magnitude);
        float delta= current_acceleration - last_acceleration;
        net_acclrn=net_acclrn *0.9f + delta;
        return net_acclrn;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
