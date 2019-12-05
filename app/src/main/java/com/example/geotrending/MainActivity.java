package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.GeoTrending.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Twitter O-Auth
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("9alVPGLKVIp0sEtoa1b3tZEnn")
                .setOAuthConsumerSecret("0y8YVdeU09laY402BPJJNqXeWxOmdosJoZn5BPDZgH1Yft2SWP")
                .setOAuthAccessToken("1199152818038308864-egUurjskATwRwHzf5CcppBuAtQGjPQ")
                .setOAuthAccessTokenSecret("JiTLQX3RcVEsZ4JBYeorKYJfZQNta5B0vRvq5EGoShUcA");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query("source:twitter4j yusukey");
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (Status status : result.getTweets()) {
            Log.v("nani","@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }

    public void searchLocation(View view) {
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
}
