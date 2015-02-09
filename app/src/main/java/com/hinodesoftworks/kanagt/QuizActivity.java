package com.hinodesoftworks.kanagt;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
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

import com.hinodesoftworks.kanagt.util.DatabaseManager;
import com.hinodesoftworks.kanagt.util.QuizManager;

import java.util.ArrayList;
import java.util.Random;


public class QuizActivity extends ActionBarActivity implements View.OnClickListener,
        QuizManager.QuizListener{

    private Button button1, button2, button3, button4;
    private TextView display;

    private DatabaseManager mDatabaseManager;
    private QuizManager mQuizManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.q_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDatabaseManager = new DatabaseManager(this);
        mQuizManager = new QuizManager();
        mQuizManager.setListener(this);


        button1 = (Button)findViewById(R.id.quiz_button_1);
        button2 = (Button)findViewById(R.id.quiz_button_2);
        button3 = (Button)findViewById(R.id.quiz_button_3);
        button4 = (Button)findViewById(R.id.quiz_button_4);

        display = (TextView)findViewById(R.id.quiz_question_display);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //TODO: make dynamic

        Intent args = this.getIntent();
        QuizManager.QuizMode mode = (QuizManager.QuizMode)args.getSerializableExtra("mode");
        String table = "hiragana";

        switch (mode){
            case MODE_HIRA_P_QUIZ:
                table="hiragana";
                break;
            case MODE_HIRA_R_QUIZ:
                table="hiragana";
                break;
            case MODE_KATA_P_QUIZ:
                table="katakana";
                break;
            case MODE_KATA_R_QUIZ:
                table="katakana";
                break;
        }

        //get right answers
        String[] rDisplay = new String[5];
        String[] rAnswer = new String[5];

        Cursor rightCursor = mDatabaseManager.getQuestionSet(table, 5);
        rightCursor.moveToFirst();
        rDisplay[0] = rightCursor.getString(0);
        rAnswer[0] = rightCursor.getString(1);

        while(rightCursor.moveToNext()){
            rDisplay[rightCursor.getPosition()] = rightCursor.getString(0);
            rAnswer[rightCursor.getPosition()] = rightCursor.getString(1);
        }

        rightCursor.close();

        //get wrong answer sets
        ArrayList<String[]> wrongs = new ArrayList<>();

        for (String kana : rDisplay){
            String[] wHolder = new String[3];
            Cursor wrongCursor = mDatabaseManager.getWrongAnswerRomanSet(table, 3, kana);
            wrongCursor.moveToFirst();

            wHolder[0] = wrongCursor.getString(0);
            while (wrongCursor.moveToNext()){
                wHolder[wrongCursor.getPosition()] = wrongCursor.getString(0);
            }
            wrongCursor.close();

            wrongs.add(wHolder);
        }

        mQuizManager.setupQuiz(rDisplay, rAnswer, wrongs, 5);
        mQuizManager.startQuiz();
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        mQuizManager.checkAnswer(b.getText().toString());
    }

    //quiz manager callbacks
    @Override
    public void onQuizStarted(String right, String rightDisplay,  String[] wrongs) {
        display.setText(rightDisplay);

        Random r = new Random();
        int place = r.nextInt(4);

        switch (place){
            case 0:
                button1.setText(right);
                button2.setText(wrongs[0]);
                button3.setText(wrongs[1]);
                button4.setText(wrongs[2]);
                break;
            case 1:
                button1.setText(wrongs[0]);
                button2.setText(right);
                button3.setText(wrongs[1]);
                button4.setText(wrongs[2]);
                break;
            case 2:
                button1.setText(wrongs[0]);
                button2.setText(wrongs[1]);
                button3.setText(right);
                button4.setText(wrongs[2]);
                break;
            case 3:
                button1.setText(wrongs[0]);
                button2.setText(wrongs[1]);
                button3.setText(wrongs[2]);
                button4.setText(right);
                break;
        }
    }

    @Override
    public void onNextQuestion(String right, String rightDisplay, String[] wrongs) {
        display.setText(rightDisplay);

        Random r = new Random();
        int place = r.nextInt(4);

        switch (place){
            case 0:
                button1.setText(right);
                button2.setText(wrongs[0]);
                button3.setText(wrongs[1]);
                button4.setText(wrongs[2]);
                break;
            case 1:
                button1.setText(wrongs[0]);
                button2.setText(right);
                button3.setText(wrongs[1]);
                button4.setText(wrongs[2]);
                break;
            case 2:
                button1.setText(wrongs[0]);
                button2.setText(wrongs[2]);
                button3.setText(right);
                button4.setText(wrongs[2]);
                break;
            case 3:
                button1.setText(wrongs[0]);
                button2.setText(wrongs[1]);
                button3.setText(wrongs[2]);
                button4.setText(right);
                break;
        }
    }

    @Override
    public void onQuizEnded(int correct, int incorrect) {

        Toast.makeText(this, "Correct: " + correct + " Incorrect: " + incorrect, Toast.LENGTH_LONG).show();
        this.finish();


    }
}
