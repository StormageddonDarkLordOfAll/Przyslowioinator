package com.example.przyslowioinator2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.adapters.ItemDecoration;
import com.example.przyslowioinator2.adapters.ListaPrzyslowAdapter;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.FavouritesUtils;
import com.example.przyslowioinator2.utils.PrzyslowiaUtils;
import com.example.przyslowioinator2.utils.ServerHandler;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouritesListActivity extends AppCompatActivity implements ListaPrzyslowAdapter.OnItemListener  {
    ArrayList<Przyslowie> przyslowa; //!!!!! PrzysLOWA a nie przysLOWIA

    ListaPrzyslowAdapter mListaPrzyslowAdapter;
    RecyclerView mRecyclerView;
    Button goBack;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favourites_list_activity);

        mRecyclerView = findViewById(R.id.przyslowiaListView);
        przyslowa = FavouritesUtils.getFavourites(findViewById(android.R.id.content).getRootView());
        przyslowa.sort(new Przyslowie.PrzyslowieComparator());

        goBack = findViewById(R.id.go_back_favourites);
        goBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        mListaPrzyslowAdapter = new ListaPrzyslowAdapter(przyslowa, FavouritesListActivity.this, findViewById(android.R.id.content).getRootView());
        mRecyclerView.setAdapter(mListaPrzyslowAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        RecyclerView.ItemDecoration itemDecoration = new ItemDecoration(getResources().getDrawable(R.drawable.divider));
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onItemClick(int position) {
        boolean expanded = przyslowa.get(position).isExpanded();
        przyslowa.get(position).setExpanded(!expanded);
        mListaPrzyslowAdapter.notifyItemChanged(position);
    }

    private void goToMainActivity(){
        Intent goToMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(goToMainActivityIntent);
    }
}