package com.example.przyslowioinator2.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.adapters.ItemDecoration;
import com.example.przyslowioinator2.adapters.ListaPrzyslowAdapter;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.ServerHandler;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaPrzyslowActivity extends AppCompatActivity  implements ListaPrzyslowAdapter.OnItemListener {
    ArrayList<Przyslowie> przyslowa; //!!!!! PrzysLOWA a nie przysLOWIA

    ListaPrzyslowAdapter mListaPrzyslowAdapter;
    RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.przyslow_listaactivity);

        mRecyclerView = findViewById(R.id.przyslowiaListView);
        new GetPrzyslowiaFromServer().execute();

        RecyclerView.ItemDecoration itemDecoration = new ItemDecoration(getResources().getDrawable(R.drawable.divider));
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onItemClick(int position) {
        boolean expanded = przyslowa.get(position).isExpanded();
        przyslowa.get(position).setExpanded(!expanded);
        mListaPrzyslowAdapter.notifyItemChanged(position);
    }

    private class GetPrzyslowiaFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            przyslowa = ServerHandler.getPrzyslowia(getApplicationContext(),findViewById(android.R.id.content).getRootView());
            while(przyslowa.size() < 1); //TODO: do timeout after few seconds
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Log.v("ListaPrzyslowActivity", String.valueOf(przyslowa.size()));
            mListaPrzyslowAdapter = new ListaPrzyslowAdapter(przyslowa, ListaPrzyslowActivity.this, findViewById(android.R.id.content).getRootView());
            mRecyclerView.setAdapter(mListaPrzyslowAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            /*
            String FILENAME = "hello_file";
            String string = "hello world!";

            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                JSONObject prz;
                for(int i =0; i<przyslowa.size(); i++){

                    fos.write(prz.getBytes());
                }
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

        }
    }
}
