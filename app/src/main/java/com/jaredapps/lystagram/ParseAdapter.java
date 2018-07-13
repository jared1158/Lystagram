package com.jaredapps.lystagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaredapps.lystagram.model.Post;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by jared1158 on 7/11/18.
 */

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {
    static List<Post> mPosts;

    static Context context;

    public ParseAdapter(List<Post> posts) {mPosts = posts;}

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
}

    @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //super.onBindViewHolder(holder,position);
        final Post post = mPosts.get(position);

        //populate the views according to this data
        try {
            holder.tvName.setText(post.getUser().fetchIfNeeded().getUsername());
            holder.tvCap.setText(post.getDescription());
            holder.tvTime.setText(post.getCreatedAt().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //holder.tvCap.setText(tweet.body);
        //holder.tvRelativeDate.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.createdAt));
        //holder.tvHandle.setText(tweet.user.screenName);

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPost);
    }


    //bind the values based on the position of the element

    /*
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Post post = mPosts.get(position);

        //populate the views according to this data
        try {
            holder.tvName.setText(post.getUser().fetchIfNeeded().getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //holder.tvCap.setText(tweet.body);
        //holder.tvRelativeDate.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.createdAt));
        //holder.tvHandle.setText(tweet.user.screenName);

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPost);
    }*/

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivPost;
        public TextView tvName;
        public TextView tvCap;
        public TextView tvTime;
        public TextView tvHandle;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform findViewById lookups

            ivPost = (ImageView) itemView.findViewById(R.id.ivPost);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCap = (TextView) itemView.findViewById(R.id.tvCap);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            //tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Post post = mPosts.get(position);
                // We can access the data within the views
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));

                //show activity
                context.startActivity(i);

            }


    }



}}
