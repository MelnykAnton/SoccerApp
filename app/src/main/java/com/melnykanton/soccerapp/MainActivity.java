package com.melnykanton.soccerapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signIn_button;
    private Button signUp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        //buttons
        signIn_button = (Button) findViewById(R.id.btn_signin);
        signUp_button = (Button) findViewById(R.id.btn_signup);

        signIn_button.setOnClickListener(this);
        signUp_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_signin:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.btn_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }

    }
}
