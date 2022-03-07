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

/**
 * Show the scores for each game
 */
public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //VARIABLES
    private ArrayList<ScoreModel> scoreModels = new ArrayList<>();
    private ListView listView;
    private String[] games;
    private Spinner spinner;
    private DBHelper dbHelper;
    private boolean activityPressed = false;
    private ImageButton back;
    private int actualSpinnerPosition;

    //OVERRIDE METHODS

    /**
     * Initialize variables and set listeners
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        //Initialize variables
        dbHelper = new DBHelper(getApplicationContext());
        games = getResources().getStringArray(R.array.games);
        spinner = findViewById(R.id.spinner_score);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.score_spinner_item, games);
        arrayAdapter.setDropDownViewResource(R.layout.score_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        back = findViewById(R.id.back_button_score);
        listView = findViewById(R.id.listview_score);

        //Listener that closes this activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                ScoresActivity.this.finish();
            }
        });

    }


    /**
     * When a spinner item is selected show the scores according to the game selected
     * @param adapterView AdapderView - ?
     * @param view View
     * @param position int
     * @param l long
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        MenuActivity.effects.playEffect(R.raw.menu_pick);
        //Save the spinner position
        actualSpinnerPosition = position;
        //Check for the item picked and fill the scores taking them from the database (Ordered by HIGHSCORE default)
        if (games[position].equalsIgnoreCase("peg")){
            fillPegScoreModels(DBHelper.OrderBy.HIGHSCORE);


        }else if(games[position].equalsIgnoreCase("2048")){
            fill2048ScoreModels(DBHelper.OrderBy.HIGHSCORE);
        }
        setUpListViewAdapter();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Pause the app and the music
     */
    @Override
    protected void onPause() {
        if (!activityPressed) {

            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    /**
     * Resume the app and the music
     */
    @Override
    protected void onResume() {
        MenuActivity.music.resume();
        super.onResume();
    }


    /**
     * Called by a button onClick, this will order the scores by the button pressed and depending on the game that has been pressed
     * @param view View
     */
    public void scoreButtonOrderBy(View view) {
        MenuActivity.effects.playEffect(R.raw.menu_pick);

        Button button = (Button)view; //set the view as a button

        if (button.getText().toString().equalsIgnoreCase(getString(R.string.score_user))) {//if score_user button is pressed
            if (games[actualSpinnerPosition].equalsIgnoreCase("peg")) {//on game peg
                fillPegScoreModels(DBHelper.OrderBy.USER);//order by peg

            } else {//on game2048
                fill2048ScoreModels(DBHelper.OrderBy.USER);//order by user

            }
        //AND SO ON LIKE THE IF BUT WITH THE OTHER BUTTONS
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
        //Update the list view
        setUpListViewAdapter();

    }

    /**
     * Update/create the score adapter with the scoreModels list and set it to the listView
     */
    private void setUpListViewAdapter() {
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, scoreModels);
        listView.setAdapter(scoreAdapter);
    }

    /**
     * Fill scoreModels list with peg scores
     * @param orderBy DBHelper.OrderBy
     */
    private void fillPegScoreModels(DBHelper.OrderBy orderBy) {
        scoreModels.clear();
        scoreModels = dbHelper.selectAllUsersPeg(orderBy);


    }

    /**
     * Fill scoreModels list with 2048 scores
     * @param orderBy DBHelper.OrderBy
     */
    private void fill2048ScoreModels(DBHelper.OrderBy orderBy) {
        scoreModels.clear();
        scoreModels = dbHelper.selectAllUsers2048(orderBy);
    }





}