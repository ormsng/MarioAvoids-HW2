package com.example.marioavoidsp2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Detector {
    private StepCallBack stepCallback;
    private SensorManager sensorManager;
    private Sensor sensor;


    private int stepCountX = 0;
    private int stepCountY = 0;
    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public Detector(Context context, StepCallBack _stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = _stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateStep(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void calculateStep(float x) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 2.0) {
                stepCountX++;
                if (stepCallback != null)
                    stepCallback.sensorMove(true);
            }
            if (x < 2.0) {
                stepCountX++;
                if (stepCallback != null)
                    stepCallback.sensorMove(false);
            }
        }
    }

    public int getStepCountX() {
        return stepCountX;
    }

    public int getStepCountY() {
        return stepCountY;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}