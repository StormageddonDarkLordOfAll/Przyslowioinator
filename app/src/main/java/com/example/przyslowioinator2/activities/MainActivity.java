package com.example.przyslowioinator2.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.FavouritesUtils;
import com.example.przyslowioinator2.utils.PrzyslowiaUtils;
import com.example.przyslowioinator2.utils.ServerHandler;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    List<Przyslowie> przyslowia = new ArrayList<>();

    private static ClipboardManager clipboardManager;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button losujButton;
        losujButton = findViewById(R.id.losujButton);
        Button doListyButton;
        doListyButton = findViewById(R.id.doListyButton);
        Button addPrzyslowieButton;
        addPrzyslowieButton = findViewById(R.id.go_to_add_przyslowie);
        Button goToFavouritesButton;
        goToFavouritesButton = findViewById(R.id.go_to_favourites);

        losujButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                addToClipboard(PrzyslowiaUtils.randomPrzyslowie(getApplicationContext(), przyslowia,findViewById(android.R.id.content).getRootView()));
            }
        });
        doListyButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                goToListaPrzyslowActivity();
            }
        });

        addPrzyslowieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewPrzyslowieActivity();
            }
        });

        goToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFavouritesListActivity();
            }
        });



        przyslowia = ServerHandler.getPrzyslowia(this, findViewById(android.R.id.content).getRootView());

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

    private void goToNewPrzyslowieActivity(){
        Intent toAddIntent = new Intent(this, NewPrzyslowieActivity.class);
        startActivity(toAddIntent);
    }

    private void goToFavouritesListActivity() {
        Intent toFavouritesIntent = new Intent(this, FavouritesListActivity.class);
        startActivity(toFavouritesIntent);
    }
}