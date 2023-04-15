package com.example.clicknow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clicknow.com.example.wit.bean.LoginBean;
import com.example.clicknow.com.example.wit.bean.UserBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clicknow.databinding.ActivityResetPasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {
    EditText mobileNumber,password,retryPassword;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String passCode="";
    String retryPasscode="";
    String userName="";
    Button resetPassword ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
            //The key argument here must match that used in the other activity
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseDatabase = FirebaseDatabase.getInstance();


        mobileNumber = findViewById(R.id.userMobileNumber);
        password = findViewById(R.id.userPassword);
        retryPasscode =  findViewById(R.id.retryPassword);



        resetPassword = (Button) findViewById(R.id.resetPassword);
        passCode = password.getText().toString();
        retryPasscode=retryPassword.getText().toString();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validatePassCode(retryPasscode)&&validatePassCode( passCode)&&retryPasscode.equalsIgnoreCase(passCode)) {



                    passCode = password.getText().toString();
                    retryPasscode=retryPassword.getText().toString();



                    databaseReference = firebaseDatabase.getReference("login");

                    LoginBean loginBean = new LoginBean();
                    loginBean.setMobileNumber(Long.parseLong(userName));
                    loginBean.setPassCode(passCode);





                    resetPassword.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                        databaseReference.child(userName).setValue(loginBean).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {




                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Registration Un Successful", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        finish();

                                    }



                        });


                    }


                }


        });
    }







    boolean validatePassCode(String input)
    {
        Pattern p = Pattern.compile("[a-zA-Z0-9]+");
        Matcher m=p.matcher(input);
        return m.matches();
    }

}