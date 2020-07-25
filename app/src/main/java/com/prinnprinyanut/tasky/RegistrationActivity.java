package com.prinnprinyanut.tasky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private Toolbar registerToolbar;
    private EditText registerEmail, registerPassword;
    private Button registerButton;
    private TextView registerQuestion;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        registerToolbar = findViewById(R.id.registerToolbar);
        setSupportActionBar(registerToolbar);
        getSupportActionBar().setTitle("Sign up");

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        registerQuestion = findViewById(R.id.registerPageQuestion);

        registerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    registerEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    registerPassword.setError("Password is required");
                    return;
                } else {
                    loader.setMessage("Registration in progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();
                                } else {
                                    String error = task.getException().toString();
                                    Toast.makeText(RegistrationActivity.this, "Registration failed! " + error, Toast.LENGTH_SHORT).show();
                                    loader.dismiss();
                                }

                            }
                        }
                    );
                }



            }
        });

    }
}