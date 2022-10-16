package com.example.personalehealth.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalehealth.R;
import com.example.personalehealth.ui.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

public class RegisterActivity extends AppCompatActivity {

    EditText edFirstName, edLastName, edEmail, edPassword;
    Button btnRegister;
    String fName, lName, email, password;
    FirebaseAuth mAuth;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = edFirstName.getText().toString().trim();
                lName = edLastName.getText().toString().trim();
                email = edEmail.getText().toString().trim();
                password = edPassword.getText().toString().trim();

                if (TextUtils.isEmpty(lName)) {
                    Toast.makeText(getApplicationContext(), "Enter your last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fName)) {
                    Toast.makeText(getApplicationContext(), "Enter your first name", Toast.LENGTH_SHORT).show();
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

                //create user
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
                                    User user = new User(id, fName, lName, email);
                                    // Insert the attendance
                                    assert id != null;
                                    dbRef.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
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
        edFirstName = findViewById(R.id.et_first_name);
        edLastName = findViewById(R.id.et_last_name);
        edEmail = findViewById(R.id.et_email);
        edPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
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