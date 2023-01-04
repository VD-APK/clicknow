package com.example.clicknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clicknow.com.example.wit.bean.LoginBean;
import com.example.clicknow.com.example.wit.dashboard.AdminDashboard;
import com.example.clicknow.com.example.wit.dashboard.Dashboard;
import com.example.clicknow.com.example.wit.dashboard.EmpDashoardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText userName,password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this,R.color.white);

        Button logIn = (Button) findViewById(R.id.login);

        Button signUp = (Button) findViewById(R.id.signUp);


        logIn.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                userName = findViewById(R.id.userName);
                password = findViewById(R.id.password);
                Long mobileNo = Long.valueOf(userName.getText().toString());
                String passCode = password.getText().toString();

                if (mobileNo == 1234 && passCode.equals("1234")) {
                    Intent myIntent = new Intent(MainActivity.this, AdminDashboard.class);
                    MainActivity.this.startActivity(myIntent);
                } else {

                    databaseReference = firebaseDatabase.getReference("login");

                    LoginBean loginBean = new LoginBean();
                    loginBean.setMobileNumber(mobileNo);
                    loginBean.setPassCode(passCode);


                    databaseReference.child(Long.toString(mobileNo)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot snapshot = task.getResult();
                                    LoginBean bean = snapshot.getValue(LoginBean.class);
                                    if (bean.getMobileNumber() == loginBean.getMobileNumber() && bean.getPassCode().equals(loginBean.getPassCode())) {
                                     //   Toast.makeText(getApplicationContext(), "Login  Successful", Toast.LENGTH_SHORT).show();
                                        if(!bean.isEmp())
                                        {
                                            Intent myIntent = new Intent(MainActivity.this, Dashboard.class);
                                            myIntent.putExtra("userName",mobileNo);
                                            MainActivity.this.startActivity(myIntent);

                                        }
                                        else{
                                            Intent myIntent = new Intent(MainActivity.this, EmpDashoardActivity.class);
                                            myIntent.putExtra("userName",mobileNo);
                                            MainActivity.this.startActivity(myIntent);
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });


                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, Decide.class);
                MainActivity.this.startActivity(myIntent);

            }
        });



    }
}/**/