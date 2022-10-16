package com.example.personalehealth.internalsensors.devices;

import android.hardware.Sensor;

import androidx.appcompat.app.AppCompatActivity;

public class DeviceInfo extends AppCompatActivity {

    public static void printSensors() {
        for (Sensor sensor : ScanDeviceFragment.deviceSensors) {
            ScanDeviceFragment.textView6.setText(ScanDeviceFragment.textView6.getText() + "\n" + sensor.getName());
        }
    }

    public static void specificSensors() {
        String yes = " Available";
        String no = " Unavailable";
        if(ScanDeviceFragment.sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null) {
            ScanDeviceFragment.textView6.setText("Ambient Temperature"+yes);
        }
        else {
            ScanDeviceFragment.textView6.setText("Ambient Temperature"+no);
        }
        if(ScanDeviceFragment.sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) !=null) {
            ScanDeviceFragment.textView7.setText("Pressure"+yes);
        }
        else {
            ScanDeviceFragment.textView7.setText("Pressure"+no);
        }
        if (ScanDeviceFragment.sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) !=null){
            ScanDeviceFragment.textView8.setText("Humidity"+yes);
        }
        else {
            ScanDeviceFragment.textView8.setText("Humidity"+no);
        }
        if (ScanDeviceFragment.sensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) !=null){
            ScanDeviceFragment.textView9.setText("Heart Beat"+yes);
        }
        else {
            ScanDeviceFragment.textView9.setText("Heart Beat"+no);
        }
        if (ScanDeviceFragment.sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) !=null){
            ScanDeviceFragment.textView9.setText("Heart Rate "+yes);
        }
        else {
            ScanDeviceFragment.textView9.setText("Heart Beat"+no);
        }
    }
}
