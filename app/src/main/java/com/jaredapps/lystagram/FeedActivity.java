package com.jaredapps.lystagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.jaredapps.lystagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ParseAdapter parseAdapter;
    ArrayList<Post> posts;
    RecyclerView rvParse;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnLogout) {
            ParseUser.logOut();
            final Intent logout  = new Intent(FeedActivity.this, LoginActivity.class);
            startActivity(logout);
            finish();
        }
        if (id == R.id.btnNewPic){
            Intent b = new Intent(FeedActivity.this, CameraActivity.class);
            startActivity(b);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Intent x = getIntent();
        //find the recyclerView
        rvParse = findViewById(R.id.rvParse);
        //init the arraylist (datasource)
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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



        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadTop();

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        loadTop();

        // Toast the name to display temporarily on screen
        //Toast.makeText(this, "Item Posted Successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadTop() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser().orderByDescending("createdAt");

        posts.clear();
        parseAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
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
