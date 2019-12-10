package com.example.geotrending;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class OAuth {

    public TwitterFactory tf;

    public Twitter twitter;

    public void main() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("---------------------------------------")
                .setOAuthConsumerSecret("---------------------------------------")
                .setOAuthAccessToken("---------------------------------------")
                .setOAuthAccessTokenSecret("---------------------------------------");

        tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public TwitterFactory getTwitterFactory() {
        main();
        return tf;
    }

    public Twitter getTwitter() {
        main();
        return twitter;
    }
}
