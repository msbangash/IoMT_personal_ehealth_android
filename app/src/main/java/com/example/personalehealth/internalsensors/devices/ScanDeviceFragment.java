package com.example.personalehealth.internalsensors.devices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalehealth.R;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;


public class ScanDeviceFragment extends Fragment {

    //Initialize global variables
    Button  btSensors;
    public static TextView  textView6, textView7, textView8, textView9;
    public static SensorManager sensorManager;
    public static List<Sensor> deviceSensors;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_device, container, false);

        initializedViews(view);


        //Initialize SensorManager & deviceSensors
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        btSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceInfo.printSensors();
                DeviceInfo.specificSensors();
            }
        });


        return view;
    }

    private void initializedViews(View view) {

        //Assign variables for FindSensors
        btSensors = view.findViewById(R.id.bt_sensors);
        textView6 = view.findViewById(R.id.text_view6);
        textView7 = view.findViewById(R.id.text_view7);
        textView8 = view.findViewById(R.id.text_view8);
        textView9 = view.findViewById(R.id.text_view9);

    }



}