package com.example.recycleproject;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.*;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpHandler {
    public OKHttpHandler(){
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    public int logUser(String url) throws Exception { //returns 1 if the user is admin, 0 if he is a simple user and -1 if the user was not found
        int isAdmin = -1;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();

        try {
            JSONObject json = new JSONObject(data);

            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {
                String username = keys.next();
                String password = json.get(username).toString();
                isAdmin = Integer.parseInt(password.substring(password.length()-1));
                //password = password.substring(0, password.length()-1); //remove the last char, which shows whether the user is an admin or not
            }
        }catch(JSONException e) {return -1;}

        return isAdmin;

    }

    public int regUser(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        data = data.replace("\n", "").replace("\r", ""); //strip new-line
        int res = Integer.parseInt(data); //-1 if account exists, 1 else

        return res;

    }

    public int savePoints(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        JSONObject jsonResponse = new JSONObject(data);
        int res = jsonResponse.getInt("status"); // -1 if failed, 1 if success
        return res;
    }

    public int saveAllRecycled(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        JSONObject jsonResponse = new JSONObject(data);
        int res = jsonResponse.getInt("status"); // -1 if failed, 1 if success
        return res;
    }

    //addGetAllRecycled
    public String getPoints(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        return data;

    }

//set current points to zero after awards are given
    public int resetCurrentRecycledPoints(String url, String username) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Build URL with query parameters
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("username", username);
        urlBuilder.addQueryParameter("current_paper", "0");
        urlBuilder.addQueryParameter("current_glass", "0");
        urlBuilder.addQueryParameter("current_metal", "0");
        urlBuilder.addQueryParameter("current_plastic", "0");
        String finalUrl = urlBuilder.build().toString();

        // Create and execute request
        Request request = new Request.Builder().url(finalUrl).build();
        Response response = client.newCall(request).execute();
        return response.isSuccessful() ? 1 : -1;
    }


    public int saveCurrentRecycledPoints(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        data = data.replace("\n", "").replace("\r", ""); //strip new-line
        int res = Integer.parseInt(data);

        return res;
    }

    public String getPointsPerUser(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        return data;

    }

    //returns current paper, glass, metal, plastic per user
    public String getRecycledPointsPerUser(String url, String uname) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Append username to the URL
        String finalUrl = url + "?username=" + uname;

        Request request = new Request.Builder().url(finalUrl).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public String[] getAllUsernames(String url) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        JSONArray jsonArray = new JSONArray(jsonResponse);
        String[] usernames = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            usernames[i] = jsonArray.getString(i);
        }
        return usernames;
    }

    public String getBestStatistics(String url, String users[], String points[]) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();

        try {
            JSONArray arr = new JSONArray(data);
            JSONObject first = (JSONObject) arr.get(0); //fetch top 3 users

            for (int i=0; i<3; i++){
                JSONObject temp = (JSONObject) arr.get(i); //cast from Object to JSONObject
                users[i] = temp.get("username").toString();
                points[i] = temp.get("points").toString();
            }

        }catch(JSONException e) {e.printStackTrace(); return "error";}

        return "OK";
    }
}
