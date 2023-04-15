package com.example.clicknow.com.example.wit.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clicknow.R;
import com.example.clicknow.com.example.wit.bean.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EmpDashoardActivity extends AppCompatActivity {


    TextView username;
    long mobileNumber;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Employee employee;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> userList = new ArrayList<>();
    List<String> userViewList = new ArrayList<>();
    EditText deleteJobID ;
    Map<String, String> empMap=new HashMap<>();
    Button deleteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_dashoard);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        username = findViewById(R.id.empNameText);
        listView = findViewById(R.id.userListJob);
//        deleteJobID = findViewById(R.id.deleteJobId);
//         deleteService = (Button) findViewById(R.id.deleteJobButton);
        if (extras != null) {
            mobileNumber = extras.getLong("userName");
            //The key argument here must match that used in the other activity
        }



        databaseReference = firebaseDatabase.getReference("Employee");

        String number = Long.toString(mobileNumber);

        try {

            databaseReference.child(number).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot snapshot = task.getResult();
                            employee = snapshot.getValue(Employee.class);

                            username.setText("Hi " + employee.getEmpName().toUpperCase(Locale.ROOT));
                            loadUserList(employee);

                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


//        deleteJobID.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        try {
//                            if (s != null && s.length()>0)
//                                if (Integer.parseInt(s.toString()) <= empMap.size()) {
//                                    deleteService.setEnabled(true);
//
//                                } else {
//                                    deleteService.setEnabled(false);
//
//                                }
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//
//        deleteService
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        String ServiceNamee = deleteJobID.getText().toString();
//
//
//                        empMap.remove(Integer.parseInt(ServiceNamee));
//                        employee.setUserRequested(empMap);
//
//                        databaseReference.child(Long.toString(employee.getMobileNumber())).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    empMap.clear();
//                                    userList.clear();
//                                    startActivity(getIntent());
//                                    finish();
//                                }
//                            }
//                        });
//                    }
//                });
//


    }

    private void loadUserList(Employee emp) {


        empMap = emp.getUserRequested();

        int i =0;
        if(empMap==null)
        {

        }else {


            for (Map.Entry<String, String> entry : empMap.entrySet()) {
                i++;

                userList.add(i + ". Name : " + entry.getValue() + " Number : " + entry.getKey());
            }

            arrayAdapter = new ArrayAdapter<String>(this, R.layout.text2, userList);

            //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listView.setAdapter(arrayAdapter);
        }

    }
}