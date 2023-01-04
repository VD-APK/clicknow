package com.example.clicknow;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.clicknow.databinding.ActivityScrollingDashboardBinding;

public class ScrollingDashboard extends AppCompatActivity {

    private ActivityScrollingDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}