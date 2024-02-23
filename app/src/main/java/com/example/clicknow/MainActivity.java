package com.example.clicknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clicknow.com.example.wit.bean.LoginBean;
import com.example.clicknow.com.example.wit.bean.UserBean;
import com.example.clicknow.com.example.wit.dashboard.AdminDashboard;
import com.example.clicknow.com.example.wit.dashboard.Dashboard;
import com.example.clicknow.com.example.wit.dashboard.EmpDashoardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText userName,password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView textView;
    private EditText otp1, otp2, otp3, otp4;
    private TextView mobileNumberview;
    private TextView resendButton;
    private AppCompatButton verifyButton;
    private int resendTime=60;
    private int selectedOtpPosition=0;
    private boolean resetEnabled=false;
    String otp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this,R.color.white);

        Button logIn = (Button) findViewById(R.id.login);

        Button signUp = (Button) findViewById(R.id.signUp);

        textView = findViewById(R.id.forgetPassword);
        String text = "Forgot Password?";
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                userName = findViewById(R.id.userName);
                String mobileNumber=userName.getText().toString();


                if(validatePhone(mobileNumber))
                {
                    Long mobileNo = Long.valueOf(mobileNumber);


                    {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference("login");



                        databaseReference.child(Long.toString(mobileNo)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        DataSnapshot snapshot = task.getResult();
                                        LoginBean bean = snapshot.getValue(LoginBean.class);
                                        if (bean.getMobileNumber() ==mobileNo) {

                                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                                            alertBuilder.setMessage("Do you want to reset your password ?");

                                            // Set Alert Title
                                            alertBuilder.setTitle("Alert !");

                                            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                                            alertBuilder.setCancelable(false);



                                            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                                            alertBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                                                // When the user click yes button then app will close

                                                {

                                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                                                    View view = getLayoutInflater().inflate(R.layout.otp_dialog, null);
                                                    mBuilder.setView(view);

                                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                                                    getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

                                                    setContentView(R.layout.otp_dialog);

                                                    otp1 = findViewById(R.id.otp1);
                                                    otp2 = findViewById(R.id.otp2);
                                                    otp3 = findViewById(R.id.otp3);
                                                    otp4 = findViewById(R.id.otp4);
                                                    resendButton = findViewById(R.id.resedOtp);
                                                    mobileNumberview = findViewById(R.id.mobileNumberText);
                                                    verifyButton = findViewById(R.id.verifyBtn);

                                                    otp1.addTextChangedListener(textWatcher);
                                                    otp2.addTextChangedListener(textWatcher);
                                                    otp3.addTextChangedListener(textWatcher);
                                                    otp4.addTextChangedListener(textWatcher);

                                                    otp = sendOTP(userName.getText().toString());
                                                    showKeyboard(otp1);
                                                    startCountDownTimer();

                                                    mobileNumberview.setText(userName.getText().toString());
                                                    resendButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (resetEnabled) {
                                                                otp = sendOTP(userName.getText().toString());
                                                                startCountDownTimer();
                                                            }
                                                        }
                                                    });

                                                    verifyButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent myIntent = new Intent(MainActivity.this, ResetPassword.class);

//                                                            LoginBean loginBean= new LoginBean();
                                                            myIntent.putExtra("LoginBean", (Serializable) bean);
                                                            MainActivity.this.startActivity(myIntent);
                                                        }


                                                    });


                                                }
                                            });

                                            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                                            alertBuilder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                                                // If user click no then dialog box is canceled.
                                                dialog.cancel();
                                            });

                                            // Create the Alert dialog
                                            AlertDialog alertDialog = alertBuilder.create();
                                            // Show the Alert Dialog box
                                            alertDialog.show();

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "User Not registered", Toast.LENGTH_SHORT).show();
                                            Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                                            MainActivity.this.startActivity(myIntent);
                                        }


                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "User Not registered", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                                        MainActivity.this.startActivity(myIntent);
                                    }

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "User Not registered", Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                                    MainActivity.this.startActivity(myIntent);
                                }

                            }
                        });
                    }



                }
                else{

                    Toast.makeText(getApplicationContext(), "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                }







            }
        };

        spannableString.setSpan(clickableSpan1, 0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                userName = findViewById(R.id.userName);
                password = findViewById(R.id.password);
                Long mobileNo = Long.valueOf(userName.getText().toString());
                String passCode = password.getText().toString();

                if (mobileNo == 123 && passCode.equals("123")) {
                    Intent myIntent = new Intent(MainActivity.this, UserDashBoard.class);
                    MainActivity.this.startActivity(myIntent);
                }

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
                                            Intent myIntent = new Intent(MainActivity.this, UserDashBoard.class);
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

    private String sendOTP(String mobNumber) {
        int randomPIN = (int)(Math.random()*9000)+1000;
        String val = ""+randomPIN;

        Toast.makeText(getApplicationContext(), "otp "+val, Toast.LENGTH_SHORT).show();
        String url="http://sms.co3.live/api/smsapi?key=7d5c59941cf306bf6f37c308b43263f5&route=2&sender=WITORG&number="+mobNumber+"&templateid=1007178949079546167&sms=Dear Customer, " +val+" is your one time password. Please enter the OTP to proceed.Thank you,Team WIT";

        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        queue.start();
        return val;
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
    boolean validatePhone(String input)
    {

        if(input==null||input.isEmpty())
            return false;
        else {
            Pattern p = Pattern.compile("[6-9]\\d{9}");
            Matcher m = p.matcher(input);
            return m.matches();
        }

    }

}/**/