package com.example.clicknow.com.example.wit.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.clicknow.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference empDatabaseReference;
    DatabaseReference userDatabaseReference;
    EditText serviceName,deleteText;
    ListView listView;
    EditText etSearch;
    List<String> serviceList = new ArrayList<>();
    List<String> serviceListView = new ArrayList<>();
    List<String> userList = new ArrayList<>();
    List<String> usersList = new ArrayList<>();
    List<String> empList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    List<String> stringList= new ArrayList<>();
    ListView userView;
    EditText userSearch;

    ListView empView;
    EditText empSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Button addService = (Button) findViewById(R.id.addService);
        Button deleteService = (Button) findViewById(R.id.deleteService);
        firebaseDatabase = FirebaseDatabase.getInstance();
        deleteText= findViewById(R.id.deleteText);
        databaseReference = firebaseDatabase.getReference("Service");

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot snapshot = task.getResult();

                        int i=0;
                        List<String> services = (List<String>) snapshot.getValue();
                        for(String s:services) {
                            i++;
                            stringList.add(i+" : " + s);
                        }
                        if (services != null) {
                            serviceList.addAll(services);
                            serviceListView.addAll(stringList);
                        }
                        loadServiceList();
                    }
                }
            }
        });

        listView = findViewById(R.id.listView);
        etSearch = findViewById(R.id.etSearch);

        deleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s != null && s.length()>0)
                        if (Integer.parseInt(s.toString()) <= serviceList.size()) {
                            deleteService.setEnabled(true);

                        } else {
                            deleteService.setEnabled(false);

                        }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               serviceName= findViewById(R.id.serviceName);
                String ServiceNamee = serviceName.getText().toString();
                serviceList.add(ServiceNamee);

                databaseReference.setValue(serviceList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            stringList.clear();
                            serviceList.clear();
                            startActivity(getIntent());
                            finish();
                        }
                    }
                });

            }
        });

        deleteService
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String ServiceNamee = deleteText.getText().toString();

                        serviceList.remove(Integer.parseInt(ServiceNamee));
                        databaseReference.setValue(serviceList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    stringList.clear();
                                    serviceList.clear();
                                    startActivity(getIntent());
                                    finish();
                                }
                            }
                        });

                    }
                });
    }

    private void loadServiceList() {
         arrayAdapter = new ArrayAdapter<String>(this,R.layout.text1, serviceListView);
      //  arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, R.layout.text1, serviceList);
        listView.setAdapter(arrayAdapter);
    }
}