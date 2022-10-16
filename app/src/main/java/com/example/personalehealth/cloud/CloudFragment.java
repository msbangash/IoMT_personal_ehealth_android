package com.example.personalehealth.cloud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.personalehealth.R;
import com.example.personalehealth.database.History;
import com.example.personalehealth.database.MyDatabase;
import com.example.personalehealth.utils.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CloudFragment extends Fragment {


    MyDatabase myDatabase;
    Button btn_save;
    String fname,lname,lat,lon, country,locality,address,ecg,ppg,pulse,currentDate,currentTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_cloud, container, false);
        myDatabase = Room.databaseBuilder(getContext(), MyDatabase.class, "MY_History").
                allowMainThreadQueries().build();

       initializedViews(view);
       getStoredValues();

       btn_save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
               currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

               myDatabase.historyDao().addToHistory(new History(
                       fname,
                       lname,
                       lat,
                       lon,
                       country,
                       locality,
                       address,
                       pulse,
                       "1.2",
                       "40",
                       currentDate+" "+currentTime

               ));

           }
       });
       return view;
    }

    private void getStoredValues() {
        fname = Utilities.getString(getContext(),"firstName");
        lname = Utilities.getString(getContext(),"lastName");
        country = Utilities.getString(getContext(),"country");
        address = Utilities.getString(getContext(),"locality");
        locality = Utilities.getString(getContext(),"address");
        lat = Utilities.getString(getContext(),"latitude");
        lon = Utilities.getString(getContext(),"longitude");
        pulse = Utilities.getString(getContext(),"pulse");
        ecg = Utilities.getString(getContext(),"ecg");
        ppg = Utilities.getString(getContext(),"ppg");

    }

    private void initializedViews(View view) {
        btn_save = view.findViewById(R.id.saveToLocal);
    }
}