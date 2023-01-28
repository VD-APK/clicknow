package com.example.clicknow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clicknow.com.example.wit.bean.Employee;
import com.example.clicknow.com.example.wit.bean.LoginBean;
import com.example.clicknow.com.example.wit.bean.PincodeBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmpSignUp extends AppCompatActivity {
    EditText mobileNumber,password;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    Spinner spinner,spinner2;
    EditText pinCod,empName,aadharDetail;
    private EditText otp1, otp2, otp3, otp4;
    private TextView mobileNumberview;
    private TextView resendButton;
    private AppCompatButton verifyButton;
    private String mobileNumberText, serviceSpinner ;
    Button empSignUp;
    List<String> serviceList;
    private int resendTime=60;
    private int selectedOtpPosition=0;
    private boolean resetEnabled=false;
    String otp="";
    boolean validatePhone(String input)
    {

        Pattern p = Pattern.compile("[6-9]\\d{9}");
        Matcher m=p.matcher(input);
        return m.matches();
    }

    boolean validatePinCode(String input)
    {

        Pattern p = Pattern.compile("[1-9][0-9]{5}");
        Matcher m=p.matcher(input);
        return m.matches();
    }

    boolean validatePassCode(String input)
    {
        Pattern p = Pattern.compile("[a-zA-Z0-9]+");
        Matcher m=p.matcher(input);
        return m.matches();
    }
    boolean validateName(String input)
    {
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m=p.matcher(input);
        return m.matches();
    }

    boolean validateAadhaar(String input)
    {
        Pattern p = Pattern.compile("^[0-9]{4}[ -]?[0-9]{4}[ -]?[0-9]{4}$");
        Matcher m=p.matcher(input);
        return m.matches();
    }
    List<String> services=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup_emp);
      serviceList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
         spinner = findViewById(R.id.serviceSpinner);
         empSignUp =  findViewById(R.id.empRgister);

        databaseReference3 = firebaseDatabase.getReference("Service");


            databaseReference3.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot snapshot = task.getResult();


                            try {
                                services = (List<String>) snapshot.getValue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            serviceList.add(0,"Job type");
                            serviceList.addAll(services);
                           loadSpiner();
                        }
                    }
                }
            });


        mobileNumber = findViewById(R.id.empMobileNumber);
        password = findViewById(R.id.empPassword);
         pinCod = findViewById(R.id.empPinCode);
         empName = findViewById(R.id.empName);
         aadharDetail = findViewById(R.id.aadhar);

        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validatePhone(mobileNumber.getText().toString()))
                {
                    empSignUp.setEnabled(true);
                    mobileNumber.setError(null);

                }
                else{
                    empSignUp.setEnabled(false);
                    mobileNumber.setError("Invalid Mobile No");

                }

            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validatePassCode(password.getText().toString()))
                {
                    empSignUp.setEnabled(true);
                    password.setError(null);

                }
                else{
                    empSignUp.setEnabled(false);
                    password.setError("Invalid Password");

                }

            }
        });

        pinCod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validatePinCode(pinCod.getText().toString()))
                {
                    empSignUp.setEnabled(true);
                    pinCod.setError(null);

                }
                else{
                    empSignUp.setEnabled(false);
                    pinCod.setError("Invalid pinCode");

                }

            }
        });


        empName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validateName(empName.getText().toString()))
                {
                    empSignUp.setEnabled(true);
                    empName.setError(null);

                }
                else{
                    empSignUp.setEnabled(false);
                    empName.setError("Invalid Name");

                }

            }
        });


        aadharDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validateAadhaar(aadharDetail.getText().toString()))
                {
                    empSignUp.setEnabled(true);
                    aadharDetail.setError(null);

                }
                else{
                    empSignUp.setEnabled(false);
                    aadharDetail.setError("Invalid Aadhar Number");

                }

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,int position, long id) {
                // On selecting a spinner item
              if(position==0)
              {
                 // empSignUp.setEnabled(false);
                  spinner.getFocusable();

              }
              else{
                  empSignUp.setEnabled(true);
                  spinner.clearFocus();
              }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                spinner.getFocusable();
            }
        });



        empSignUp.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (validateInput( mobileNumber.getText().toString(), empName.getText().toString(),password.getText().toString() , pinCod.getText().toString(),aadharDetail.getText().toString(),spinner)) {

                long mobileNo = Long.valueOf(mobileNumber.getText().toString());
                int pinCode = Integer.parseInt(pinCod.getText().toString());

                String passCode = password.getText().toString();
                String employeeName = empName.getText().toString();

                try {
                     serviceSpinner = spinner.getSelectedItem().toString();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                String aadharNumber = aadharDetail.getText().toString();

                LoginBean loginBean = new LoginBean();
                loginBean.setMobileNumber(mobileNo);
                loginBean.setPassCode(passCode);
                loginBean.setEmp(true);


                    databaseReference = firebaseDatabase.getReference("login");



                    databaseReference.child(Long.toString(mobileNo)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot snapshot = task.getResult();
                                    LoginBean bean = snapshot.getValue(LoginBean.class);
                                    if (bean.getMobileNumber() == loginBean.getMobileNumber()) {
                                        Toast.makeText(getApplicationContext(), "Registration Un-Successful User already exist", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(EmpSignUp.this, MainActivity.class);
                                        EmpSignUp.this.startActivity(myIntent);

                                    }


                                }
                            }
                        }
                    });


                    AlertDialog.Builder mBuilder= new AlertDialog.Builder(EmpSignUp.this);
                    View viewOtp=getLayoutInflater().inflate(R.layout.otp_dialog,null);
                    mBuilder.setView(viewOtp);

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

                    setContentView(R.layout.otp_dialog);

                    otp1 = findViewById(R.id.otp1);
                    otp2 = findViewById(R.id.otp2);
                    otp3 = findViewById(R.id.otp3);
                    otp4 = findViewById(R.id.otp4);
                    resendButton= findViewById(R.id.resedOtp);
                    mobileNumberview =findViewById(R.id.mobileNumberText);
                    verifyButton = findViewById(R.id.verifyBtn);

                    otp1.addTextChangedListener(textWatcher);
                    otp2.addTextChangedListener(textWatcher);
                    otp3.addTextChangedListener(textWatcher);
                    otp4.addTextChangedListener(textWatcher);

                    showKeyboard(otp1);
                    startCountDownTimer();
                    otp=sendOTP(mobileNumber.getText().toString());
                    mobileNumberview.setText(mobileNumberText);
                    resendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(resetEnabled)
                            {
                                otp=sendOTP(mobileNumber.getText().toString());
                                startCountDownTimer();
                            }
                        }
                    });

                    verifyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final  String getOtp=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();

                            if(getOtp.length()==4){

                                if(getOtp.equals(otp))
                                {
                                    databaseReference.child(Long.toString(mobileNo)).setValue(loginBean).addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                Employee employee = new Employee();
                                                employee.setEmpName(employeeName);
                                                employee.setMobileNumber(mobileNo);
                                                employee.setPassCode(passCode);
                                                employee.setPinCode(pinCode);
                                                employee.setJobType(serviceSpinner);
                                                employee.setAadharNumber(aadharNumber);
                                                employee.setRating("0");
                                                databaseReference = firebaseDatabase.getReference("Employee");

                                                databaseReference.child(Long.toString(mobileNo)).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                        }


                                                    }
                                                });


                                            } else {
                                                Toast.makeText(getApplicationContext(), "Registration Un Successful", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });


                                    PincodeBean pinBean = new PincodeBean();


                                    databaseReference2 = firebaseDatabase.getReference("linkTable");
                                    databaseReference2.child(Integer.toString(pinCode)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            List<Long> linkList = new ArrayList<>();
                                            if (task.isSuccessful()) {

                                                try {
                                                    DataSnapshot snapshot = task.getResult();

                                                    PincodeBean pinCodeBean = snapshot.getValue((PincodeBean.class));
                                                    linkList = pinCodeBean.getList();

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                if (linkList != null && linkList.size() > 0) {

                                                    linkList.add(mobileNo);

                                                } else {

                                                    linkList.add(mobileNo);
                                                }
                                            }

                                            pinBean.setList(linkList);
                                            databaseReference2.child(Integer.toString(pinCode)).setValue(pinBean).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                                        Intent myIntent = new Intent(EmpSignUp.this, MainActivity.class);
                                                        EmpSignUp.this.startActivity(myIntent);
                                                    }
                                                }


                                            });
                                        }

                                    });


                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "wrong otp", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }


                    });

                }
                else {


                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    private void loadSpiner() {

        String[] arrayList = serviceList.toArray(new String[serviceList.size()]);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.text1, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.length()>0)
            {
                if(selectedOtpPosition==0)
                {
                    selectedOtpPosition=1;
                    showKeyboard(otp2);
                }
                else if(selectedOtpPosition==1){
                    selectedOtpPosition=2;
                    showKeyboard(otp3);

                }
                else if(selectedOtpPosition==2){
                    selectedOtpPosition=3;
                    showKeyboard(otp4);

                }
                else{
                    verifyButton.setBackgroundColor(R.drawable.round_back_red_10);
                }
            }

        }




    };

    private String sendOTP(String mobNumber) {
        int randomPIN = (int)(Math.random()*9000)+1000;
        String val = ""+randomPIN;


        String url="http://sms.co3.live/api/smsapi?key=7d5c59941cf306bf6f37c308b43263f5&route=2&sender=WITORG&number="+mobNumber+"&templateid=1007178949079546167&sms=Dear Customer, " +val+" is your one time password. Please enter the OTP to proceed.Thank you,Team WIT";

        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("success");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("failure");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        queue.start();
        return val;
    }

    private void showKeyboard(EditText otp)
    {
        otp.requestFocus();

        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp,InputMethodManager.SHOW_IMPLICIT);

    }

    private void startCountDownTimer(){
        resetEnabled=false;
        // resendButton.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendButton.setText("Resend Code ("+(millisUntilFinished/1000)+")");
            }

            @Override
            public void onFinish() {
                resetEnabled=true;
                resendButton.setText("Resend Code");
                resendButton.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));




            }
        }.start();
    }


    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_DEL) {

            if (selectedOtpPosition == 3) {
                selectedOtpPosition = 2;
                showKeyboard(otp3);
            } else if (selectedOtpPosition == 2) {
                selectedOtpPosition = 1;
                showKeyboard(otp2);
            } else if (selectedOtpPosition == 1) {
                selectedOtpPosition = 0;
                showKeyboard(otp1);
            }
            if(selectedOtpPosition>1)
                verifyButton.setBackgroundResource(R.drawable.round_back_brown);
            return true;

        } else {


            return super.onKeyUp(keyCode, event);
        }

    }



    private boolean validateInput(String mobile, String userName, String passwordString, String pincode,String aadharNumber,Spinner spinnerItem) {

        boolean flag;

        if(spinnerItem != null && spinnerItem.getSelectedItem() !=null ) {
            {
                // On selecting a spinner item
                if(spinnerItem.getSelectedItem().toString().equalsIgnoreCase("Job type"))
                {
                    empSignUp.setEnabled(false);
                    spinner.getFocusable();
                    return false;
                }
                else{
                    empSignUp.setEnabled(true);
                    spinner.clearFocus();
                    empSignUp.setEnabled(true);
                    spinner.clearFocus();
                    flag=true;
                }
            }
        }
        if(validatePhone(mobile))
        {
            empSignUp.setEnabled(true);
            mobileNumber.setError(null);
            flag=true;

        }
        else{
            empSignUp.setEnabled(false);
            mobileNumber.setError("Invalid Mobile No");
               return false;
        }

        if(validatePassCode(passwordString))
        {
            empSignUp.setEnabled(true);
            password.setError(null);
            flag=true;
        }
        else{
            empSignUp.setEnabled(false);
            password.setError("Invalid Password");
               return false;
        }
        if(validateName(userName))
        {
            empSignUp.setEnabled(true);
            empName.setError(null);
            flag=true;
        }
        else{
            empSignUp.setEnabled(false);
            empName.setError("Invalid Name");
               return false;
        }

        if(validatePinCode(pincode))
        {
            empSignUp.setEnabled(true);
            pinCod.setError(null);
            flag=true;
        }
        else{
            empSignUp.setEnabled(false);
            pinCod.setError("Invalid pinCode");
               return false;
        }

        if(validateAadhaar(aadharNumber))
        {
            empSignUp.setEnabled(true);
            aadharDetail.setError(null);
            flag=true;
        }
        else{
            empSignUp.setEnabled(false);
            aadharDetail.setError("Invalid aadhar number");
               return false;
        }


        return flag;
    }


}
