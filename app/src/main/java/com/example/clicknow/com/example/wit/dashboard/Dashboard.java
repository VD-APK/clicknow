package com.example.clicknow.com.example.wit.dashboard;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.clicknow.R;
import com.example.clicknow.com.example.wit.bean.Employee;
import com.example.clicknow.com.example.wit.bean.PincodeBean;
import com.example.clicknow.com.example.wit.bean.UserBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2, userDatabaseReference, databaseReference3,databaseReference4;
    ListView listView, listViewEmp;
    List<String> serviceList = new ArrayList<>();
    List<String> empPinList = new ArrayList<>();

    List<Employee> employeePincodeList = new ArrayList<>();
    boolean checkFlag = false;
    ArrayAdapter<String> arrayAdapter, arrayAdapterEmp;
    EditText etSearch;
    TextView username, empClick;

    UserBean userBean;
    long mobileNumber;
    List<Long> linkList = new ArrayList<>();
    Employee employee;
    List<Employee> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        empClick = findViewById(R.id.empClick);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        Spinner spinner = findViewById(R.id.spinner);
        username = findViewById(R.id.userNameText);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mobileNumber = extras.getLong("userName");
            //The key argument here must match that used in the other activity
        }

        userDatabaseReference = firebaseDatabase.getReference("User");

        String number = Long.toString(mobileNumber);
        Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();


        try {
            userDatabaseReference.child(number).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot snapshot = task.getResult();
                            userBean = snapshot.getValue(UserBean.class);

                            username.setText("Hi " + userBean.getUserName().toUpperCase(Locale.ROOT));

                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            databaseReference = firebaseDatabase.getReference("Service");

            databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot snapshot = task.getResult();
                            List<String> services = new ArrayList<>();

                            try {
                                services = (List<String>) snapshot.getValue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            serviceList.addAll(services);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.listViewJob);
        listViewEmp = findViewById(R.id.listViewEmp);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.text2, serviceList);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(arrayAdapter);
//        spinner.setAdapter(arrayAdapter);291


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = listView.getItemAtPosition(position);

                clearListViewEmp();


                databaseReference2 = firebaseDatabase.getReference("linkTable");
                databaseReference2.child(Integer.toString(userBean.getPinCode())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        linkList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    DataSnapshot snapshot = task.getResult();

                                    PincodeBean pinCodeBean = snapshot.getValue((PincodeBean.class));
                                    if (pinCodeBean != null && pinCodeBean.getList() != null) {
                                        linkList = pinCodeBean.getList();
                                        loadEmpList(listItem.toString());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No Employee found in this area", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No Employee found in this area", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No Employee found in this area", Toast.LENGTH_SHORT).show();
                        }


                    }

                });


            }

        });

        listViewEmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object selectedEmp = listViewEmp.getItemAtPosition(position);
                try {
                    int number = Integer.parseInt(selectedEmp.toString());
                    employee = employeePincodeList.get(number);
                } catch (Exception e) {
                    e.printStackTrace();
                    employee = employeePincodeList.get(0);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setMessage("Name : " + employee.getEmpName() + " Mobile Number : " + employee.getMobileNumber())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Map<String, String> empMap = employee.getUserRequested();
                                if (empMap == null) {
                                    empMap = new HashMap<>();
                                }
                                empMap.put(Long.toString(userBean.getMobileNumber()), userBean.getUserName());

                                employee.setUserRequested(empMap);
                                insertEmp(employee);
                                builder.setCancelable(true);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });


    }


    public void loadEmpList(String jobType) {
        databaseReference3 = firebaseDatabase.getReference("Employee");
        if (linkList.size() > 0) {
            empPinList.clear();
            employeePincodeList.clear();
            for (Long l : linkList) {

                databaseReference3.child(Long.toString(l)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot snapshot = task.getResult();
                                Employee emp = snapshot.getValue(Employee.class);
                                if (emp.getJobType().equalsIgnoreCase(jobType)) {
                                    empPinList.add("Name :" + emp.getEmpName() + " ,Rating : " + emp.getRating());
                                    employeePincodeList.add(emp);
                                    checkFlag = true;
                                    loadSecondList();

                                } else {
                                    if (empPinList.size() == 0) {
                                        empClick.setText("");
                                        Toast.makeText(getApplicationContext(), "No Employee found in this area", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }
                        }

                    }
                });


            }

        } else {
            Toast.makeText(getApplicationContext(), "No Employee found in this area", Toast.LENGTH_SHORT).show();
        }


    }

    private void insertEmp(Employee emp) {
        try {
          Long mob=  emp.getMobileNumber();
        //  mob=mob+5;
          String s=Long.toString(mob);
            databaseReference4 = firebaseDatabase.getReference("Employee");
            databaseReference4.child(s).setValue(emp).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadSecondList() {


        empClick.setText("Click to get the mobile number");

        arrayAdapterEmp = new ArrayAdapter<String>(this, R.layout.text2, empPinList);

        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewEmp.setAdapter(arrayAdapterEmp);
    }

    private void clearListViewEmp() {
        List<String> emptyList = new ArrayList<>();
        arrayAdapterEmp = new ArrayAdapter<String>(this, R.layout.text1, emptyList);

        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewEmp.setAdapter(arrayAdapterEmp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        try {
            MenuItem menuItem = menu.findItem(R.id.action_search);

            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("type here to search ");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    arrayAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }
}
