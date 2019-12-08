package com.example.geotrending;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TopTenTrending extends AppCompatActivity {

    private Trend[] trendArray;
    
    private Button browse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_trending);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Integer woeid = intent.getIntExtra(MainActivity.EXTRA_WOEID, 0);

        // Capture the layout's TextView and set the string as its text
        String title = ("Trending @" + message);
        setTitle(title);

        TopTenThread thread = new TopTenThread(woeid);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateTopTen();

    }


    public void updateTopTen() {
        ViewGroup list = findViewById(R.id.list);
        list.removeAllViews();

        for (int i = 0; i < 10; i++) {
            View topTenChunk = getLayoutInflater().inflate(R.layout.chunk_top_ten, list, false);
            TextView number = topTenChunk.findViewById(R.id.trendNumber);
            final int num = i + 1;
            number.setText("Trending #" + num);

            TextView hype = topTenChunk.findViewById(R.id.trendHype);
            int volume = trendArray[i].getTweetVolume();
            if (volume == -1) {
                hype.setText("Volume: Not Available");
            } else {
                hype.setText("Volume: " + volume);
            }


            TextView value = topTenChunk.findViewById(R.id.trendValue);
            value.setText(trendArray[i].getName());
            Button browse = topTenChunk.findViewById(R.id.browsebut);

            browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/search?q=" + trendArray[num - 1].getName() ));
                    startActivity(browserIntent);
                }
            });

            list.addView(topTenChunk);
        }
    }


    class TopTenThread extends Thread {

        private int woeid;

        public TopTenThread(int woeid) {
            this.woeid = woeid;
        }

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
                Trends trends = twitter.getPlaceTrends(woeid);
                trendArray = trends.getTrends();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }
}
