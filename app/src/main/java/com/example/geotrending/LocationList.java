package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationList extends AppCompatActivity {

    private ArrayList<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        Intent intent = getIntent();
        locations = getIntent().getStringArrayListExtra("locations");

        String joined = TextUtils.join("\n", locations);
        TextView textView = findViewById(R.id.locations);
        textView.setText(joined);
    }
}
