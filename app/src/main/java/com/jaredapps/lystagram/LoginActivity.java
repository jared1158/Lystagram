package com.jaredapps.lystagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText etUN;
    private EditText etPW;
    private Button btnLogin;
    private Button btnSign;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etSname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ParseUser neu_user = new ParseUser();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            final Intent I = new Intent(LoginActivity.this , CameraActivity.class);
            startActivity(I);
            finish();

        }
        else{

        setContentView(R.layout.activity_main);

        etUN = findViewById(R.id.etUN);
        etPW = findViewById(R.id.etPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnSign = findViewById(R.id.btnSign);
        etSname = findViewById(R.id.etSname);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUN.getText().toString();
                final String password = etPW.getText().toString();

                login(username,password);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String new_un = etSname.getText().toString();
                final String new_pw = etPassword.getText().toString();
                final String new_em = etEmail.getText().toString();
                neu_user.setUsername(new_un);
                neu_user.setPassword(new_pw);
                neu_user.setEmail(new_em);
                signup(neu_user);
            }
        });

    }}


    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("LoginActivity" , "login successful");
                    final Intent i = new Intent(LoginActivity.this , CameraActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Log.e("LoginActivity" , "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }

    private void signup(ParseUser user){

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignUpActivity" , "sign up successful");
                    final Intent i = new Intent(LoginActivity.this , CameraActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }


}
