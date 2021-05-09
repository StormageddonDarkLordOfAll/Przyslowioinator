package com.example.przyslowioinator2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.przyslowioinator2.R;
import com.example.przyslowioinator2.utils.ServerHandler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewPrzyslowieActivity extends AppCompatActivity {

    Button goBackButton;
    Button addButton;
    EditText typePrzyslowieEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_przyslowie);

        goBackButton = (Button) findViewById(R.id.go_back_addPrzyslowie);
        addButton = (Button) findViewById(R.id.addPrzyslowieButton);
        typePrzyslowieEditText = (EditText) findViewById(R.id.editTextAddPrzyslowie);

        goBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ServerHandler.addPrzyslowie(getApplicationContext(), typePrzyslowieEditText.getText().toString());
                goToMainActivity();
            }
        });

    }

    private void goToMainActivity(){
        Intent goToMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(goToMainActivityIntent);
    }
}
