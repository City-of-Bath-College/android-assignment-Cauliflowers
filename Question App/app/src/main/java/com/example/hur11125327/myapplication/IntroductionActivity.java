package com.example.hur11125327.myapplication;

/**Created by hur11125327 on 07/10/2015.**/
//Import declarations
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the layout utilised
        setContentView(R.layout.activity_introduction);
        //Finds the buttons in the in activity_introduction layout
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnHighScore = (Button) findViewById(R.id.btnHighScore);
        Button btnAbout = (Button) findViewById(R.id.btnAbout);
        Button btnClear = (Button) findViewById(R.id.btnClear);

        //When the play button is clicked, the app moves to the main activity where questions are posed to the user
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        //When the highscore button is pressed, the app moves to the high score page listing all high scores
        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, HighScoreActivity.class);
                startActivity(i);
            }
        });
        //When the about button is played, the app moves to the profile page about the app developer
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });//When this button is clicked, all high scores are deleted from app memory
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().delete("high scores");
                Toast.makeText(IntroductionActivity.this, "High scores cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume(){
        super.onResume();
        Paper.init(this);
        //Displays highscores on the main app page
        List<HighScoreObject> highScores = Paper.book().read("high scores", new ArrayList<HighScoreObject>());
        int maxScore = 0;
        for (int i = 0; i < highScores.size(); i++) {
            HighScoreObject h = highScores.get(i);
            if (h.getScore() > maxScore) {
                maxScore = h.getScore();
            }
        }
        //Sets the test for the highest score
        TextView bestScoreText = (TextView) findViewById(R.id.bestScore);
        bestScoreText.setText("Current High Score is: " + maxScore);
        Log.d("James_App", "Reached onResume");
    }

    protected void onStop(){
        super.onStop();
        Log.d("James_App", "Reached onStop");
    }

}