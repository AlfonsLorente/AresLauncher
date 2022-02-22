package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<ScoreModel> scoreModels = new ArrayList<>();
    private ListView listView;
    private String[] games;
    private Spinner spinner;
    private DBHelper dbHelper;
    private DBHelper.OrderBy orderBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        dbHelper = new DBHelper(getApplicationContext());
        orderBy = DBHelper.OrderBy.HIGHSCORE;
        games = getResources().getStringArray(R.array.games);
        spinner = findViewById(R.id.spinner_score);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.score_spinner_item, games);
        arrayAdapter.setDropDownViewResource(R.layout.score_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        listView = findViewById(R.id.listview_score);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (games[position].equalsIgnoreCase("peg")){
            fillPegScoreModels();

        }else if(games[position].equalsIgnoreCase("2048")){
            fill2048ScoreModels();
        }
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, scoreModels);
        listView.setAdapter(scoreAdapter);
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void fillPegScoreModels() {
        scoreModels.clear();
        scoreModels = dbHelper.selectAllUsersPeg(orderBy);


    }

    private void fill2048ScoreModels() {
        scoreModels.clear();
        scoreModels = dbHelper.selectAllUsers2048(orderBy);
    }
}