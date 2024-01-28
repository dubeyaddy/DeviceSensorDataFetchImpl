package com.practiceapp.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager msensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorAmbientTemp;
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextAmbientTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = msensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList ) {
            sensorText.append(currentSensor.getName()).append(
                    System.getProperty("line.separator"));
        }



        mTextSensorLight=(TextView)findViewById(R.id.label_light);
        mTextSensorProximity=(TextView)findViewById(R.id.label_proximity);
        mTextAmbientTemp=(TextView)findViewById(R.id.label_ambienttemp);

        mSensorProximity=msensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight= msensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorAmbientTemp=msensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        String sensor_error= getResources().getString(R.string.error_no_sensor);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }

        if (mSensorAmbientTemp==null){
            mTextAmbientTemp.setText(sensor_error);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType= event.sensor.getType();
        float currentValue= event.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(R.string.label_light,currentValue));
                break;

            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity,currentValue));
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextAmbientTemp.setText(getResources().getString(R.string.label_ambienttemp,currentValue));

            default:
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity!=null){
            msensorManager.registerListener(this,mSensorProximity,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight!=null){
            msensorManager.registerListener(this,mSensorLight,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorAmbientTemp!=null){
            msensorManager.registerListener(this,mSensorAmbientTemp,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        msensorManager.unregisterListener(this);
    }
}