package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This allows someone to search for different users.
 */
public class Search extends AppCompatActivity {
    private Button homeButton;
    private Button searchButton;
    private String userID;
    private TextView[] personText;
    private Button[] personButton;

    private Button nextButton;
    private Button previousButton;

    int currPage;
    int maxPage;

    private ArrayList<String> resultsList;
    private String[] dataset;

    private RequestQueue mQueue;
    int loopCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Perform basic initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        userID = getIntent().getStringExtra("userID");
        resultsList = new ArrayList<String>();
        dataset = new String[5];
        personButton = new Button[5];
        personText = new TextView[5];
        currPage = 0;
        maxPage = 0;

        personText[0] = (TextView)findViewById(R.id.person1Text);
        personText[1] = (TextView)findViewById(R.id.person2Text);
        personText[2] = (TextView)findViewById(R.id.person3Text);
        personText[3] = (TextView)findViewById(R.id.person4Text);
        personText[4] = (TextView)findViewById(R.id.person5Text);

        personButton[0] = (Button)findViewById(R.id.visitPerson1);
        personButton[1] = (Button)findViewById(R.id.visitPerson2);
        personButton[2] = (Button)findViewById(R.id.visitPerson3);
        personButton[3] = (Button)findViewById(R.id.visitPerson4);
        personButton[4] = (Button)findViewById(R.id.visitPerson5);

        nextButton = (Button)findViewById(R.id.nextPageButton);
        previousButton = (Button)findViewById(R.id.prevPageButton);

        homeButton = (Button)findViewById(R.id.homeFromSearchButton);
        searchButton = (Button)findViewById(R.id.searchButton);

        // Initialize the layout with all of the users
        fillAllUsers();

        // Set up the search button listener to search when requested
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClick();
            }
        });

        // Set up the home button listener to go home when pressed
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        // Set up the home button listener to go home when pressed
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevPage();
            }
        });

        // Set up the home button listener to go home when pressed
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        personButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitPerson(0);
            }
        });

        personButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitPerson(1);
            }
        });

        personButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitPerson(2);
            }
        });

        personButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitPerson(3);
            }
        });

        personButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitPerson(4);
            }
        });
    }

    /**
     * This function, if the given user is an admin, gets all of the users.
     */
    private void fillAllUsers() {
        // Set up the URL
        String url = Const.PREREQ + userID + Const.URL_ALL_USERS;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url ,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        personText[0].setText(response.toString());

                        try {
                            // Work through the entire array of responses, adding them to the list
                            JSONArray array = new JSONArray(response);
                            for(int i=0; i<array.length(); i++) {
                                JSONObject item = array.getJSONObject(i);
                                resultsList.add(item.getString("name"));
                            }
                            // Add 5 extra blank results, to ensure that if nothing is received, there
                            // isn't an error in filling out the TextView forms.
                            for(int i=array.length(); i<array.length()+5; i++)
                                resultsList.add("");

                            // Set the current number of pages and set the visibility/data of forms
                            maxPage = (resultsList.size()/5);
                            if(maxPage>0) { maxPage--; }
                            currPage = 0;
                            setData();
                            previousButton.setVisibility(View.INVISIBLE);
                            if(maxPage == 0)
                                nextButton.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = error.toString();
                errorString = errorString.replaceAll("com.android.volley.ParseError: org.json.JSONException: Value ","");
                errorString = errorString.replaceAll(" of type org.json.JSONArray cannot be converted to JSONObject","");
                personText[0].setText(errorString);

                try {
                    // Work through the entire array of responses, adding them to the list
                    JSONArray array = new JSONArray(errorString);
                    resultsList.clear();
                    for(int i=0; i<array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        resultsList.add(item.getString("username"));
                    }
                    // Add 5 extra blank results, to ensure that if nothing is received, there
                    // isn't an error in filling out the TextView forms.
                    for(int i=array.length(); i<array.length()+5; i++)
                        resultsList.add("");

                    // Set the current number of pages and set the visibility/data of forms
                    maxPage = (resultsList.size()/5);
                    if(maxPage>0) { maxPage--; }
                    currPage = 0;
                    setData();
                    previousButton.setVisibility(View.INVISIBLE);
                    if(maxPage == 0)
                        nextButton.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {}
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
     * This function searches for the sent text.
     */
    private void searchClick() {
        // Set up the URL
        String searchString = ((EditText)findViewById(R.id.searchString)).getText().toString();
        String url = Const.PREREQ + userID + Const.URL_SEARCH + searchString;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url ,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        personText[0].setText(response.toString());
                        try {
                            // Work through the entire array of responses, adding them to the list
                            JSONArray array = new JSONArray(response);
                            resultsList.clear();
                            for(int i=0; i<array.length(); i++) {
                                JSONObject item = array.getJSONObject(i);
                                resultsList.add(item.getString("name"));
                            }
                            // Add 5 extra blank results, to ensure that if nothing is received, there
                            // isn't an error in filling out the TextView forms.
                            for(int i=array.length(); i<array.length()+5; i++)
                                resultsList.add("");

                            // Set the current number of pages and set the visibility/data of forms
                            maxPage = (resultsList.size()/5);
                            if(maxPage>0) { maxPage--; }
                            currPage = 0;
                            setData();
                            previousButton.setVisibility(View.INVISIBLE);
                            if(maxPage == 0)
                                nextButton.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = error.toString();
                errorString = errorString.replaceAll("com.android.volley.ParseError: org.json.JSONException: Value ","");
                errorString = errorString.replaceAll(" of type org.json.JSONArray cannot be converted to JSONObject","");
                personText[0].setText(errorString);

                try {
                    // Work through the entire array of responses, adding them to the list
                    JSONArray array = new JSONArray(errorString);
                    resultsList.clear();
                    String nameList = "";
                    for(int i=0; i<array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        resultsList.add(item.getString("username"));
                    }
                    // Add 5 extra blank results, to ensure that if nothing is received, there
                    // isn't an error in filling out the TextView forms.
                    for(int i=array.length(); i<array.length()+5; i++)
                        resultsList.add("");

                    // Set the current number of pages and set the visibility/data of forms
                    maxPage = (resultsList.size()/5);
                    if(maxPage>0) { maxPage--; }
                    currPage = 0;
                    setData();
                    previousButton.setVisibility(View.INVISIBLE);
                    if(maxPage == 0)
                        nextButton.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {}
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
     * This exits and goes back to the homepage.
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This takes the application and moves to the next page.
     */
    private void nextPage() {
        if(currPage < maxPage) {
            previousButton.setVisibility(View.VISIBLE);
            currPage++;
            setData();
            if(currPage == maxPage) {
                nextButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * This takes the application and moves to the previous page of search results.
     */
    private void prevPage() {
        if(currPage > 0) {
            nextButton.setVisibility(View.VISIBLE);
            currPage--;
            setData();
            if(currPage == 0) {
                previousButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * This sets the data for each of the text fields, also setting visibility of the buttons.
     */
    private void setData() {
        int pageRef = currPage * 5;
        for(int i=0; i<5; i++) {
            personText[i].setText(resultsList.get(pageRef+i));
            personButton[i].setVisibility(View.VISIBLE);
            if(resultsList.get(pageRef+i).equals(""))
                personButton[i].setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This figures out which button was clicked, takes that data and visits that person's closet.
     * @param person button pressed representing person on list
     */
    private void visitPerson(int person) {
        String otherID = personText[person].getText().toString();
        Intent intent = new Intent(this, Closet.class);
        intent.putExtra("userID", userID);
        intent.putExtra("otherID", otherID);
        startActivity(intent);
    }
}