package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coms_309.ad_4.sharpdressedcy.net_utils.Const;
import org.json.JSONObject;

/**
 * An activity to add clothing to the selected user's closet.
 */
public class AddClothingActivity extends AppCompatActivity {

    private Button saveButton;
    private Button homeButton;

    private Spinner clothingType;
    private EditText itemName;

    private String userID;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        userID = getIntent().getStringExtra("userID");

        // This declares the individual buttons
        saveButton = findViewById(R.id.saveButton);
        homeButton = findViewById(R.id.exitButton);

        // This declares the individual spinners and other variables
        clothingType = findViewById(R.id.clothingType);
        itemName = findViewById(R.id.itemNameText);

        // This listens for the "Save and Reset" button to be clicked
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        // This listens for the "Home" button to be clicked
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    /**
     * When this is run, the page will save the current article of clothing to the
     * database and reset all of the fields.
     */
    private void reset() {
        // This gets the current values from each of the fields.
        final String clothingTypeString = clothingType.getSelectedItem().toString();
        final String name = itemName.getText().toString();

        String url = Const.PREREQ + userID + Const.URL_SUBMIT_ARTICLE + userID + "&" + name + "&" + clothingTypeString;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {}
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }
        );
        mQueue.add(jsonObjReq);

        // This resets the values for each field.
        clothingType.setSelection(0);
        itemName.setText("");
    }

    /**
     * This function exits without saving and goes to the home page
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
