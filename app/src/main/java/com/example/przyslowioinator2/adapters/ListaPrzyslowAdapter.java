package com.example.przyslowioinator2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.przyslowioinator2.MainActivity;
import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.models.Przyslowie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ListaPrzyslowAdapter extends RecyclerView.Adapter<ListaPrzyslowAdapter.ViewHolder>  {
    private final ArrayList<Przyslowie> przyslowia;
    private final OnItemListener mOnItemListener;

    public ListaPrzyslowAdapter(ArrayList<Przyslowie> przyslowia, OnItemListener onItemListener) {
        this.przyslowia = przyslowia;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_przyslow_row, viewGroup, false);

        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(przyslowia.get(position));
    }

    @Override
    public int getItemCount() {
        return przyslowia.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView typeText;
        LinearLayout linearLayout;
        Button addToClipboard;
        OnItemListener onItemListener;


        public ViewHolder(View view, OnItemListener onItemListener) {
            super(view);

            typeText = (TextView) view.findViewById(R.id.typeText);
            linearLayout = (LinearLayout) view.findViewById(R.id.expanded_row);
            addToClipboard = (Button) view.findViewById(R.id.copy_to_clipboard);
            addToClipboard.setOnClickListener(v -> {
                MainActivity.addToClipboard((String) typeText.getText());
            });
            this.onItemListener = onItemListener;

            view.setOnClickListener(this);
        }

        public TextView getTypeTextView() {
            return typeText;
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }

        private void bind(Przyslowie przyslowie){
            typeText.setText(przyslowie.getTresc());
            boolean expanded = przyslowie.isExpanded();
            linearLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }


    public interface OnItemListener{
        void onItemClick(int position);
    }
}
