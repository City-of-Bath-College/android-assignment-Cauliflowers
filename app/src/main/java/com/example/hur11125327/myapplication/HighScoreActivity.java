package com.example.hur11125327.myapplication;

/**Created by James on 15/11/2015.**/

//Import Declarations
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.paperdb.Paper;

public class HighScoreActivity extends AppCompatActivity {
    //Variable declarations
    private ListView listView;
    private List<HighScoreObject> highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Uses the activity_high_score layout to display information
        setContentView(R.layout.activity_high_score);
        listView = (ListView)findViewById(R.id.listView);
        Paper.init(this);
        highscores = Paper.book().read("high scores", new ArrayList<HighScoreObject>());
        //Displays the number of highscores stored in memory as a toast message at the bottom of the high score screen
        Toast.makeText(this, "number = " + highscores.size() , Toast.LENGTH_LONG).show();

        HighscoreAdapter adapter = new HighscoreAdapter(highscores);

       listView.setAdapter(adapter);
    }

    private class HighscoreAdapter extends ArrayAdapter<HighScoreObject> {

        public HighscoreAdapter(List<HighScoreObject> items) {
            super(HighScoreActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        //Sets the layout used
                        R.layout.row_highscore, null);
            }


            //Acquires the highscore for the row being looked at.
            HighScoreObject highScore = highscores.get(position);
            Date date = new Date(highScore.getTimestamp());
            //Provides the format for the date
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            TextView lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
            //Information format for the row
            lblTitle.setText(highScore.getScore() + " - " + highScore.getName() + " - " + fmtOut.format(date));
            return convertView;
        }//getView ends here
    }//Adapter class ends here
}