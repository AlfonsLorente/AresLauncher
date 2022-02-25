package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<ScoreModel> scoreModels = new ArrayList<>();
    private ListView listView;
    private String[] games;
    private Spinner spinner;
    private DBHelper dbHelper;
    private boolean activityPressed = false;
    private ImageButton back;
    private int actualSpinnerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        dbHelper = new DBHelper(getApplicationContext());
        games = getResources().getStringArray(R.array.games);
        spinner = findViewById(R.id.spinner_score);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.score_spinner_item, games);
        arrayAdapter.setDropDownViewResource(R.layout.score_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        back = findViewById(R.id.back_button_score);

        listView = findViewById(R.id.listview_score);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                ScoresActivity.this.finish();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        MenuActivity.effects.playEffect(R.raw.menu_pick);

        actualSpinnerPosition = position;
        if (games[position].equalsIgnoreCase("peg")){
            fillPegScoreModels(DBHelper.OrderBy.HIGHSCORE);


        }else if(games[position].equalsIgnoreCase("2048")){
            fill2048ScoreModels(DBHelper.OrderBy.HIGHSCORE);
        }
        setUpListViewAdapter();
    }

    private void setUpListViewAdapter() {
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, scoreModels);
        listView.setAdapter(scoreAdapter);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void fillPegScoreModels(DBHelper.OrderBy orderBy) {
        scoreModels.clear();
        scoreModels = dbHelper.selectAllUsersPeg(orderBy);


    }

    private void fill2048ScoreModels(DBHelper.OrderBy orderBy) {
        scoreModels.clear();
        scoreModels = dbHelper.selectAllUsers2048(orderBy);
    }

    @Override
    protected void onPause() {
        if (!activityPressed) {

            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    @Override
    protected void onResume() {
        MenuActivity.music.resume();
        super.onResume();
    }


    //onClick
    public void scoreButtonOrderBy(View view) {
        MenuActivity.effects.playEffect(R.raw.menu_pick);

        Button button = (Button)view;
        if (button.getText().toString().equalsIgnoreCase(getString(R.string.score_user))) {
            if (games[actualSpinnerPosition].equalsIgnoreCase("peg")) {
                fillPegScoreModels(DBHelper.OrderBy.USER);

            } else {
                fill2048ScoreModels(DBHelper.OrderBy.USER);

            }
        }else if(button.getText().toString().equalsIgnoreCase(getString(R.string.score_highscore))){
            if (games[actualSpinnerPosition].equalsIgnoreCase("peg")) {
                fillPegScoreModels(DBHelper.OrderBy.HIGHSCORE);

            } else {
                fill2048ScoreModels(DBHelper.OrderBy.HIGHSCORE);


            }

        }else if(button.getText().toString().equalsIgnoreCase(getString(R.string.score_time))){
            if (games[actualSpinnerPosition].equalsIgnoreCase("peg")) {
                fillPegScoreModels(DBHelper.OrderBy.TIME);

            } else {
                fill2048ScoreModels(DBHelper.OrderBy.TIME);


            }
        }
        setUpListViewAdapter();

    }
}