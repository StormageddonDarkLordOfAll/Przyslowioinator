package com.example.przyslowioinator2.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.przyslowioinator2.activities.MainActivity;
import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.models.Przyslowie;
import com.example.przyslowioinator2.utils.FavouritesUtils;
import com.example.przyslowioinator2.utils.PrzyslowiaUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;


public class ListaPrzyslowAdapter extends RecyclerView.Adapter<ListaPrzyslowAdapter.ViewHolder>  {
    private final ArrayList<Przyslowie> przyslowia;
    private final OnItemListener mOnItemListener;
    private final View view;

    public ListaPrzyslowAdapter(ArrayList<Przyslowie> przyslowia, OnItemListener onItemListener, View view) {
        this.przyslowia = przyslowia;
        this.mOnItemListener = onItemListener;
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_przyslow_row, viewGroup, false);

        return new ViewHolder(view, mOnItemListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(przyslowia.get(position), view);
    }

    @Override
    public int getItemCount() {
        return przyslowia.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView typeText;
        LinearLayout linearLayout;
        Button addToClipboard;
        Button wiktionaryButton;
        Button saveButton;
        OnItemListener onItemListener;
        String uri;
        Przyslowie przyslowie;


        @RequiresApi(api = Build.VERSION_CODES.N)
        public ViewHolder(View view, OnItemListener onItemListener) {
            super(view);

            typeText = view.findViewById(R.id.typeText);
            linearLayout = view.findViewById(R.id.expanded_row);
            addToClipboard = view.findViewById(R.id.copy_to_clipboard);
            addToClipboard.setOnClickListener(v -> {
                MainActivity.addToClipboard((String) typeText.getText());
            });
            wiktionaryButton = view.findViewById(R.id.wiktionary);
            wiktionaryButton.setOnClickListener(v -> {
                startActivity(view.getContext(), new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), null);
            });
            saveButton = view.findViewById(R.id.save_button);
            saveButton.setOnClickListener(view1 -> {
                if(PrzyslowiaUtils.isPrzyslowieFavourite(view, przyslowie)){
                    FavouritesUtils.removeFromFavourites(przyslowie, view);
                    saveButton.setText("+");
                }
                else {
                    FavouritesUtils.addToFavourites(przyslowie, view);
                    saveButton.setText("-");
                }
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void bind(Przyslowie przyslowie, View view){
            typeText.setText(przyslowie.getFormattedText());
            boolean expanded = przyslowie.isExpanded();
            linearLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
            if(PrzyslowiaUtils.isPrzyslowieFavourite(view, przyslowie)){
                saveButton.setText("-");
            }
            else {
                saveButton.setText("+");
            }
            uri = przyslowie.getWiktionaryLink();
            this.przyslowie = przyslowie;
        }
    }


    public interface OnItemListener{
        void onItemClick(int position);
    }
}
