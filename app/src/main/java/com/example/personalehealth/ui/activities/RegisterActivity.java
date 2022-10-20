package com.example.personalehealth.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalehealth.R;
import com.example.personalehealth.ui.model.User;
import com.example.personalehealth.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText edFirstName, edLastName, edEmail, edPassword, edAge, edPhone;
    RadioButton rdbMale,rdbFemale;
    Button btnRegister;
    TextView edDob;
    String fName, lName, email, password, dob="", age, phone, gender = "male";
    FirebaseAuth mAuth;
    KProgressHUD hud;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = edFirstName.getText().toString().trim();
                lName = edLastName.getText().toString().trim();
                phone = edPhone.getText().toString().trim();
                age = edAge.getText().toString().trim();
                email = edEmail.getText().toString().trim();
                password = edPassword.getText().toString().trim();


                if (TextUtils.isEmpty(fName)) {
                    Toast.makeText(getApplicationContext(), "Enter your first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(lName)) {
                    Toast.makeText(getApplicationContext(), "Enter your last name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dob)) {
                    Toast.makeText(getApplicationContext(), "Enter your date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(age)) {
                    Toast.makeText(getApplicationContext(), "Enter your age", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                hud = KProgressHUD.create(RegisterActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(true)
                        .setBackgroundColor(R.color.color_primary)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();

//                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    hud.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();


                                } else {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference dbRef = database.getReference("Users");

                                    // Generate Unique Key.
                                    String id = FirebaseAuth.getInstance().getUid();
                                    User user = new User(id, fName, lName, email,dob,age,phone,gender);
                                    // Insert the attendance

                                    dbRef.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Utilities.saveString(RegisterActivity.this,"firstName",fName);
                                                Utilities.saveString(RegisterActivity.this,"lastName",lName);
                                                Utilities.saveString(RegisterActivity.this,"userId",id);
                                                Utilities.saveString(RegisterActivity.this,"email",email);
                                                Utilities.saveString(RegisterActivity.this,"dob",dob);
                                                Utilities.saveString(RegisterActivity.this,"age",age);
                                                Utilities.saveString(RegisterActivity.this,"phone",phone);
                                                Utilities.saveString(RegisterActivity.this,"gender",gender);
                                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    hud.dismiss();
                                }
                            }
                        });
            }
        });

    }



    private void initializeViews() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        edFirstName = findViewById(R.id.et_first_name);
        edLastName = findViewById(R.id.et_last_name);
        edEmail = findViewById(R.id.et_email);
        edPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        edAge = findViewById(R.id.et_age);
        edDob = findViewById(R.id.et_dob);
        edDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this,
                        myDateListener, year, month, day).show();
            }
        });
        edPhone = findViewById(R.id.et_phone);
        rdbMale = findViewById(R.id.rdbMale);
        rdbFemale = findViewById(R.id.rdbFemale);
        rdbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gender = "male";
                }
            }
        });
        rdbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gender = "female";
                }
            }
        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        edDob.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        dob=edDob.getText().toString();
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

}