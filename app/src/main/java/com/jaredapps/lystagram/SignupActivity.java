package com.jaredapps.lystagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etSname;
    private Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        final ParseUser neu_user = new ParseUser();
        etSname = findViewById(R.id.etSname);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSign = findViewById(R.id.btnSign);




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

    }

    private void signup(ParseUser user){

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignUpActivity" , "sign up successful");
                    final Intent i = new Intent(SignupActivity.this , CameraActivity.class);
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
