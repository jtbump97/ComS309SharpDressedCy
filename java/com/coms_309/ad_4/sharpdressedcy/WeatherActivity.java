package com.coms_309.ad_4.sharpdressedcy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

/**
 * This displays the user's 7-day weather forecast with their clothing for each day.
 */
public class WeatherActivity extends Activity {

    // This sets each button to a class wide scope
    Button zeroDayButton;
    Button oneDayButton;
    Button twoDayButton;
    Button threeDayButton;
    Button fourDayButton;
    Button fiveDayButton;
    Button sixDayButton;
    Button homebutton;

    //These are the arrays storing the weather information

    String information = "";
    String Place;
    String Condition[] = new String[7];
    String Icon[] = new String[7];
    String High[] = new String[7];
    String Low[] = new String[7];
    String dayText[] = {"Today", "Tomorrow", "Two Days", "Three Days", "Four Days", "Five Days", "Six Days"};

    String userID;

    //This allows the changing of the text views based on weather
    static TextView locationTextView;
    static TextView conditionTextView;
    static ImageView iconImageView;
    static TextView highTextView;
    static TextView lowTextView;
    static TextView dayTextView;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mQueue = Volley.newRequestQueue(getApplicationContext());

        locationTextView = findViewById(R.id.location);
        conditionTextView = findViewById(R.id.condition);
        iconImageView = findViewById(R.id.icon);
        highTextView = findViewById(R.id.high);
        lowTextView = findViewById(R.id.low);
        dayTextView = findViewById(R.id.day);

        userID = getIntent().getStringExtra("userID");

        DownloadWeather download = new DownloadWeather(this);

        String zipCode = "52722";
        //By default for now we will use Ames, Iowa as the location
        download.execute("http://api.apixu.com/v1/forecast.json?key=f2d843d98889464e9d044020180710&q=" + zipCode + "&days=7");

        // This initializes each button

//        getDayWeather(0);
        zeroDayButton = findViewById(R.id.zeroDayButton);
        oneDayButton = findViewById(R.id.oneDayButton);
        twoDayButton = findViewById(R.id.twoDayButton);
        threeDayButton = findViewById(R.id.threeDayButton);
        fourDayButton = findViewById(R.id.fourDayButton);
        fiveDayButton = findViewById(R.id.fiveDayButton);
        sixDayButton = findViewById(R.id.sixDayButton);
        homebutton = findViewById(R.id.homeButton);

        //These add listeners to each of the buttons
        zeroDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(0);
            }
        });

        oneDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(1);
            }
        });

        twoDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(2);
            }
        });

        threeDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(3);
            }
        });

        fourDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(4);
            }
        });

        fiveDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(5);
            }
        });

        sixDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDayWeather(6);
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    /**
     * This sets the list with the information for each day
     *
     * @param information JSON information from API
     */
    public void setList(String information) {
        try {

            //Creates object of JSON
            JSONObject jsonObject = new JSONObject(information);

            JSONObject place = new JSONObject(jsonObject.getString("location"));
            Place = place.getString("name") + ", " + place.get("region");
            locationTextView.setText(Place);

            jsonObject = new JSONObject(jsonObject.getString("forecast"));
            JSONArray jsonArray = new JSONArray(jsonObject.getString("forecastday"));

            for (int i = 0; i < 7; i++) {
                JSONObject weatherCond = jsonArray.getJSONObject(i);
                weatherCond = new JSONObject(weatherCond.getString("day"));

                //condition
                JSONObject condition = new JSONObject(weatherCond.getString("condition"));
                Condition[i] = condition.getString("text");

                //Icon
                Icon[i] = condition.getString("icon");
                Icon[i] = Icon[i].replaceAll("//cdn.apixu.com/weather/64x64/day/", "");
                Icon[i] = Icon[i].replaceAll(".png", "");

                //High
                High[i] = weatherCond.getString("maxtemp_f");

                //Low
                Low[i] = weatherCond.getString("mintemp_f");
            }
            highTextView.setText(High[0]);
            lowTextView.setText(Low[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This gets the weather for a specified day
     *
     * @param day Number of day to grab weather, 0-6
     */
    private void getDayWeather(int day) {
        getRecommendedClothes(day);
        conditionTextView.setText(getCondition(day));
        highTextView.setText(High[day]);
        lowTextView.setText(Low[day]);
        setIcon(day);
        dayTextView.setText(dayText[day]);
    }

    /**
     * This gets the current location
     *
     * @return String location
     */
    public String getPlace() {
        return Place;
    }

    /**
     * This gets the condition of the current day
     *
     * @param day number of day, 0-6
     * @return String condition of the day
     */
    public String getCondition(int day) {
        return Condition[day];
    }

    /**
     * This sets the weather icon of the day
     *
     * @param day number of day, 0-6
     */
    public void setIcon(int day) {

        int resId = getResources().getIdentifier("p" + Icon[day], "drawable", getPackageName());

        iconImageView.setImageResource(resId);
    }

    /**
     * This goes home from this activity
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    /**
     * This gets the recommended clothing for the specified day
     *
     * @param day number of day, 0-6
     */
    private void getRecommendedClothes(int day) {
        int temp = (int) (Double.parseDouble(High[day]));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.PREREQ + userID + Const.URL_OUTFIT + temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject shirt = array.getJSONObject(0);
                            ((TextView) findViewById(R.id.upperTextType)).setText(shirt.getString("type"));
                            ((TextView) findViewById(R.id.upperTextName)).setText(shirt.getString("name"));
                            JSONObject pants = array.getJSONObject(1);
                            ((TextView) findViewById(R.id.lowerTextType)).setText(pants.getString("type"));
                            ((TextView) findViewById(R.id.lowerTextName)).setText(pants.getString("name"));
                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String errorString = error.toString();
                if (errorString.contains("ParseError")) {
                    errorString = errorString.replaceAll("com.android.volley.ParseError: org.json.JSONException: Value ", "");
                    errorString = errorString.replaceAll(" of type org.json.JSONArray cannot be converted to JSONObject", "");
                    try {
                        JSONArray array = new JSONArray(errorString);

                        JSONObject shirt = array.getJSONObject(0);
                        if (shirt.getString("type").equals("jacket"))
                            ((TextView) findViewById(R.id.upperTextType)).setText("Jacket:");
                        else
                            ((TextView) findViewById(R.id.upperTextType)).setText("Shirt:");
                        ((TextView) findViewById(R.id.upperTextName)).setText(shirt.getString("name"));

                        JSONObject pants = array.getJSONObject(1);
                        if (pants.getString("type").equals("pants"))
                            ((TextView) findViewById(R.id.lowerTextType)).setText("Pants:");
                        else
                            ((TextView) findViewById(R.id.lowerTextType)).setText("Shorts:");
                        ((TextView) findViewById(R.id.lowerTextName)).setText(pants.getString("name"));

                    } catch (JSONException e) {
                        ((TextView) findViewById(R.id.upperTextType)).setText(e.toString());
                    }
                }
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
}