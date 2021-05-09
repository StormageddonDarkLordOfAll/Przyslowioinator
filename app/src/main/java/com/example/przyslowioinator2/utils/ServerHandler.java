package com.example.przyslowioinator2.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServerHandler {

    public synchronized static ArrayList<Przyslowie> getPrzyslowia(Context context) {

        ArrayList<Przyslowie> przyslowia = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("haslo","maslo");

            String url = "http://10.0.2.2:5000/przyslowia";


            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    boolean isSuccess = true;
                    String stringError = "";

                    if(isSuccess){
                        JSONArray resp = null;
                        try {
                            resp = response.getJSONArray("przyslowia");
                            for(int i = 0; i < resp.length(); i++){
                                JSONObject respJSONObject=resp.getJSONObject(i);
                                przyslowia.add(new Przyslowie(respJSONObject.getString("tresc")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Log.e("ServerHandler", stringError);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ServerHandler", error.getMessage());
                }
            });

            RequestSingleton.getInstance(context).addToRequestQueue(sr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("ServerHandler", String.valueOf(przyslowia.size()));
        return przyslowia;
    }

    public static void addPrzyslowie(Context context, String przyslowie){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("przyslowie", przyslowie);

            String url = "http://10.0.2.2:5000/addPrzyslowie";

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String stringError = "";

                    try {

                        if(!response.getBoolean("success")) {
                            stringError = response.getString("errorString");
                            Log.e("ServerHandler", stringError);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ServerHandler", error.getMessage());
                }
            });

            RequestSingleton.getInstance(context).addToRequestQueue(objectRequest);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}
