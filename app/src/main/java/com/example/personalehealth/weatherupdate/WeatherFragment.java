package com.example.personalehealth.weatherupdate;

import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.personalehealth.R;
import com.example.personalehealth.internalsensors.location.GPSTracker;
import com.example.personalehealth.utils.Utilities;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherFragment extends Fragment {



    Button weather_update_btn;
    public static TextView temperature_text, humidity_text, pressure_text, weather_text, weather_des_text;
    public static Geocoder geocoder;
    String latitude,longitude,temperature,humidity,pressure,weather;

     private static final String appid="077dc565d12c79240d61263753011bc7";
      private static final String tem_unit="metric";





    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.openweathermap.org/data/2.5/weather?";
           // "lat=31.45&lon=73.13&appid=077dc565d12c79240d61263753011bc7";


    public WeatherFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // check if GPS enabled


        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        initializeViews(v);
        GPSTracker gpsTracker = new GPSTracker(getContext());
        weather_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    latitude = String.valueOf(gpsTracker.latitude);
                    longitude = String.valueOf(gpsTracker.longitude);

                    sendAndRequestResponse(latitude,longitude,appid,tem_unit);







                }

            }
        });


        return v;
    }

    private void sendAndRequestResponse(String latitude,String longitude,String appid,String unit) {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url+"lat="+latitude+"&lon="+longitude+"&units="+unit+"&appid="+appid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject= new JSONObject(response);

                    JSONObject weatherObj= jsonObject.getJSONArray("weather").getJSONObject(0);

                    String weatherTitle=weatherObj.getString("main");
                    String description=weatherObj.getString("description");

                    JSONObject mainObj = jsonObject.getJSONObject("main");
                    String temp = mainObj.get("temp").toString();
                    String pressure = mainObj.get("pressure").toString();
                    String humidity = mainObj.get("humidity").toString();

                    Utilities.saveString(getContext(),"weatherTitle",weatherTitle);
                    Utilities.saveString(getContext(),"description",description);
                    Utilities.saveString(getContext(),"temperature",temp);
                    Utilities.saveString(getContext(),"pressure",pressure);
                    Utilities.saveString(getContext(),"humidity",humidity);


                    temperature_text.setText(Html.fromHtml(
                            "<font ><b>Temperature : </b>"+temp+" Celsius<br></font>"

                    ));
                    humidity_text.setText(Html.fromHtml(
                            "<font ><b>Humidity : </b>"+humidity+" %<br></font>"

                    ));
                    pressure_text.setText(Html.fromHtml(
                            "<font ><b>Pressure : </b>"+pressure+" hPa<br></font>"

                    ));
                    weather_text.setText(Html.fromHtml(
                            "<font ><b>Weather : </b>"+weatherTitle+"<br></font>"

                    ));
                    weather_des_text.setText(Html.fromHtml(
                            "<font ><b>Weather Description : </b>"+description+"<br></font>"

                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                Toast.makeText(getContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("TAG","Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    private void initializeViews(View v) {
        //Assign variables for GPS
        weather_update_btn = v.findViewById(R.id.weather_update_btn);
        temperature_text = v.findViewById(R.id.temperature_text);
        humidity_text = v.findViewById(R.id.humidity_text);
        pressure_text = v.findViewById(R.id.pressure_text);
        weather_text = v.findViewById(R.id.weather_text);
        weather_des_text = v.findViewById(R.id.weather_des_text);


    }
}