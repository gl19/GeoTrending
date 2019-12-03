package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.GeoTrending.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Twitter twitter = TwitterFactory.getSingleton();

    }

    public void searchLocation(View view) {
        Intent intent = new Intent(this, TopTenTrending.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        // Empty search results in no action and toast is shown to user
        if (!message.equals("")) {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Location search empty";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
