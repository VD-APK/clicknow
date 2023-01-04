package com.example.clicknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clicknow.com.example.wit.bean.LoginBean;
import com.example.clicknow.com.example.wit.bean.UserBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText  mobileNumber,password, pinCod, name;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String passCode="";
    String userName="";
    int pinCode=0;
    long mobileNo=0;

    private EditText otp1, otp2, otp3, otp4;
    private TextView mobileNumberview;
    private TextView resendButton;
    private AppCompatButton verifyButton;


    private int resendTime=60;
    private int selectedOtpPosition=0;
    private boolean resetEnabled=false;
    private String mobileNumberText;


    Button signUp ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseDatabase = FirebaseDatabase.getInstance();


        mobileNumber = findViewById(R.id.userMobileNumber);
        signUp = (Button) findViewById(R.id.userRgister);
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validatePhone(s.toString()))
                {
                    signUp.setEnabled(true);
                    mobileNumber.setError(null);

                }
                else{
                    signUp.setEnabled(false);
                    mobileNumber.setError("Invalid Mobile No");

                }

            }
        });

        password = findViewById(R.id.userPassword);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validatePassCode(s.toString()))
                {
                    signUp.setEnabled(true);
                    password.setError(null);

                }
                else{
                    signUp.setEnabled(false);
                    password.setError("Invalid Password");

                }


            }
        });
        pinCod = findViewById(R.id.userPinCode);

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
                    signUp.setEnabled(true);
                    pinCod.setError(null);

                }
                else{
                    signUp.setEnabled(false);
                    pinCod.setError("Invalid pinCode");

                }

            }
        });
        name = findViewById(R.id.name);


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validateName(name.getText().toString()))
                {
                    signUp.setEnabled(true);
                    name.setError(null);

                }
                else{
                    signUp.setEnabled(false);
                    name.setError("Invalid Name");

                }

            }
        });




        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateInput(mobileNumber.getText().toString(), password.getText().toString() ,name.getText().toString(),
                       pinCod.getText().toString())) {

                    mobileNo = Long.valueOf(mobileNumber.getText().toString());
                    pinCode = Integer.parseInt(pinCod.getText().toString());
                    passCode = password.getText().toString();
                    userName = name.getText().toString();


                    databaseReference = firebaseDatabase.getReference("login");

                    LoginBean loginBean = new LoginBean();
                    loginBean.setMobileNumber(mobileNo);
                    loginBean.setPassCode(passCode);
                    loginBean.setEmp(false);

                    databaseReference.child(Long.toString(mobileNo)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot snapshot = task.getResult();
                                    LoginBean bean = snapshot.getValue(LoginBean.class);
                                    if (bean.getMobileNumber() == loginBean.getMobileNumber()) {
                                        Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(SignupActivity.this, MainActivity.class);
                                        SignupActivity.this.startActivity(myIntent);

                                    }
                                }
                            }
                        }
                    });

                        AlertDialog.Builder mBuilder= new AlertDialog.Builder(SignupActivity.this);
                        View view=getLayoutInflater().inflate(R.layout.otp_dialog,null);
                        mBuilder.setView(view);

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

                        mobileNumberview.setText(mobileNumberText);
                        resendButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(resetEnabled)
                                {
                                    startCountDownTimer();
                                }
                            }
                        });

                        verifyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final  String getOtp=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();

                                if(getOtp.length()==4){

                                    if(getOtp.equals("1234"))
                                    {
                                        databaseReference.child(Long.toString(mobileNo)).setValue(loginBean).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {


                                                    UserBean userBean = new UserBean();

                                                    userBean.setUserName(userName);
                                                    userBean.setMobileNumber(mobileNo);
                                                    userBean.setPassCode(passCode);
                                                    userBean.setPinCode(pinCode);

                                                    databaseReference = firebaseDatabase.getReference("User");

                                                    databaseReference.child(Long.toString(mobileNo)).setValue(userBean).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                Intent myIntent = new Intent(SignupActivity.this, MainActivity.class);
                                                                SignupActivity.this.startActivity(myIntent);

                                                            }
                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Registration Un Successful", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "wrong otp", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }


                        });







                }


            }

        });
    }


    //  private void  exitState(){
//        this.dismiss();
//    }

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

    private boolean validateInput(String mobile, String userName, String passwordString, String pincode) {

        boolean flag;
        if(validatePhone(mobile))
        {
            signUp.setEnabled(true);
            mobileNumber.setError(null);
            flag=true;

        }
        else{
            signUp.setEnabled(false);
            mobileNumber.setError("Invalid Mobile No");
            flag=false;
        }

        if(validatePassCode(passwordString))
        {
            signUp.setEnabled(true);
            password.setError(null);
            flag=true;
        }
        else{
            signUp.setEnabled(false);
            password.setError("Invalid Password");
            flag=false;
        }
        if(validateName(userName))
        {
            signUp.setEnabled(true);
            name.setError(null);
            flag=true;
        }
        else{
            signUp.setEnabled(false);
            name.setError("Invalid Name");
            flag=false;
        }

        if(validatePinCode(pincode))
        {
            signUp.setEnabled(true);
            pinCod.setError(null);
            flag=true;
        }
        else{
            signUp.setEnabled(false);
            pinCod.setError("Invalid pinCode");
            flag=false;
        }

        return flag;
    }
}


