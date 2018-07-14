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

public class LoginActivity extends AppCompatActivity {

    private EditText etUN;
    private EditText etPW;
    private Button btnLogin;
    private Button btnSignup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            final Intent I = new Intent(LoginActivity.this , FeedActivity.class);
            startActivity(I);
            finish();

        }
        else{

        setContentView(R.layout.activity_main);

        etUN = findViewById(R.id.etUN);
        etPW = findViewById(R.id.etPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUN.getText().toString();
                final String password = etPW.getText().toString();

                login(username,password);
            }
        });

            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent r = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(r);

                }
            });


    }}


    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("LoginActivity" , "login successful");
                    final Intent i = new Intent(LoginActivity.this , FeedActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Log.e("LoginActivity" , "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }




}
