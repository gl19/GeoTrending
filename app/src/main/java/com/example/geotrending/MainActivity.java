package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

    public static final String EXTRA_MESSAGE = "com.example.GeoTrending.MESSAGE";

    public static final String EXTRA_WOEID = "com.example.GeoTrending.WOEID";

    private String messageTest;

    private ResponseList<Location> locations;

    private List<String> locationsString = new ArrayList<>();

    private List<String> locationsName = new ArrayList<>();

    private List<Integer> locationsWoeid = new ArrayList<>();

    private String[] locationsArrayName;

    private Integer[] locationsArrayWoeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationThread locationThread = new LocationThread();
        locationThread.start();
        try {
            locationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExampleThread thread = new ExampleThread();
        thread.start();

        AutoCompleteTextView autoLocation = findViewById(R.id.locationSearch);
        autoLocation.setAdapter(new ArrayAdapter<>
                (MainActivity.this, android.R.layout.simple_list_item_1, locationsArrayName));

        TextView a = findViewById(R.id.textView4);
        a.setText(locationsArrayWoeid[5].toString());
    }

    public void searchLocation(View view) throws TwitterException {
        Intent intent = new Intent(this, TopTenTrending.class);
        AutoCompleteTextView locationSearch = findViewById(R.id.locationSearch);
        String message = locationSearch.getText().toString();

        if (!(message.equals("") || message == null)) {
            int number = 0;
            for(int i = 0; i < locationsArrayName.length; i++) {
                if(message.equals(locationsArrayName[i])) {
                    number = i;
                    break;
                }
            }
            intent.putExtra(EXTRA_MESSAGE, locationsArrayName[number]);
            intent.putExtra(EXTRA_WOEID, locationsArrayWoeid[number]);
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

                for (Location location : locations) {
                    locationsString.add((location.getName() + " (woeid:" + location.getWoeid() + ")"));
                    locationsName.add(location.getName());
                    locationsWoeid.add(location.getWoeid());
                }
                locationsArrayName = locationsName.toArray(new String[0]);
                locationsArrayWoeid = locationsWoeid.toArray(new Integer[0]);
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
            try {
                List<Status> statuses = twitter.getHomeTimeline();
                for (Status status : statuses) {
                    messageTest =  status.getUser().getName() + ":" + status.getText();
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }
}
