package com.jaredapps.lystagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by jared1158 on 7/9/18.
 */

@ParseClassName("Post")
public class Post extends ParseObject{
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_IMAGE = "Image";
    private static final String KEY_USER = "user";
    private static final String CREATED_AT = "createdAt";


    public String getDescription(){
        return getString(KEY_DESCRIPTION);

    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }



    public static class Query extends ParseQuery<Post> {
        public Query(){
            super(Post.class);
        }
        public Query getTop(){
            setLimit(20);
            return this;
        }

        public Query withUser(){
            include ("user");
            return this;
        }
    }

}
