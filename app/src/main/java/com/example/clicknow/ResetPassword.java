package com.example.clicknow;

import android.content.Intent;
import android.os.Bundle;

import com.example.clicknow.com.example.wit.bean.LoginBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {
    EditText mobileNumber,password, retypePassword;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String passCode="";
    String retypePasscode ="";
    LoginBean loginBean;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this,R.color.white);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loginBean = (LoginBean) extras.getSerializable("LoginBean");
            //The key argument here must match that used in the other activity
        }

        mobileNumber = findViewById(R.id.userMobileNumber);
        password = findViewById(R.id.password);
        retypePassword =  findViewById(R.id.retypePassword);



        Button  resetPassword = (Button) findViewById(R.id.resetPassword);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                passCode = password.getText().toString();
                retypePasscode = retypePassword.getText().toString();

                if (validatePassCode(retypePasscode)&&validatePassCode( passCode)&& retypePasscode.equalsIgnoreCase(passCode)) {

                    passCode = password.getText().toString();
                    retypePasscode = retypePassword.getText().toString();

                    databaseReference = firebaseDatabase.getReference("login");

                    loginBean.setPassCode(passCode);


 
                            HashMap hashMap= new HashMap();
                            hashMap.put("emp",loginBean.isEmp());
                            hashMap.put("mobileNumber",loginBean.getMobileNumber());
                            hashMap.put("passCode",loginBean.getPassCode());
                            databaseReference.child(String.valueOf(loginBean.getMobileNumber())).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Intent myIntent = new Intent(ResetPassword.this, MainActivity.class);
                                        ResetPassword.this.startActivity(myIntent);
                                        Toast.makeText(getApplicationContext(), "Password changed successfully ", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Change Un Successful", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                else{
                    Toast.makeText(getApplicationContext(), "password and retype password should match", Toast.LENGTH_SHORT).show();
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