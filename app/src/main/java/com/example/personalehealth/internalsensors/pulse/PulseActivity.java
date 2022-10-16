package com.example.personalehealth.internalsensors.pulse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.personalehealth.MainActivity;
import com.example.personalehealth.R;
import com.example.personalehealth.utils.Utilities;

public class PulseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        String number = Utilities.getString(getApplicationContext(), "pulse");
        if (!number.isEmpty()) {
            if (number != "0") {

                TextView tv = (TextView) findViewById(R.id.number);
                tv.setText(number);

                RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
                tv = (TextView) findViewById(R.id.text);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        final Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Measure.class);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}