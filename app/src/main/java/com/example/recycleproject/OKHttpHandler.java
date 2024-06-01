package com.example.recycleproject;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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

    public int savePoints(String url, String username, int points) throws Exception {
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

    public String getPoints(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        return data;

    }

    public String getPointsPerUser(String url, String uname) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        return data;

    }

}
