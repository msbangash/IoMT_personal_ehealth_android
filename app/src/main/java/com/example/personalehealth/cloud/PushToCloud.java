package com.example.personalehealth.cloud;

import android.content.Context;
import android.widget.Toast;
//
//import org.eclipse.paho.android.service.MqttAndroidClient;
//import org.eclipse.paho.client.mqttv3.IMqttActionListener;
//import org.eclipse.paho.client.mqttv3.IMqttToken;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class PushToCloud {


    private String id,fname,lname,dob,age,phone,gender,lat,lon, country,locality,address,ecg,ppg,pulse,currentDate,currentTime, weatherTitle, description, temperature, pressure, humidity;

    public PushToCloud() {


    }

    public PushToCloud(String id, String fname, String lname, String dob, String age, String phone, String gender, String lat, String lon, String country, String locality, String address, String ecg, String ppg, String pulse, String currentDate, String currentTime, String weatherTitle, String description, String temperature, String pressure, String humidity) {

        this.fname = fname;
        this.id = id;
        this.lname = lname;
        this.dob = dob;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.locality = locality;
        this.address = address;
        this.ecg = ecg;
        this.ppg = ppg;
        this.pulse = pulse;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.weatherTitle = weatherTitle;
        this.description = description;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;

    }


    public String PushNow(){

        String userDataInJson="{"+
                "User: [" +
                "{"+
                "Identification_Information:[" +
                "id:\""+this.id+"\","+
                "first_name:\""+this.fname+"," +
                "last_name:\""+this.lname+"," +
                "age:\""+this.age+"," +
                "DOB:\""+this.dob+"," +
                "phone_no:\""+this.phone+"," +
                "email:\"\"," +
                "gender:\""+this.gender+"\"," +
                "address:\""+this.address+"\"," +
                "counrty:\""+this.country+"\"," +
                "city:\"\"," +
                "longitude:\""+this.lon+"\"," +
                "latitude:\""+this.lat+"\"" +
                "]," +
                "VitalSigns:[" +
                "ECG:[" +
                "sensor_name:\"BITLANO ECG\"," +
                "value:\""+this.ecg+"\"," +
                "date:\""+this.currentDate+"\"," +
                "location:[" +
                "longitude:\""+this.lon+"\"," +
                "latitude:\""+this.lat+"\"" +
                "]," +
                "" +
                "]," +
                "PPG:[" +
                "sensor_name:\"BITLANO PPG\"," +
                "value:\""+this.ppg+"\"," +
                "date:\""+this.currentDate+"\"," +
                "location:[" +
                "longitude:\""+this.lon+"\"," +
                "latitude:\""+this.lat+"\"" +
                "]" +
                "]," +
                "pluse:[" +
                "sensor_name:\"Mobile Camera Sensor\"," +
                "value:\""+this.pulse+"\"," +
                "date:\""+this.currentDate+"\"," +
                "location:[" +
                "longitude:\""+this.lon+"\"," +
                "latitude:\""+this.lat+"\"" +
                "]," +
                "]," +
                "]," +
                "external_services:[" +
                "temperature:\""+this.temperature+"\"," +
                "humidity:\""+this.humidity+"\"," +
                "pressure:\""+this.pressure+"\"," +
                "weather:\""+this.weatherTitle+"\"," +
                "]" +
                "" +
                "]" +
                "}" +
                "";





        return userDataInJson;

    }
}
