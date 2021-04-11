package com.example.przyslowioinator2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.przyslowioinator2.adapters.ItemDecoration;
import com.example.przyslowioinator2.adapters.ListaPrzyslowAdapter;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.RequestSingleton;
import com.example.przyslowioinator2.utils.ServerHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            przyslowa = ServerHandler.getPrzyslowia(getApplicationContext());
            while(przyslowa.size() < 1); //TODO: do timeout after few seconds
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Log.v("ListaPrzyslowActivity", String.valueOf(przyslowa.size()));
            mListaPrzyslowAdapter = new ListaPrzyslowAdapter(przyslowa, ListaPrzyslowActivity.this);
            mRecyclerView.setAdapter(mListaPrzyslowAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }
}
