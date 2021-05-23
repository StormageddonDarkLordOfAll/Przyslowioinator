package com.example.przyslowioinator2.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.adapters.ItemDecoration;
import com.example.przyslowioinator2.adapters.ListaPrzyslowAdapter;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.FavouritesUtils;
import com.example.przyslowioinator2.utils.ServerHandler;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouritesListActivity extends AppCompatActivity implements ListaPrzyslowAdapter.OnItemListener  {
    ArrayList<Przyslowie> przyslowa; //!!!!! PrzysLOWA a nie przysLOWIA

    ListaPrzyslowAdapter mListaPrzyslowAdapter;
    RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favourites_list_activity);

        mRecyclerView = findViewById(R.id.przyslowiaListView);
        przyslowa = FavouritesUtils.getFavourites(findViewById(android.R.id.content).getRootView());

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
}