package com.coms_309.ad_4.sharpdressedcy;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This downloads the weather from the OpenWeatherMap API.
 */
public class DownloadWeather extends AsyncTask<String, Void, String> {

    String result;
    private WeatherActivity activity;

    /**
     * This calls the data from the API for the sent activity
     * @param activity
     *          Instance of WeatherActivity to send data to
     */
    public DownloadWeather(WeatherActivity activity) {
        this.activity = activity;
    }

    /**
     * This downloads the weather from the selected URL
     * @param urls
     *      URL to download weather from
     * @return
     *      String result of data
     */
    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;


        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {
                char current =  (char) data;
                result += current;
                data = reader.read();

            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * This sets the list of the weather.
     * @param result
     *          String of the results
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        activity.setList(result);
    }
}
