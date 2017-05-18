package com.melnykanton.soccerapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends SignInActivity implements View.OnClickListener{

    private final String TAG = "SignUp";

    private EditText signUpEmail;
    private EditText signUpPass;

    private Button createBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signUpEmail = (EditText) findViewById(R.id.signup_email_edit_text);
        signUpPass = (EditText) findViewById(R.id.signup_psw_edit_text);

        createBtn = (Button) findViewById(R.id.create_btn);
        createBtn.setOnClickListener(this);

    }

    private void createAccount(String mail, String pass) {
        Log.d(TAG, "Create acc: " + mail);

        if (!validForm()){
            Log.d(TAG, "Error in valid form");
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"Create user: " + task.isSuccessful());

                        if(!task.isSuccessful()){
                            Log.d(TAG, "Create failed");
                            Toast.makeText(SignUpActivity.this,"Create failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean validForm(){
        boolean valid = true;

        String mail = signUpEmail.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            signUpEmail.setError("Required");
            valid = false;
            Log.d(TAG,"SignIn: Error in email input");
        }else {
            signUpEmail.setError(null);
        }

        String pass = signUpPass.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            signUpPass.setError("Required");
            valid = false;
            Log.d(TAG,"SignIn: Error in pass input");
        }else {
            signUpPass.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.create_btn:
            createAccount(signUpEmail.getText().toString(), signUpPass.getText().toString());
                break;
        }

    }
}
