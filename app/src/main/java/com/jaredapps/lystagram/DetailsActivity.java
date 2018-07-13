package com.jaredapps.lystagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaredapps.lystagram.model.Post;
import com.parse.ParseException;

import org.parceler.Parcels;

import static com.jaredapps.lystagram.ParseAdapter.context;

public class DetailsActivity extends AppCompatActivity {
    Post post;
    TextView tvHandle;
    TextView tvDes;
    TextView tvAgo;
    ImageView ivExpand;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //resolve objects
        ivExpand = (ImageView) findViewById(R.id.ivExpand);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        tvAgo = (TextView) findViewById(R.id.tvAgo);
        tvDes = (TextView) findViewById(R.id.tvDes);

        //unwrap the intent data
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        //set the textviews
        try {
            tvHandle.setText(post.getUser().fetchIfNeeded().getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvAgo.setText(post.getCreatedAt().toString());
        tvDes.setText(post.getDescription());
        Glide.with(context).load(post.getImage().getUrl()).into(ivExpand);
    }

    public void onBack(MenuItem mi) {
        Intent i = new Intent(DetailsActivity.this, FeedActivity.class);
        startActivity(i);
        finish();

    }

}
