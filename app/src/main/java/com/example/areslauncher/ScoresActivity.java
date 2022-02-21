package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ScoresActivity extends AppCompatActivity {

    private String[] games;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        games = getResources().getStringArray(R.array.games);
        spinner = findViewById(R.id.spinner_score);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.score_spinner_item, games);
        arrayAdapter.setDropDownViewResource(R.layout.score_spinner_item);
        spinner.setAdapter(arrayAdapter);


    }
}