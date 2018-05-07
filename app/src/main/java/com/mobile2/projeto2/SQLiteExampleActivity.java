package com.mobile2.projeto2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SQLiteExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create sample data */
        User sampleUser = new User();
        sampleUser.userName = "Steph";
        sampleUser.profilePictureUrl = "https://i.imgur.com/tGbaZCY.jpg";

        Post samplePost = new Post();
        samplePost.user = sampleUser;
        samplePost.text = "Won won!";

        /* Get singleton instance of database */
        PostsDatabaseHelper databaseHelper = PostsDatabaseHelper.getInstance(this);

        /* Add sample post to the database */
        databaseHelper.addPost(samplePost);

        /* Get all posts from database */
        List<Post> posts = databaseHelper.getAllPosts();
        for (Post post : posts) {
            // DO SOMETHING
        }
    }

}
