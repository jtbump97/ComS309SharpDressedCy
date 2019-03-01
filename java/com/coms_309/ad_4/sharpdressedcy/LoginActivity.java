package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    private RequestQueue mQueue;

    String tag_json_obg = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        Button addUserButton = (Button) findViewById(R.id.addUserButton);

        mQueue = Volley.newRequestQueue(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.username).setBackgroundColor(Color.rgb(0, 0, 0));
                findViewById(R.id.password).setBackgroundColor(Color.rgb(0, 0, 0));
                loginDetails();
            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    /**
     * When this button is clicked, it will exit without saving and go to the home page
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", ((EditText) findViewById(R.id.username)).getText().toString());
        startActivity(intent);
    }

    /**
     * When the add user button is clicked, it goes to add the user
     */
    private void addUser() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    public void loginDetails() {
        RequestQueue queue = Volley.newRequestQueue(this);


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Const.URL_LOGIN+((EditText)findViewById(R.id.username)).getText().toString()
                        +"&"+((EditText)findViewById(R.id.password)).getText().toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    /**
                     * @throws JSONException
                     */
                    public void onResponse(JSONObject response) {
                        EditText username = findViewById(R.id.username);
                        EditText password = findViewById(R.id.password);
                            if (response != null) {
                                username.setBackgroundColor(Color.rgb(0,255,0));
                                password.setBackgroundColor(Color.rgb(0,255,0));
                                goHome();
                            }
                            else {
                                ((EditText)findViewById(R.id.password)).setText("");
                                username.setBackgroundColor(Color.rgb(0,255,0));
                                password.setBackgroundColor(Color.rgb(255,0,0));
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.username).setBackgroundColor(Color.rgb(255,0,0));
                findViewById(R.id.password).setBackgroundColor(Color.rgb(255,0,0));


            }
        });
        mQueue.add(jsonObjectRequest);
    }
}
