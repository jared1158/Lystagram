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

public class MainActivity extends AppCompatActivity {

    private EditText etUN;
    private EditText etPW;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUN = findViewById(R.id.etUN);
        etPW = findViewById(R.id.etPW);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUN.getText().toString();
                final String password = etPW.getText().toString();

                login(username,password);
            }
        });
    }


    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("LoginActivity" , "login successful");
                    final Intent i = new Intent(MainActivity.this , HomeActivity.class);
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
