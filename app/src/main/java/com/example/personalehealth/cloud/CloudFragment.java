package com.example.personalehealth.cloud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalehealth.MainActivity;
import com.example.personalehealth.R;
import com.example.personalehealth.database.History;
import com.example.personalehealth.database.MyDatabase;
import com.example.personalehealth.ui.activities.HomeActivity;
import com.example.personalehealth.utils.Utilities;


import info.mqtt.android.service.Ack;
import info.mqtt.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CloudFragment extends Fragment {

    MyDatabase myDatabase;
    MqttAndroidClient client;
    Button btn_save,btn_push;
    TextView mytextview;
    String email,id,fname,lname,dob,age,phone,gender,lat,lon, country,locality,address,ecg,ppg,pulse,currentDate,currentTime, weatherTitle, description, temperature, pressure, humidity;
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

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getContext(), "tcp://ec2-3-82-109-152.compute-1.amazonaws.com:1883",
                clientId, Ack.AUTO_ACK);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName("username");
        options.setPassword("password".toCharArray());




        IMqttToken token = client.connect(options);
        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                // We are connected
                Toast.makeText(getContext(), "connected to cloud", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                // Something went wrong e.g. connection timeout or firewall problems
                Toast.makeText(getContext(), "Failed to connect cloud", Toast.LENGTH_SHORT).show();

            }
        });

        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String userDataInJson="{"+
                        "\"User\": {" +

                        "\"Identification_Information\":{" +
                        "\"id\":\""+id+"\","+
                        "\"first_name\":\""+fname+"\"," +
                        "\"last_name\":\""+lname+"\"," +
                        "\"age\":\""+age+"\"," +
                        "\"DOB\":\""+dob+"\"," +
                        "\"phone_no\":\""+phone+"\"," +
                        "\"email\":\""+email+"\"," +
                        "\"gender\":\""+gender+"\"," +
                        "\"address\":\""+address+"\"," +
                        "\"country\":\""+country+"\"," +
                        "\"city\":\""+locality+"\"," +
                        "\"longitude\":\""+lon+"\"," +
                        "\"latitude\":\""+lat+"\"" +
                        "}," +

                        "\"VitalSigns\":{" +
                        "\"ECG\":{" +
                        "\"sensor_name\":\"BITLANO ECG\"," +
                        "\"value\":\""+ecg+"\"," +
                        "\"date\":\""+currentDate+"\"," +
                        "\"location\":{" +
                        "\"longitude\":\""+lon+"\"," +
                        "\"latitude\":\""+lat+"\"" +
                        "}" +
                        "}," +
                        "\"PPG\":{" +
                        "\"sensor_name\":\"BITLANO PPG\"," +
                        "\"value\":\""+ppg+"\"," +
                        "\"date\":\""+currentDate+"\"," +
                        "\"location\":{" +
                        "\"longitude\":\""+lon+"\"," +
                        "\"latitude\":\""+lat+"\"" +
                        "}" +
                        "}," +
                        "\"pluse\":{" +
                        "\"sensor_name\":\"Mobile Camera Sensor\"," +
                        "\"value\":\""+pulse+"\"," +
                        "\"date\":\""+currentDate+"\"," +
                        "\"location\":{" +
                        "\"longitude\":\""+lon+"\"," +
                        "\"latitude\":\""+lat+"\"" +
                        "}" +
                        "}" +
                        "}," +
                        "\"Weather_Information\":{" +
                        "\"temperature\":\""+temperature+"\"," +
                        "\"humidity\":\""+humidity+"\"," +
                        "\"pressure\":\""+pressure+"\"," +
                        "\"weather\":\""+weatherTitle+"\"," +
                        "\"weatherDescription\":\""+description+"\"" +
                        "}" +
                        "}" +
                        "}";




                Log.d("TAG", userDataInJson);
                String topic = "both_directions";

                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = userDataInJson.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                    Toast.makeText(getContext(), "Data pushed to cloud", Toast.LENGTH_SHORT).show();



                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                myDatabase.historyDao().addToHistory(new History(
                        id,
                        fname,
                        lname,
                        dob,
                        age,
                        phone,
                        gender,
                        lat,
                        lon,
                        country,
                        locality,
                        address,
                        pulse,
                        ecg,
                        ppg,
                        currentDate+" "+currentTime,
                        weatherTitle,
                        description,
                        temperature,
                        pressure,
                        humidity

                ));

            }
        });




       return view;
    }

    private void getStoredValues() {
        id = Utilities.getString(getContext(),"userId");
        email = Utilities.getString(getContext(),"email");
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

    }

    private void initializedViews(View view) {
//        btn_save = view.findViewById(R.id.saveToLocal);
        btn_push = view.findViewById(R.id.push_data_aws);
//        mytextview = view.findViewById(R.id.mytextview);

    }
    public void published(View v){

    }


}