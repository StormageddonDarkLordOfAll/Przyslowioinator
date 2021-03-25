package com.example.przyslowioinator2;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaPrzyslowActivity extends AppCompatActivity {
    ArrayList<String> przyslowa = new ArrayList<String>(); //!!!!! PrzysLOWA a nie przysLOWIA

    ListaPrzyslowAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.przyslow_listaactivity);
        RecyclerView widokRecyklingu = findViewById(R.id.przyslowiaListView);

        adapter = new ListaPrzyslowAdapter(przyslowa, this);

        widokRecyklingu.setAdapter(adapter);
        widokRecyklingu.setLayoutManager(new LinearLayoutManager(this));

        pobierzPrzyslowia();
    }

    private void pobierzPrzyslowia() {
        try {

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
                                przyslowa.add(rzecz.getString("tresc"));

                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),stringError,Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            RequestSingleton.getInstance(this).addToRequestQueue(sr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
