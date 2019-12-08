package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TopTenTrending extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_trending);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.locationTitle);
        textView.setText(message);

        ViewGroup list = findViewById(R.id.list);
        list.removeAllViews();
        for(int i = 0; i < 10; i++) {
            View topTenChunk = getLayoutInflater().inflate(R.layout.chunk_top_ten, list, false);\
            TextView number = topTenChunk.findViewById(R.id.trendNumber);
            TextView value = topTenChunk.findViewById(R.id.trendValue);


        }
    }
}
