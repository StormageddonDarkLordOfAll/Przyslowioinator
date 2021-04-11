package com.example.przyslowioinator2.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.przyslowioinator2.models.Przyslowie;

import java.util.List;
import java.util.Random;

public class PrzyslowiaUtils {

    public static String randomPrzyslowie(Context context, List<Przyslowie> przyslowia){
        if(przyslowia.size()<1) {
            przyslowia = ServerHandler.getPrzyslowia(context);
        }
        if(przyslowia.size()<1) {
            Toast.makeText(context,"Error brak przyslow - MAIN",Toast.LENGTH_LONG).show();
            return "";
        }
        Random r = new Random();
        int losowy = r.nextInt(przyslowia.size());
        String linia = przyslowia.get(losowy).getTresc();
        przyslowia.remove(losowy);
        Toast.makeText(context, linia, Toast.LENGTH_LONG).show();
        Log.v("PrzyslowiaUtils", String.valueOf(przyslowia.size()));
        return linia;
    }
}
