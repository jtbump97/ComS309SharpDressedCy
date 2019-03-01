package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coms_309.ad_4.sharpdressedcy.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This activity shows the closet of the selected user
 */
public class Closet extends AppCompatActivity {

    private Button homeButton;
    private String userID;
    private String otherID;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_closet);
        userID = getIntent().getStringExtra("userID");
        otherID = getIntent().getStringExtra("otherID");

        ((TextView)findViewById(R.id.closetNameText)).setText(otherID+"'s Closet");

        Button commentButton = findViewById(R.id.commentAccessButton);
        homeButton = findViewById(R.id.homeFromClosetButton);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessComments();
            }
        });

        mQueue = Volley.newRequestQueue(getApplicationContext());

        getClothing();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    /**
     * This goes to the chat room for the activity.
     */
    private void accessComments() {
        Intent intent = new Intent(this, ClosetCommentThread.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This runs at the beginning of the clothing, and requests all of the clothing for the specific
     * username.
     */
    private void getClothing() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.PREREQ + userID + Const.URL_CLOSET+otherID,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = error.toString();
                errorString = errorString.replaceAll("com.android.volley.ParseError: org.json.JSONException: Value ","");
                errorString = errorString.replaceAll(" of type org.json.JSONArray cannot be converted to JSONObject","");
                try {
                    JSONArray array = new JSONArray(errorString);
                    String listOfItems = "";
                    for(int i=0; i<array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        listOfItems += item.getString("type") + ": " + item.getString("name") + "\n";
                    }
                    ((TextView)findViewById(R.id.closetData)).setText(listOfItems);
                } catch(JSONException e) {((TextView)findViewById(R.id.closetData)).setText(e.toString());}
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        mQueue.add(jsonObjReq);
    }

    /**
     * This activity exits without saving and goes to the home page
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
