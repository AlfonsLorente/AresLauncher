package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameChooserActivity extends AppCompatActivity {
    private ImageButton button2048, buttonPeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chooser);
        button2048 = findViewById(R.id.button2048);
        buttonPeg = findViewById(R.id.button_peg);

        button2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameChooserActivity.this, Launcher2048Activity.class));
                GameChooserActivity.this.finish();

            }
        });

        buttonPeg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameChooserActivity.this, LauncherPegActivity.class));
                GameChooserActivity.this.finish();
            }
        });

    }

}