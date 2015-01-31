package com.hinodesoftworks.kanagt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class QuizActivity extends ActionBarActivity implements View.OnClickListener {

    //todo: dummy data
    private int correct, incorrect;
    private Button button1, button2, button3, button4;
    private TextView display;
    private String[] quests = {"あ", "か", "れ", "に", "ち"};
    private String[] rights = {"a", "ka", "re", "ni", "chi"};
    private String[] wrongs1 = {"fu", "mu", "ja", "ri", "mo"};
    private String[] wrongs2 = {"shi", "pu", "ba", "ta", "to"};
    private String[] wrongs3 = {"ya", "e", "wo", "nu", "se"};
    private int qCounter= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.q_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //todo: dummy code
        button1 = (Button)findViewById(R.id.quiz_button_1);
        button2 = (Button)findViewById(R.id.quiz_button_2);
        button3 = (Button)findViewById(R.id.quiz_button_3);
        button4 = (Button)findViewById(R.id.quiz_button_4);

        display = (TextView)findViewById(R.id.quiz_question_display);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //set first question, hard coded
        display.setText(quests[0]);
        button1.setText("ma");
        button2.setText(rights[0]);
        button3.setText("i");
        button4.setText("ke");
    }

    @Override
    public void onClick(View v) {
        if (qCounter >=5) return;

        Button b = (Button)v;
        checkAnswer(b.getText().toString());
    }

    public void checkAnswer(String answer){
        if (answer.matches(rights[qCounter])){
            correct++;
        }
        else{
            incorrect++;
        }

        nextQuestion();
    }

    public void nextQuestion(){
        qCounter++;

        if (qCounter >= 5){
            endQuiz();
            return;
        }

        display.setText(quests[qCounter]);

        Random r = new Random();
        int correctLoc = r.nextInt(4) + 1;

        switch (correctLoc){
            case 1:
                button1.setText(rights[qCounter]);
                button2.setText(wrongs1[r.nextInt(5)]);
                button3.setText(wrongs2[r.nextInt(5)]);
                button4.setText(wrongs3[r.nextInt(5)]);
                break;
            case 2:
                button1.setText(wrongs1[r.nextInt(5)]);
                button2.setText(rights[qCounter]);
                button3.setText(wrongs2[r.nextInt(5)]);
                button4.setText(wrongs3[r.nextInt(5)]);
                break;
            case 3:
                button1.setText(wrongs1[r.nextInt(5)]);
                button2.setText(wrongs2[r.nextInt(5)]);
                button3.setText(rights[qCounter]);
                button4.setText(wrongs3[r.nextInt(5)]);
                break;
            case 4:
                button1.setText(wrongs1[r.nextInt(5)]);
                button2.setText(wrongs2[r.nextInt(5)]);
                button3.setText(wrongs3[r.nextInt(5)]);
                button4.setText(rights[qCounter]);
                break;
        }

    }

    public void endQuiz(){
        Toast.makeText(this, correct + " correct, " + incorrect + " incorrect. This will be a full dialog in the alpha",
                Toast.LENGTH_LONG).show();

        this.finish();
    }


}
