package com.example.clicknow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AcMechanic extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acmechanic);
        lv = findViewById(R.id.lv);
        String values[] = { "AC Install and Uninstall", "AC Sevice", "AC Repair and Gas Filling"};
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,values);
        lv.setAdapter(arrayAdapter);
    }
}