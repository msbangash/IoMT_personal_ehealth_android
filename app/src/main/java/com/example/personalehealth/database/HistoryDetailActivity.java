package com.example.personalehealth.database;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalehealth.R;

public class HistoryDetailActivity extends AppCompatActivity {

    TextView tv_id,tv_fname, tv_lname, tv_dob, tv_age, tv_phone, tv_gender, tv_lat, tv_lon, tv_ecg, tv_ppg, tv_country, tv_locality, tv_pulse, tv_address,tv_temperature,tv_humidity,tv_weather,tv_weather_des,tv_pressure;
    String id,fname, lname, dob, age, phone, gender, lat, lon, ecg, ppg, country, locality, pulse, address,temperature,humidity,weather,weather_des,pressure;

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
        tv_dob.setText(Html.fromHtml(
                "<b>Date of Birth :</b><br></font>"
                        +dob
        ));
        tv_age.setText(Html.fromHtml(
                "<b>Age :</b><br></font>"
                        +age
        ));
        tv_phone.setText(Html.fromHtml(
                "<b>Phone :</b><br></font>"
                        +phone
        ));
        tv_gender.setText(Html.fromHtml(
                "<b>Gender :</b><br></font>"
                        +gender
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
        tv_temperature.setText(Html.fromHtml(
                "<b>Temperature :</b><br></font>"
                        +temperature
        ));
        tv_humidity.setText(Html.fromHtml(
                "<b>Humidity :</b><br></font>"
                        +humidity
        ));
        tv_weather.setText(Html.fromHtml(
                "<b>Weather :</b><br></font>"
                        +weather
        ));
        tv_weather_des.setText(Html.fromHtml(
                "<b>Description :</b><br></font>"
                        +weather_des
        ));
        tv_pressure.setText(Html.fromHtml(
                "<b>Pressure :</b><br></font>"
                        +pressure
        ));


    }

    private void getData() {

        Intent intent = getIntent();
        id = intent.getStringExtra("item_id");
        fname = intent.getStringExtra("item_fname");
        lname = intent.getStringExtra("item_lname");
        dob = intent.getStringExtra("item_dob");
        age = intent.getStringExtra("item_age");
        phone = intent.getStringExtra("item_phone");
        gender = intent.getStringExtra("item_gender");
        address = intent.getStringExtra("item_address");
        locality = intent.getStringExtra("item_locality");
        country = intent.getStringExtra("item_country");
        lat = intent.getStringExtra("item_lat");
        lon = intent.getStringExtra("item_lon");
        pulse = intent.getStringExtra("item_pulse");
        ecg = intent.getStringExtra("item_ecg");
        ppg = intent.getStringExtra("item_ppg");
        temperature = intent.getStringExtra("item_temperature");
        weather = intent.getStringExtra("item_weather");
        pressure = intent.getStringExtra("item_pressure");
        humidity= intent.getStringExtra("item_humidity");
        weather_des = intent.getStringExtra("item_des");
        setData();
    }

    private void initViews() {


        tv_id=findViewById(R.id.txtId);
        tv_fname = findViewById(R.id.fistName);
        tv_lname = findViewById(R.id.lastName);
        tv_dob = findViewById(R.id.dob);
        tv_age = findViewById(R.id.age);
        tv_phone = findViewById(R.id.phone);
        tv_gender = findViewById(R.id.gender);
        tv_country = findViewById(R.id.country);
        tv_lat = findViewById(R.id.latitude);
        tv_lon = findViewById(R.id.longitude);
        tv_ppg = findViewById(R.id.ppg);
        tv_ecg = findViewById(R.id.ecg);
        tv_address = findViewById(R.id.address);
        tv_locality = findViewById(R.id.locality);
        tv_pulse = findViewById(R.id.pulse);
        tv_temperature = findViewById(R.id.temperature);
        tv_pressure = findViewById(R.id.pressure);
        tv_humidity = findViewById(R.id.humidity);
        tv_weather = findViewById(R.id.weather);
        tv_weather_des = findViewById(R.id.weather_des);

    }
}