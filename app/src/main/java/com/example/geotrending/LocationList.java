package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import twitter4j.Location;
import twitter4j.ResponseList;

public class LocationList extends AppCompatActivity {

    private ArrayList<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        Intent intent = getIntent();
        locations = getIntent().getStringArrayListExtra("locations");

    }
}
