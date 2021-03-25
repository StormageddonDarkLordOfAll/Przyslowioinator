package com.example.przyslowioinator2;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KomunikatorSerwerowy {
    public static ArrayList<String>  pobierzPrzyslowia(Context context) {
        //wyslanie do babzy danych zapytanych
        ArrayList<String> slowa = new ArrayList<String>();
        try {

            //wrzucenie podanych danych do jsona
            JSONObject jo = new JSONObject();
            jo.put("haslo","maslo");

            String url= "http://10.0.2.2:5000/przyslowia";


            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    boolean isSuccess = true;
                    String stringError = "";

                    if(isSuccess){
                        JSONArray resp = null;
                        try {
                            resp = response.getJSONArray("przyslowia");
                            for(int i=0; i<resp.length();i++){
                                JSONObject rzecz=resp.getJSONObject(i);
                                slowa.add(rzecz.getString("tresc"));

                                Toast.makeText(context,rzecz.getString("tresc"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(context,stringError,Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            RequestSingleton.getInstance(context).addToRequestQueue(sr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return slowa;
    }
}
