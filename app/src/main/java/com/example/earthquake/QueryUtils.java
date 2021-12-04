package com.example.earthquake;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {
    private static final String LOG_TAG= QueryUtils.class.getSimpleName();

    /** Sample JSON response for a USGS query */

    private QueryUtils() {

    }
    private  static URL createUrl(String stringUrl){
        URL url= null;
        try {
            url = new URL(stringUrl);
        }
       catch (MalformedURLException e){
            Log.e(LOG_TAG,"error in create url",e);
       }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";
        if (url==null){
            return jsonResponse;
        }
       HttpURLConnection urlConnection = null;
        InputStream inputStream= null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(150000);
            urlConnection.setRequestMethod("GET");
            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse =readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG,"error response code"+ urlConnection.getResponseCode());
            }

        } catch (IOException e){
            Log.e(LOG_TAG,"problem in makeHttp method",e);
        }
        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }





    private static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }


            return output.toString();

    }


    public static List<earthQuake> extractFeaturesFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }

        List<earthQuake> earthquakes= new ArrayList<>();

        try {


            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
            for (int i = 0 ; i< earthquakeArray.length();i++){
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String place=properties.getString("place");
                Long time = properties.getLong("time");
                String url =properties.getString("url");
                earthQuake earthquake = new earthQuake(magnitude,place,time,url);
                earthquakes.add(earthquake);
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

    public static List<earthQuake> fetchEarthQuakeData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e){
            Log.e(LOG_TAG,"problem making http request", e);
        }
        List<earthQuake> earthquakes = extractFeaturesFromJson(jsonResponse);
        return earthquakes;

    }

}