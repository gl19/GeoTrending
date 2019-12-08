package com.example.geotrending;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String title = ("About");
        setTitle(title);

        TextView title1 = findViewById(R.id.aboutTitle);
        title1.setText("GeoTrending 2019");

        TextView body = findViewById(R.id.aboutBody);
        body.setText("Project created by Guangxin Liu and Parth Silva for CS125 @ UIUC.");
    }
}
