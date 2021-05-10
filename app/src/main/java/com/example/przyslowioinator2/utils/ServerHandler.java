package com.example.przyslowioinator2.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
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


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ServerHandler {

    public synchronized static ArrayList<Przyslowie> getPrzyslowia(Context context, View v) {

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
                    String FILENAME = "hello_file.txt";
                    String string = "hello world!";

                    if(isSuccess){
                        JSONArray resp = null;
                        try {
                            resp = response.getJSONArray("przyslowia");

                            FileOutputStream fos = v.getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);

                            for(int i = 0; i < resp.length(); i++){
                                JSONObject respJSONObject=resp.getJSONObject(i);

                                fos.write(respJSONObject.toString().getBytes()); //zapisanie JSONA w pliku
                                fos.write("\n".getBytes());

                                przyslowia.add(new Przyslowie(respJSONObject.getString("tresc")));
                            }
                            fos.close();
                        } catch (JSONException | FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }




                    }else{
                        Log.e("ServerHandler", stringError);
                        //Toast.makeText(context,"c1",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ServerHandler", error.getMessage());
                    //Toast.makeText(context,"c2",Toast.LENGTH_LONG).show();
                }
            });

            RequestSingleton.getInstance(context).addToRequestQueue(sr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("ServerHandler", String.valueOf(przyslowia.size()));
        if(przyslowia.size()==0){
            przyslowia.addAll(loadFromFilePrzyslowie(context,v));
        }
        return przyslowia;
    }
    public static ArrayList<Przyslowie> loadFromFilePrzyslowie(Context context, View v){
        String FILENAME = "hello_file.txt";
        ArrayList<Przyslowie> przyslowia2 = new ArrayList<>();
        String strLine,prz;
        try {

            FileInputStream fis = v.getContext().openFileInput(FILENAME);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =new BufferedReader(new InputStreamReader(in));

            JSONObject testSON= new JSONObject();
            while ((strLine = br.readLine()) != null) {
                prz = strLine;
                testSON = new JSONObject(prz);
                przyslowia2.add(new Przyslowie(testSON.getString("tresc")));
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(context,"c1",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(context,"c2",Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            //Toast.makeText(context,"c3",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return przyslowia2;
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
