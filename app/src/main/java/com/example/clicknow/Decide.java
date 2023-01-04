package com.example.clicknow;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Decide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);
        Button register = (Button) findViewById(R.id.register);
        Button empRegister = (Button) findViewById(R.id.empRegister);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                .makeText(getApplicationContext(), "inside",Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Decide.this, SignupActivity.class);
                Decide.this.startActivity(myIntent);

            }
        });
        empRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                .makeText(getApplicationContext(), "inside",Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Decide.this, EmpSignUp.class);
                Decide.this.startActivity(myIntent);

            }
        });
    }
}