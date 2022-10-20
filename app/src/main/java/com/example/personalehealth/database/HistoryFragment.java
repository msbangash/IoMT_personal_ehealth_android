package com.example.personalehealth.database;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.personalehealth.R;
import com.example.personalehealth.ui.activities.LoginActivity;
import com.example.personalehealth.ui.adapter.HistoryAdapter;
import com.example.personalehealth.utils.Utilities;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<History> list;
    TextView tv_id, tv_fname, tv_lname, tv_dob, tv_age, tv_phone, tv_gender, tv_lat, tv_lon, tv_ecg, tv_ppg, tv_country, tv_locality, tv_pulse, tv_address,tv_weather,tv_pressure,tv_temperature,tv_weather_des,tv_humidity;
    String userId,fname,lname,dob,age,phone,gender,lat,lon, country,locality,address,ecg,ppg,pulse,currentDate,currentTime, weatherTitle, description, temperature, pressure, humidity;
    HistoryAdapter adapter;
    MyDatabase myDatabase;
    Button btn_visual,btn_logout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        myDatabase = Room.databaseBuilder(getContext(), MyDatabase.class, "MY_History").
                allowMainThreadQueries().build();

        initializeViews(view);
        getData();
        setListener();


        return view;
    }

    private void setListener() {
        btn_visual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistorySheet();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    private void getData() {
        userId = Utilities.getString(getContext(),"userId");
        fname = Utilities.getString(getContext(),"firstName");
        lname = Utilities.getString(getContext(),"lastName");
        dob = Utilities.getString(getContext(),"dob");
        age = Utilities.getString(getContext(),"age");
        phone = Utilities.getString(getContext(),"phone");
        gender = Utilities.getString(getContext(),"gender");
        country = Utilities.getString(getContext(),"country");
        address = Utilities.getString(getContext(),"address");
        locality = Utilities.getString(getContext(),"locality");
        lat = Utilities.getString(getContext(),"Latitude");
        lon = Utilities.getString(getContext(),"Longitude");
        pulse = Utilities.getString(getContext(),"pulse");
        ecg = Utilities.getString(getContext(),"ecg");
        ppg = Utilities.getString(getContext(),"ppg");
        weatherTitle = Utilities.getString(getContext(),"weatherTitle");
        description = Utilities.getString(getContext(),"description");
        temperature = Utilities.getString(getContext(),"temperature");
        pressure = Utilities.getString(getContext(),"pressure");
        humidity = Utilities.getString(getContext(),"humidity");

        list = myDatabase.historyDao().getData();
        setData();
    }

    private void setData(){
        tv_id.setText(Html.fromHtml(
                "<b>UUID :</b><br></font>"
                        +userId
        ));
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

        tv_weather.setText(Html.fromHtml(
                "<b>Weather :</b><br></font>"
                        +weatherTitle
        ));
                tv_pressure.setText(Html.fromHtml(
                        "<b>Pressure :</b><br></font>"
                                +pressure
                ));
                tv_temperature.setText(Html.fromHtml(
                        "<b>Temperature :</b><br></font>"
                                +temperature
                ));
                tv_weather_des.setText(Html.fromHtml(
                        "<b>Weather Description :</b><br></font>"
                                +description
                ));
                tv_humidity.setText(Html.fromHtml(
                        "<b>Humidity :</b><br></font>"
                                +humidity
                ));
    }

    private void initializeViews(View view) {
        tv_id = view.findViewById(R.id.txtId);
        tv_fname = view.findViewById(R.id.fistName);
        tv_lname = view.findViewById(R.id.lastName);
        tv_dob = view.findViewById(R.id.dob);
        tv_age = view.findViewById(R.id.age);
        tv_phone = view.findViewById(R.id.phone);
        tv_gender = view.findViewById(R.id.gender);
        btn_visual = view.findViewById(R.id.btn_visual);
        btn_logout = view.findViewById(R.id.btn_logout);
        tv_fname = view.findViewById(R.id.fistName);
        tv_lname = view.findViewById(R.id.lastName);
        tv_dob = view.findViewById(R.id.dob);
        tv_age = view.findViewById(R.id.age);
        tv_phone = view.findViewById(R.id.phone);
        tv_gender = view.findViewById(R.id.gender);
        tv_country = view.findViewById(R.id.country);
        tv_lat = view.findViewById(R.id.latitude);
        tv_lon = view.findViewById(R.id.longitude);
        tv_ppg = view.findViewById(R.id.ppg);
        tv_ecg = view.findViewById(R.id.ecg);
        tv_address = view.findViewById(R.id.address);
        tv_locality = view.findViewById(R.id.locality);
        tv_pulse = view.findViewById(R.id.pulse);
        btn_visual = view.findViewById(R.id.btn_visual);
        tv_weather=view.findViewById(R.id.weather);
        tv_weather_des=view.findViewById(R.id.weather_des);
        tv_humidity=view.findViewById(R.id.humidity);
        tv_temperature=view.findViewById(R.id.temperature);
        tv_pressure=view.findViewById(R.id.pressure);

    }

    private void showHistorySheet(){
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.sheet_history,null);
        BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity());
        sheetDialog.getBehavior().setState(STATE_EXPANDED);
        sheetDialog.setContentView(root);
        sheetDialog.show();
        recyclerView = root.findViewById(R.id.rv_history);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        if (list.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new HistoryAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
        }

    }
}