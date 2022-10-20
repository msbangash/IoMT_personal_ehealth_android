package com.example.personalehealth.internalsensors.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalehealth.R;
import com.example.personalehealth.utils.Utilities;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;
import java.util.Objects;

public class LocationFragment extends Fragment {

    //Initialize global variables
    Button btLocation;
    public static TextView textView1, textView2, textView3, textView4, textView5;
    public static Geocoder geocoder;
    String latitude,longitude,country,locality,address;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        initializeViews(v);


        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(getContext());



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gpsTracker.getIsGPSTrackingEnabled())
                {
                    latitude = String.valueOf(gpsTracker.latitude);
                    textView1.setText(Html.fromHtml(
                            "<font color='#ff9052'><b>Latitude :</b><br></font>"
                                    +latitude
                    ));
                    Utilities.saveString(getContext(),"Latitude",latitude);


                    longitude = String.valueOf(gpsTracker.longitude);
                    textView2.setText(Html.fromHtml(
                            "<font color='#ff9052'><b>Longitude :</b><br></font>"
                                    +longitude
                    ));
                    Utilities.saveString(getContext(),"Longitude",longitude);


                    address = gpsTracker.getAddressLine(getContext());
                    textView5.setText(Html.fromHtml(
                            "<font color='#ff9052'><b>Address:</b><br></font>"
                                    +address
                    ));

                    Utilities.saveString(getContext(),"address",address);


                    locality = gpsTracker.getLocality(getContext());
                    textView4.setText(Html.fromHtml(
                            "<font color='#ff9052'><b>Locality :</b><br></font>"
                                    +locality
                    ));

                    Utilities.saveString(getContext(),"locality",locality);


                    country = gpsTracker.getCountryName(getContext());
                    textView3.setText(Html.fromHtml(
                            "<font color='#ff9052'><b>Country :</b><br></font>"
                                    +country
                    ));

                    Utilities.saveString(getContext(),"country",country);


                }
                else
                {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gpsTracker.showSettingsAlert();
                }

            }
        });

        return v;
    }


    private void initializeViews(View v) {
        //Assign variables for GPS
        btLocation = v.findViewById(R.id.bt_location);
        textView1 = v.findViewById(R.id.text_view1);
        textView2 = v.findViewById(R.id.text_view2);
        textView3 = v.findViewById(R.id.text_view3);
        textView4 = v.findViewById(R.id.text_view4);
        textView5 = v.findViewById(R.id.text_view5);
        
    }
}