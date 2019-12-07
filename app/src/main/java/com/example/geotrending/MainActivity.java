package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

    private String messageTest;

    private ResponseList<Location> locations;

    private List<String> locationsString;

    public static final String EXTRA_MESSAGE = "com.example.GeoTrending.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationThread locationThread = new LocationThread();
        locationThread.start();

        ExampleThread thread = new ExampleThread();
        thread.start();
    }

    public void searchLocation(View view) throws TwitterException {
        Intent intent = new Intent(this, TopTenTrending.class);
        EditText editText = (EditText) findViewById(R.id.locationSearch);
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

    public void availableLocations(View view) {
        Intent intent = new Intent(this, LocationList.class);
        intent.putStringArrayListExtra("locations", (ArrayList<String>) locationsString);
        startActivity(intent);
    }

    public void test(View view) {
        TextView y = findViewById(R.id.textView3);
        y.setText(messageTest);

        TextView z = findViewById(R.id.textView4);
        String a = locations.get(0).getName() + " (woeid:" + locations.get(0).getWoeid() + ")";
        z.setText(a);
    }

    //Initial locations available
    class LocationThread extends Thread {
        @Override
        public void run() {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("9alVPGLKVIp0sEtoa1b3tZEnn")
                    .setOAuthConsumerSecret("0y8YVdeU09laY402BPJJNqXeWxOmdosJoZn5BPDZgH1Yft2SWP")
                    .setOAuthAccessToken("1199152818038308864-egUurjskATwRwHzf5CcppBuAtQGjPQ")
                    .setOAuthAccessTokenSecret("JiTLQX3RcVEsZ4JBYeorKYJfZQNta5B0vRvq5EGoShUcA");

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            try {
                locations = twitter.getAvailableTrends();
                locationsString = new ArrayList<String>();
                for (Location location : locations) {
                    locationsString.add((location.getName() + " (woeid:" + location.getWoeid() + ")"));
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }

    class ExampleThread extends Thread {

        @Override
        public void run() {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("9alVPGLKVIp0sEtoa1b3tZEnn")
                    .setOAuthConsumerSecret("0y8YVdeU09laY402BPJJNqXeWxOmdosJoZn5BPDZgH1Yft2SWP")
                    .setOAuthAccessToken("1199152818038308864-egUurjskATwRwHzf5CcppBuAtQGjPQ")
                    .setOAuthAccessTokenSecret("JiTLQX3RcVEsZ4JBYeorKYJfZQNta5B0vRvq5EGoShUcA");

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            List<Status> statuses = null;
            try {
                statuses = twitter.getHomeTimeline();
            } catch (TwitterException e) {
                Context context = getApplicationContext();
                CharSequence text = "error";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            for (Status status : statuses) {
                messageTest =  status.getUser().getName() + ":" + status.getText();
            }
        }
    }
}
