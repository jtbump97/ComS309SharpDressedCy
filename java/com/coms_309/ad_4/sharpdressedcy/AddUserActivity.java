package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * A login screen that adds the new user with their data to the database, logging them in.
 */
public class AddUserActivity extends AppCompatActivity {
    Button addUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        addUser = (Button) findViewById(R.id.addUserConfirmButton);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    /**
     * This function exits without saving and goes to the home page
     */
    private void goHome(String username) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", username);
        startActivity(intent);
    }

    /**
     * This function exits without saving and goes to the home page
     */
    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * This adds the selected user to the database
     */
    public void addUser() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String type = "user";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Const.URL_ADD_USER+username+"&"+password+"&"+type, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    /**
                     * @throws JSONException
                     */
                    public void onResponse(JSONObject response) {
                        if(response != null) {
                            goHome("username");
                        }
                        else {
                            TextView errorMessage = (TextView)findViewById(R.id.errorMessage);
                            errorMessage.setVisibility(View.VISIBLE);
                            errorMessage.setText("ERROR: USER ALREADY EXISTS");
                            try {
                                wait(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            goLogin();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.password).setBackgroundColor(Color.rgb(255,0,0));

            }
        });
        mQueue.add(jsonObjectRequest);
    }
}
