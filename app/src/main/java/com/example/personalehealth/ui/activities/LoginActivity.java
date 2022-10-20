package com.example.personalehealth.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalehealth.R;
import com.example.personalehealth.ui.model.User;
import com.example.personalehealth.utils.Utilities;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
     private FirebaseAuth auth;
    private Button btnLogin;
    KProgressHUD hud;
    private TextView tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        initializeViews();
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Email To Reset Password Has Send To "+email, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                hud = KProgressHUD.create(LoginActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(true)
                        .setBackgroundColor(R.color.color_primary)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                auth =FirebaseAuth.getInstance();
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(LoginActivity.this, "Auth Failed", Toast.LENGTH_LONG).show();

                                } else {

                                    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                    FirebaseDatabase.getInstance().getReference("Users").child(uid).
                                            addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    User user = snapshot.getValue(User.class);
                                                    assert user != null;
                                                    String firstName = user.getFirstName();
                                                    String lastName = user.getLastName();
                                                    String userId = user.getId();
                                                    String email = user.getEmail();
                                                    String dob = user.getDob();
                                                    String age = user.getAge();
                                                    String gender = user.getGender();
                                                    String phone = user.getPhone();


                                                    Utilities.saveString(LoginActivity.this,"firstName",firstName);
                                                    Utilities.saveString(LoginActivity.this,"lastName",lastName);
                                                    Utilities.saveString(LoginActivity.this,"userId",userId);
                                                    Utilities.saveString(LoginActivity.this,"email",email);
                                                    Utilities.saveString(LoginActivity.this,"dob",dob);
                                                    Utilities.saveString(LoginActivity.this,"age",age);
                                                    Utilities.saveString(LoginActivity.this,"phone",phone);
                                                    Utilities.saveString(LoginActivity.this,"gender",gender);
                                                    Toast.makeText(getApplicationContext(), firstName + " " + lastName, Toast.LENGTH_SHORT).show();

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }

    private void initializeViews() {
        inputEmail = findViewById(R.id.et_email);
        inputPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_signin);
        tv_forget = findViewById(R.id.tv_forget);
//        auth = FirebaseAuth.getInstance();
    }

    public void gotoRegister(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}