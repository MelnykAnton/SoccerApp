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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "Authenticate";

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListner;

    private EditText email_input;
    private EditText psw_input;

    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_input = (EditText) findViewById(R.id.email_text_edit);
        psw_input = (EditText) findViewById(R.id.psw_text_edit);

        login_button = (Button) findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG,"Authenticate successful");
                }else {
                    Log.d(TAG, "!!!Something goes wrong!!!");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }

    public boolean validForm(){
        boolean valid = false;

        String mail = email_input.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            email_input.setError("Required");
            valid = false;
        }else {
            email_input.setError(null);
        }

        String psw = psw_input.getText().toString();
        if (TextUtils.isEmpty(psw)) {
            psw_input.setError("Required");
            valid = false;
        }else {
            psw_input.setError(null);
        }
        return valid;
    }

    private void signIn(String mail, String pass) {
        Log.d(TAG, "SignIn: " +mail);

        if (validForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"SignIn:onComplete: " + task.isSuccessful());

                        if (!task.isSuccessful()){
                            Log.d(TAG, "SignIn: failed" + task.getException());
                            Toast.makeText(SignInActivity.this,"Filed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {

    }
}
