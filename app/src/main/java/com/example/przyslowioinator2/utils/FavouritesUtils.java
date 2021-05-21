package com.example.przyslowioinator2.utils;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.przyslowioinator2.models.Przyslowie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FavouritesUtils {

    private static final String FILENAME = "favourites.txt";

    public static void addToFavourites(Przyslowie przyslowie, View view){
        JSONObject mainJsonObject = new JSONObject();
        JSONObject przyslowieJson = new JSONObject();
        JSONArray przyslowiaJsonArray = null;
        StringBuilder jsonArrayBuilder = new StringBuilder();
        String strLine;

        File file = new File(view.getContext().getFilesDir(), FILENAME);
        if(file.exists()){
            try {
                FileInputStream fis = view.getContext().openFileInput(FILENAME);
                DataInputStream dataInputStream = new DataInputStream(fis);
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(dataInputStream));

                while ((strLine = bufferedReader.readLine()) != null) {
                    jsonArrayBuilder.append(strLine);
                }

                przyslowiaJsonArray = new JSONObject(jsonArrayBuilder.toString()).getJSONArray("przyslowia");
                fis.close();
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            przyslowiaJsonArray = new JSONArray();
        }

        try {
            FileOutputStream fos = view.getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);

            int przyslowieId = przyslowie.getId();

            if (przyslowiaJsonArray != null) {
                for(int i = 0; i < przyslowiaJsonArray.length(); i++){
                    JSONObject favouriteJson = przyslowiaJsonArray.getJSONObject(i);
                    if(Integer.parseInt(favouriteJson.getString("id")) == przyslowieId){
                        Toast.makeText(view.getContext(), "Przyslowie już jest na liście ulubionych", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            przyslowieJson.put("id", przyslowie.getId());
            przyslowieJson.put("tresc", przyslowie.getText());

            assert przyslowiaJsonArray != null;
            przyslowiaJsonArray.put(przyslowieJson);

            mainJsonObject.put("przyslowia", przyslowiaJsonArray);

            fos.write(mainJsonObject.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Przyslowie> getFavourites(View view){
        ArrayList<Przyslowie> favouritePrzyslowia = new ArrayList<>();
        JSONArray przyslowiaJsonArray;
        StringBuilder jsonArrayBuilder = new StringBuilder();
        String strLine;

        File file = new File(view.getContext().getFilesDir(), FILENAME);
        if(file.exists()){
            try {
                FileInputStream fis = view.getContext().openFileInput(FILENAME);
                DataInputStream dataInputStream = new DataInputStream(fis);
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(dataInputStream));

                while ((strLine = bufferedReader.readLine()) != null) {
                    jsonArrayBuilder.append(strLine);
                }
                fis.close();

                przyslowiaJsonArray = new JSONObject(jsonArrayBuilder.toString()).getJSONArray("przyslowia");

                for(int i = 0; i < przyslowiaJsonArray.length(); i++){
                    JSONObject przyslowieJson = przyslowiaJsonArray.getJSONObject(i);
                    Przyslowie przyslowie = new Przyslowie(
                            Integer.parseInt(przyslowieJson.getString("id")),
                            przyslowieJson.getString("tresc")
                    );
                    favouritePrzyslowia.add(przyslowie);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return favouritePrzyslowia;
    }
}
