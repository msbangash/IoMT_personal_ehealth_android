package com.example.personalehealth.database;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalehealth.R;

public class HistoryDetailActivity extends AppCompatActivity {

    TextView tv_fname, tv_lname, tv_lat, tv_lon, tv_ecg, tv_ppg, tv_country, tv_locality, tv_pulse, tv_address;
    String fname, lname, lat, lon, ecg, ppg, country, locality, pulse, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        initViews();
        getData();


    }

    private void setData() {
        tv_fname.setText(Html.fromHtml(
                "<b>First Name :</b><br></font>"
                        +fname
        ));
         tv_lname.setText(Html.fromHtml(
                "<b>Last Name :</b><br></font>"
                        +lname
        ));
         tv_pulse.setText(Html.fromHtml(
                "<b>Pulse :</b><br></font>"
                        +pulse
        ));
         tv_ecg.setText(Html.fromHtml(
                "<b>ECG :</b><br></font>"
                        +ecg
        ));
         tv_ppg.setText(Html.fromHtml(
                "<b>PPG :</b><br></font>"
                        +ppg
        ));
        tv_lat.setText(Html.fromHtml(
                "<b>Latitude :</b><br></font>"
                        + lat
        ));
        tv_lon.setText(Html.fromHtml(
                "<b>Longitude :</b><br></font>"
                        + lon
        ));
        tv_country.setText(Html.fromHtml(
                "<b>Country :</b><br></font>"
                        + country
        ));
        tv_locality.setText(Html.fromHtml(
                "<b>Locality :</b><br></font>"
                        + locality
        ));
        tv_address.setText(Html.fromHtml(
                "<b>Address :</b><br></font>"
                        +address
        ));


    }

    private void getData() {

        Intent intent = getIntent();
        fname = intent.getStringExtra("item_fname");
        lname = intent.getStringExtra("item_lname");
        address = intent.getStringExtra("item_address");
        locality = intent.getStringExtra("item_locality");
        country = intent.getStringExtra("item_country");
        lat = intent.getStringExtra("item_lat");
        lon = intent.getStringExtra("item_lon");
        pulse = intent.getStringExtra("item_pulse");
        ecg = intent.getStringExtra("item_ecg");
        ppg = intent.getStringExtra("item_ppg");
        setData();
    }

    private void initViews() {

        tv_fname = findViewById(R.id.fistName);
        tv_lname = findViewById(R.id.lastName);
        tv_country = findViewById(R.id.country);
        tv_lat = findViewById(R.id.latitude);
        tv_lon = findViewById(R.id.longitude);
        tv_ppg = findViewById(R.id.ppg);
        tv_ecg = findViewById(R.id.ecg);
        tv_address = findViewById(R.id.address);
        tv_locality = findViewById(R.id.locality);
        tv_pulse = findViewById(R.id.pulse);

    }
}