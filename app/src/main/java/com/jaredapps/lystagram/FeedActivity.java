package com.jaredapps.lystagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jaredapps.lystagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ParseAdapter parseAdapter;
    ArrayList<Post> posts;
    RecyclerView rvParse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Intent x = getIntent();
        //find the recyclerView
        rvParse = findViewById(R.id.rvParse);
        //init the arraylist (datasource)
        posts = new ArrayList<>();
        //construct the adapter from this datasource
        parseAdapter = new ParseAdapter(posts);
        //recyclerView setup( layout manager , use adapter)
        rvParse.setLayoutManager(new LinearLayoutManager(this));
        //set the adapter
        rvParse.setAdapter(parseAdapter);

        Post post = Parcels.unwrap(x.getParcelableExtra("post"));
        //Post post = Parcels.unwrap(x.getParcelableExtra("post"));
        //int code = data.getExtras().getInt("code", 0);

        //notify adapter that tweet has been added
        posts.add(0, post);
        parseAdapter.notifyItemInserted(0);
        rvParse.scrollToPosition(0);

        //scroll back to top of timeline
        //rvTweets.scrollToPosition(0);

        loadTop();

        // Toast the name to display temporarily on screen
        Toast.makeText(this, "Item Posted Successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadTop() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser().orderByDescending("createdAt");

        posts.clear();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    for (int i = 0; i < objects.size(); i++ ){
                        Post post = objects.get(i);
                        posts.add(post);
                        parseAdapter.notifyItemInserted(0);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

}
