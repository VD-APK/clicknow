package com.example.clicknow.com.example.wit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.clicknow.R;

public class OtpDialog extends Dialog
    {

        private EditText otp1, otp2, otp3, otp4;
        private TextView mobileNumberview;
        private TextView resendButton;
        private AppCompatButton verifyButton;
        public Activity activity;

        private int resendTime=60;
        private int selectedOtpPosition=0;
        private boolean resetEnabled=false;
        private String mobileNumberText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));

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
             exitState();
                    }

                }
            }
        });



    }

    private void  exitState(){
            this.dismiss();
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

            InputMethodManager inputMethodManager= (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                resendButton.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));




            }
        }.start();
    }

        @Override
        public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_DEL)
        {
            if(selectedOtpPosition==3){
                selectedOtpPosition=2;
                showKeyboard(otp3);
            }
            else  if(selectedOtpPosition==2){
                selectedOtpPosition=1;
                showKeyboard(otp2);
            }
            else  if(selectedOtpPosition==1){
                selectedOtpPosition=0;
                showKeyboard(otp1);
            }

            verifyButton.setBackgroundResource(R.drawable.round_back_brown);
            return true;

        }
        else {
            return super.onKeyUp(keyCode, event);
        }
    }

    public OtpDialog(@NonNull Context context,Activity activity,String mobileNumber) {
        super(context);
        this.activity=activity;
        this.mobileNumberText=mobileNumber;

    }


}
