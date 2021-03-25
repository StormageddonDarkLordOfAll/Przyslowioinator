package com.example.przyslowioinator2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> przyslowia=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button losujButton;
        losujButton = (Button) findViewById(R.id.losujButton);
        Button doListyButton;
        doListyButton = (Button) findViewById(R.id.doListyButton);

        losujButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                losujRequest();
            }
        });
        doListyButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent dolistyIntent = new Intent(getApplicationContext(), ListaPrzyslowActivity.class);
                startActivity(dolistyIntent);
            }
        });
        przyslowia  = pobierzPrzyslowia();




    }
    private ArrayList<String>  pobierzPrzyslowia() {
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
                    boolean isSuccess = false;
                    String stringError = "";

                    Toast.makeText(getApplicationContext(),"Przysłowia pobrane",Toast.LENGTH_LONG).show();
                    try {
                        isSuccess = response.getBoolean("success");
                        stringError = response.getString("errorString");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(isSuccess){
                        Toast.makeText(getApplicationContext(),"Przysłowia pobrane",Toast.LENGTH_LONG).show();
                        JSONArray resp = null;
                        try {
                            resp = response.getJSONArray("przyslowia");
                            for(int i=0; i<resp.length();i++){
                                JSONObject rzecz=resp.getJSONObject(i);
                                slowa.add(rzecz.getString("tresc"));
                            }
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

        return slowa;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void losujRequest(){//ArrayList<String> slowa, String sciezka

            //jesli wszystkie przyslowia zuzyte
            if(przyslowia.size()<1) {
                przyslowia = pobierzPrzyslowia();
            }
            if(przyslowia.size()<1) {
                //przyslowia.add("ERROR BRAK PRZYSLOW");
                Toast.makeText(getApplicationContext(),"Error brak przyslow",Toast.LENGTH_LONG).show();
                return;
            }
            int losowy = ThreadLocalRandom.current().nextInt(0, przyslowia.size()); //+1
            String linia = przyslowia.get(losowy);
        przyslowia.remove(losowy);
        ClipboardManager clipboard = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }
        ClipData clip = ClipData.newPlainText("Przyslowie", linia);
        clipboard.setPrimaryClip(clip);

    }
}