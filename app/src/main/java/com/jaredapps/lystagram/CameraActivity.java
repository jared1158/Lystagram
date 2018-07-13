package com.jaredapps.lystagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaredapps.lystagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;
    ParseAdapter parseAdapter;
    ArrayList<Post> posts;
    RecyclerView rvParse;


    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
    //add logout button to action bar
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnLogout) {
            ParseUser.logOut();
            final Intent logout  = new Intent(CameraActivity.this, LoginActivity.class);
            startActivity(logout);
            finish();
        }
        if (id == R.id.btnBack){
            Intent b = new Intent(CameraActivity.this, FeedActivity.class);
            startActivity(b);
        }
        return super.onOptionsItemSelected(item);
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        posts = new ArrayList<>();

        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();


        final Button submit = findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView newPic = (ImageView) findViewById(R.id.ivPreview);
                Bitmap bm=((BitmapDrawable)newPic.getDrawable()).getBitmap();
                EditText caption = (EditText) findViewById(R.id.etCaption);
                //Save the status in Parse.com
                final Post post = new Post();//Create a new parse class
                post.setDescription(caption.getText().toString());
                try {
                    post.setImage(new ParseFile(saveToFile(getApplicationContext(), bm)));//Creates a new attribute and adds value from newStatus
                } catch (IOException e) {
                    e.printStackTrace();
                }
                post.setUser(ParseUser.getCurrentUser());//Stores username in new parse class
                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {


                Intent x = new Intent(CameraActivity.this, FeedActivity.class);
                x.putExtra("post" , Parcels.wrap(post));
                startActivity(x);
                    }
                });


                /*//notify adapter of new added post
                posts.add(0, post);
                parseAdapter.notifyItemInserted(0);
                rvParse.scrollToPosition(0);
                */
                //send to feed and pull out files

            }
        });


        final Button pic = findViewById(R.id.btnPic);
        pic.setOnClickListener(new View.OnClickListener() {
        public void onClick (View v){
        // Code here executes on main thread after user presses button
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            //File photoFile = null;

                photoFile = getPhotoFileUri(photoFileName);

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(CameraActivity.this,
                        "com.codepath.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        }
     }});









        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {

                    if (e == null) {
                        for (int i = 0; i < objects.size(); ++i) {
                            Log.d("HomeActivity", "Post[" + i + "] = "
                                    + objects.get(i).getDescription()
                                    + "\nusername = "
                                    + objects.get(i).getUser().getUsername());
                        }
                    } else {
                        e.printStackTrace();
                    }
                }

        });
    }




    public static File saveToFile(Context context, Bitmap bitmap) throws IOException {
        //create a file to write bitmap data
        File f = new File(context.getCacheDir(), System.currentTimeMillis() + ".jpg");
        f.createNewFile();

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                //declare inputted text

                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
                ivPreview.setImageBitmap(takenImage);

            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }







}
