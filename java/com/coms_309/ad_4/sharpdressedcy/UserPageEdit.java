package com.coms_309.ad_4.sharpdressedcy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coms_309.ad_4.sharpdressedcy.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This allows a user to view their profile and edit who can see it.
 */
public class UserPageEdit extends AppCompatActivity {
    private String userID;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_user_page_edit);
        userID = getIntent().getStringExtra("userID");
        Button homeButton = (Button)findViewById(R.id.homeFromUserButton);
        ((TextView)findViewById(R.id.usernameTextbox)).setText(userID);
        Button addButton = (Button)findViewById(R.id.addPermissionButton);
        Button subButton = (Button)findViewById(R.id.removePermissionButton);
        Button closetButton = (Button)findViewById(R.id.viewClosetFromUserEdit);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPermission();
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subPermission();
            }
        });

        closetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCloset();
            }
        });
    }

    /**
     * This takes the user back to the homepage.
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This adds the given user to view the application user's page.
     */
    private void addPermission() {
        EditText otherUserBox = (EditText)findViewById(R.id.usernamePermissions);
        String otherUser = otherUserBox.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Const.PREREQ + userID + Const.URL_ADD_PERMISSION + otherUser, null,
                new Response.Listener<JSONObject>() {
            @Override
            /**
             * @throws JSONException
             */
            public void onResponse(JSONObject response) {}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        mQueue.add(jsonObjectRequest);

        otherUserBox.setText("Enter Username");
    }

    /**
     * This removes viewing permission of the given user.
     */
    private void subPermission() {
        EditText otherUserBox = (EditText)findViewById(R.id.usernamePermissions);
        String otherUser = otherUserBox.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Const.PREREQ + userID + Const.URL_SUB_PERMISSION + otherUser, null,
                new Response.Listener<JSONObject>() {
            @Override
            /**
             * @throws JSONException
             */
            public void onResponse(JSONObject response) {}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        mQueue.add(jsonObjectRequest);

        otherUserBox.setText("Enter Username");
    }

    /**
     * This goes to the own user's closet.
     */
    private void goToCloset() {
        Intent intent = new Intent(this, Closet.class);
        intent.putExtra("userID", userID);
        intent.putExtra("otherID", userID);
        startActivity(intent);
    }
}
