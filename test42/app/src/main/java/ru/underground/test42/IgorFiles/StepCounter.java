package ru.underground.test42.IgorFiles;

import android.content.Intent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.underground.test42.InnerThings.History;
import ru.underground.test42.InnerThings.MainSystem;
import ru.underground.test42.R;

public class StepCounter extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView count;
    private TextView kalor;
    boolean activityRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_dislike);
        count = (TextView) findViewById(R.id.count);
        kalor = (TextView) findViewById(R.id.eater);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting
        // step events
        // sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            count.setText(String.valueOf(event.values[0]));
            kalor.setText((int) MainSystem.getDayKal());
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
