package com.example.przyslowioinator2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ListaPrzyslowAdapter extends RecyclerView.Adapter<ListaPrzyslowAdapter.ViewHolder> {
    private ArrayList<String> przyslowia;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeText;


        public ViewHolder(View view) {
            super(view);

            typeText = (TextView) view.findViewById(R.id.typeText);

        }

        public TextView getTypeTextView() {
            return typeText;
        }


    }

    public ListaPrzyslowAdapter(ArrayList<String> przyslowia) {
        this.przyslowia = przyslowia;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_przyslow_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTypeTextView().setText(przyslowia.get(position));
    }

    @Override
    public int getItemCount() {
        return przyslowia.size();
    }
}
