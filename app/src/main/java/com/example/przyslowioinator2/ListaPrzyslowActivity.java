package com.example.przyslowioinator2;

import android.app.AppComponentFactory;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaPrzyslowActivity extends AppCompatActivity {
    ArrayList<String> przyslowa = new ArrayList<String>(); //!!!!! PrzysLOWA a nie przysLOWIA
    ListaPrzyslowAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.przyslow_listaactivity);
        RecyclerView widokRecyklingu = findViewById(R.id.przyslowiaListView);

        adapter = new ListaPrzyslowAdapter(przyslowa);

        widokRecyklingu.setAdapter(adapter);
        widokRecyklingu.setLayoutManager(new LinearLayoutManager(this));

        przyslowa = KomunikatorSerwerowy.pobierzPrzyslowia(getApplicationContext());
    }
}
