package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This allows someone to view someone's profile.
 */
public class ProfileView extends AppCompatActivity {
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        userID = getIntent().getStringExtra("userID");


    }

    /**
     * This goes to the home page without saving
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
