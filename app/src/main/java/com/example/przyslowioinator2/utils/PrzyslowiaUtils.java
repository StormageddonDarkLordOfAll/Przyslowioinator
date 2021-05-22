package com.example.przyslowioinator2.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.przyslowioinator2.models.Przyslowie;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

import androidx.annotation.RequiresApi;

public class PrzyslowiaUtils {

    public static String randomPrzyslowie(Context context, List<Przyslowie> przyslowia, View v){
        if(przyslowia.size()<1) {
            przyslowia = ServerHandler.getPrzyslowia(context,v);
        }
        if(przyslowia.size()<1) {
            Toast.makeText(context,"Error brak przyslow - MAIN",Toast.LENGTH_LONG).show();
            return "";
        }
        Random r = new Random();
        int losowy = r.nextInt(przyslowia.size());
        String linia = przyslowia.get(losowy).getFormattedText();
        przyslowia.remove(losowy);
        Toast.makeText(context, linia, Toast.LENGTH_LONG).show();
        Log.v("PrzyslowiaUtils", String.valueOf(przyslowia.size()));
        return linia;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isPrzyslowieFavourite(View view, Przyslowie przyslowie) {
        return FavouritesUtils.getFavourites(view).stream()
                .mapToInt(Przyslowie::getId)
                .filter(id -> przyslowie.getId() == id)
                .findFirst()
                .isPresent();
    }
}
