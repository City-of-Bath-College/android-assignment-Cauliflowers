package com.example.hur11125327.myapplication;

/**Created by hur11125327 on 07/10/2015.**/

//Import declarations.
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import io.paperdb.Paper;
import android.text.InputType;
import android.media.MediaPlayer;

//import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    //Full list of all private variables.
    private TextView question;
    private Button btnTrue;
    private Button btnFalse;
    private ImageView img;
    private List<QuestionObject> questions;
    private QuestionObject currentQuestion;
    private int index;
    private int score;
    private TextView InGameScore;
    private String userName = "";
    //Firebase database URL
    //Firebase myFirebaseRef = new Firebase("https://torrid-fire-1024.firebaseio.com/#-K3Uu7Z-LKNO9VLg3zoM|8b53ccb3e2ef21183574842662371c6e/");
    private MediaPlayer player;

    //Beginning of the main app itself.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase Initisialisation
        //Firebase.setAndroidContext(this);
        //Sets the layout utilised by this activity
        setContentView(R.layout.activity_main);

        //These connect to variables stated above.
        question = (TextView)findViewById(R.id.question);
        btnTrue = (Button)findViewById(R.id.btnTrue);
        btnFalse = (Button)findViewById(R.id.btnFalse);
        img = (ImageView)findViewById(R.id.img);
        InGameScore = (TextView)findViewById(R.id.InGameScore);

        //Sets the text for the question.
        question.setText("Is this space?");

        //Sets the picture for the question.
        img.setImageResource(R.drawable.space);

        //Index and score variables, by default their value is 0, which increases as the user progresses through questions.
        index = 0;
        score = 0;

        //The true onclick listener.
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(true);
            }
        });
        //The false onclick listener.
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(false);
            }
        });

        //Functions the app will run, in order. Functions are declared below.
        generateQuestions();
        setUpQuestion();
        Paper.init(this);
    }
    //Question Generation Function - A list of all questions included in the app, including their correct answers, and associated pictures.
    private void generateQuestions(){
        Log.d("James_App", "Reached generateQuestions");
        questions = new ArrayList<>();
        questions.add(new QuestionObject("Is this planet Earth?", true, R.drawable.earth));
        questions.add(new QuestionObject("Is this the Sun?", true, R.drawable.sun));
        questions.add(new QuestionObject("Is this Mars?", false, R.drawable.jupiter));
        questions.add(new QuestionObject("Is this Neptune?", false, R.drawable.uranus));
        questions.add(new QuestionObject("Is this our Moon?", true, R.drawable.moon));
        questions.add(new QuestionObject("Is this the moon, Europa?", false, R.drawable.titan));
        questions.add(new QuestionObject("Is this the moon, Titan?", false, R.drawable.europa));
        questions.add(new QuestionObject("Is this Saturn?", true, R.drawable.saturn));
        questions.add(new QuestionObject("Is this Pluto?", true, R.drawable.pluto));
        questions.add(new QuestionObject("Is this Venus?", true, R.drawable.venus));
        questions.add(new QuestionObject("Is this the moon, Io?", true, R.drawable.io));
        questions.add(new QuestionObject("Is this Mercury?", true, R.drawable.mercury));
        questions.add(new QuestionObject("Is this Comet 67P?", true, R.drawable.comet67p));
        questions.add(new QuestionObject("Is this Uranus?", false, R.drawable.neptune));
        questions.add(new QuestionObject("Is this Jupiter?", true, R.drawable.jupiter));
        questions.add(new QuestionObject("Are there eight planets in our solar system?", true, R.drawable.solarsystem));
        questions.add(new QuestionObject("Are there 181 moons in our solar system?", true, R.drawable.moon1));
        questions.add(new QuestionObject("Are there 2000 comets in our solar system?", false, R.drawable.comet));
        questions.add(new QuestionObject("Are there 7 dwarf planets in our solar system?", false, R.drawable.dwarfplanet));
        Log.d("James_App", "Completed generateQuestions");
    }
    //Question Setup Function.
    private void setUpQuestion(){
        //Checks to see if all questions have been answered, otherwise creates the next question.
        if(index == questions.size()){
            player = MediaPlayer.create(MainActivity.this, R.raw.end);
            player.start();
            //This logs that all questions have been answered by the user.
            Log.d("JAMES_APP", "All questions answered");
            endGame();
        }else{
            //Creates a question.
            currentQuestion = questions.get(index);
            //Gets the question text.
            question.setText(currentQuestion.getQuestion());
            //Gets the question picture.
            img.setImageResource(currentQuestion.getPicture());
            //Increases the value of the index variable by 1.
            index++;
        }
    }
    //Function to determine the button press.
    private void determineButtonPress(boolean answer){
        boolean expectedAnswer = currentQuestion.isAnswer();
        //Checks to see if the user answered as expected, or not, thereby determining if they gave the correct answer or not.
        if (answer == expectedAnswer) {
            //Plays a sound file when the user answers correctly!
            player = MediaPlayer.create(MainActivity.this, R.raw.correct);
            player.start();
            //If the answer was correct, a message is displayed.
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            //Increases the value of the score variable by 1.
            score++;
            //Displays a running score for the user.
            InGameScore.setText("Your score so far is " + score);
        } else {
            player = MediaPlayer.create(MainActivity.this, R.raw.incorrect);
            player.start();
            Toast.makeText(MainActivity.this, "Incorrect!!", Toast.LENGTH_SHORT).show();
        }
        setUpQuestion();
    }
    //End Game Function.
    private void endGame(){
        Log.d("James_App", "Reached onResume");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Sets a new title on screen.
        builder.setTitle("Congratulations!");
        //Messages the player about their new score and prompts the user to input their name
        builder.setMessage("You scored " + score + " points this round." + "\n\n" + "Please enter your name.");
        //Prohibits the user from clicking outside the alert box.
        builder.setCancelable(false);
        //Creates the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //Creates the "Ok" button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = input.getText().toString();
                //Writes username and score to firebase database
                //myFirebaseRef.child("Username - Score").setValue(userName, score);
                //Creates the new high score with their score, name, and time.
                HighScoreObject highScore = new HighScoreObject(score, userName, new Date().getTime());
                //Acquires user preferences.
                List<HighScoreObject> highScores = Paper.book().read("high scores", new ArrayList<HighScoreObject>());
                //Adds the item.
                highScores.add(highScore);
                //Saves the item again.
                Paper.book().write("high scores", highScores);
                finish();
            }
        })
        .create();
        builder.show();
        Log.d("James_App", "Completed endGame");
    }
}