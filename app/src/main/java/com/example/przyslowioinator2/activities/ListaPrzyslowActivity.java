package com.example.przyslowioinator2.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.utils.ItemDecoration;
import com.example.przyslowioinator2.adapters.ListaPrzyslowAdapter;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.ServerHandler;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaPrzyslowActivity extends AppCompatActivity  implements ListaPrzyslowAdapter.OnItemListener {
    ArrayList<Przyslowie> przyslowa; //!!!!! PrzysLOWA a nie przysLOWIA

    ListaPrzyslowAdapter mListaPrzyslowAdapter;
    RecyclerView mRecyclerView;
    Button goBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.przyslow_listaactivity);

        mRecyclerView = findViewById(R.id.przyslowiaListView);
        new GetPrzyslowiaFromServer().execute();

        goBack = findViewById(R.id.go_back_lista);
        goBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

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

    private class GetPrzyslowiaFromServer extends AsyncTask<Void, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            przyslowa = ServerHandler.getPrzyslowia(getApplicationContext(),findViewById(android.R.id.content).getRootView());
            while(przyslowa.size() < 1); //TODO: do timeout after few seconds
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Log.v("ListaPrzyslowActivity", String.valueOf(przyslowa.size()));
            przyslowa.sort(new Przyslowie.PrzyslowieComparator());
            mListaPrzyslowAdapter = new ListaPrzyslowAdapter(przyslowa, ListaPrzyslowActivity.this, findViewById(android.R.id.content).getRootView());
            mRecyclerView.setAdapter(mListaPrzyslowAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }
}
