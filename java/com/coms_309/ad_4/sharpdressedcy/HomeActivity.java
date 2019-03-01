package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * This is the homepage for all of the app users
 */
public class HomeActivity extends AppCompatActivity {

    // These are the private buttons to redirect to other activities
    private Button addClothing;
    private Button accessWeather;
    private Button clothing;
    private String userID;
    private Button commentPage;
    private Button search;
    private Button userPage;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userID = getIntent().getStringExtra("userID");

        // This assigns the buttons from the view to this class
        addClothing = findViewById(R.id.addClothingButton);
        accessWeather = findViewById(R.id.weatherButton);
        clothing = findViewById(R.id.sevenDayClothing);
        userPage = findViewById(R.id.viewUserPage);
        search = findViewById(R.id.homeToSearchButton);
        commentPage = findViewById(R.id.viewClosetFromUserEdit);
        logout = findViewById(R.id.logout);

        addClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddClothing();
            }
        });


        // This adds a listener to the "Add Clothing" button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        // This adds a listener to the "Add Clothing" button
        commentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToComments();
            }
        });

        // This adds a listened to the "Go To Weather" button
        accessWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoWeather();
            }
        });

        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClothing();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserEdit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    /**
     * This runs when the "Add Clothing" button is clicked, and redirects the user to add clothing
     * to their personal closet.
     */
    private void gotoAddClothing() {
        Intent intent = new Intent(this, AddClothingActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This runs when the "Add Clothing" button is clicked, and redirects the user to add clothing
     * to their personal closet.
     */
    private void goToUserEdit() {
        Intent intent = new Intent(this, UserPageEdit.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This runs when the weather button is clicked, and redirects the user to the Weather Activity.
     */
    private void gotoWeather() {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This runs when the clothing button is clicked, and goes to view all of a user's clothing items.
     */
    private void goToClothing() {
        Intent intent = new Intent(this, Closet.class);
        intent.putExtra("userID", userID);
        intent.putExtra("otherID", userID);
        startActivity(intent);
    }

    /**
     * This goes to the search page when clicked.
     */
    private void search() {
        Intent intent = new Intent(this, Search.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This goes to a user's homepage when clicked.
     */
    private void viewUserEdit() {
        Intent intent = new Intent(this, UserPageEdit.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This goes to the user page.
     */
    private void goToComments() {
        Intent intent = new Intent(this, ClosetCommentThread.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This logs the current user out
     */
    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
