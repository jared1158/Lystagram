package com.jaredapps.lystagram;

import android.app.Application;

import com.jaredapps.lystagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by jared1158 on 7/9/18.
 */

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("dabest")
                .clientKey("Anthony1158")
                .server("http://lystagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
