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
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.PrzyslowiaUtils;
import com.example.przyslowioinator2.utils.RequestSingleton;
import com.example.przyslowioinator2.utils.ServerHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    ArrayList<Przyslowie> przyslowia = new ArrayList<>();

    private static ClipboardManager clipboardManager;
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
                addToClipboard(PrzyslowiaUtils.randomPrzyslowie(getApplicationContext(), przyslowia));
            }
        });
        doListyButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                goToListaPrzyslowActivity();
            }
        });

        przyslowia = ServerHandler.getPrzyslowia(this);

        setClipboardManager();
    }


    public static void addToClipboard(String text){
        ClipData clip = ClipData.newPlainText("Przyslowie", text);
        clipboardManager.setPrimaryClip(clip);
    }

    private void setClipboardManager(){
        clipboardManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }
    }

    private void goToListaPrzyslowActivity(){
        Intent toListIntent = new Intent(this, ListaPrzyslowActivity.class);
        startActivity(toListIntent);
    }
}