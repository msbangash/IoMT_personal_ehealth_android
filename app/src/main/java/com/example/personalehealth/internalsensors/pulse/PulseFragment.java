package com.example.personalehealth.internalsensors.pulse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.personalehealth.R;
import com.example.personalehealth.utils.Utilities;

public class PulseFragment extends Fragment {

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pulse, container, false);


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
        }

        final Button button = (Button) view.findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), Measure.class);
                startActivity(intent);
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        String number = Utilities.getString(getActivity(),"LAST_MEASURE");
        if (!number.isEmpty()) {
            if (number != "0") {

                TextView tv = (TextView) view.findViewById(R.id.number);
                tv.setText(number);

                RatingBar rb = (RatingBar) view.findViewById(R.id.ratingBar);
                tv = (TextView) view.findViewById(R.id.text);
                if (Double.parseDouble(number) > 90) {
                    //Cosa mala
                    rb.setRating(0);
                    tv.setText("Your heart rate is to high");
                } else {
                    //Cosa buena
                    rb.setRating(1);
                    tv.setText("Your heart rate is correct");
                }
            }
        }
    }
}