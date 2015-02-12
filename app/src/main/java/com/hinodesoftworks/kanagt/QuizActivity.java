package com.hinodesoftworks.kanagt;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
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

import com.hinodesoftworks.kanagt.dialogs.QuizResultsDialog;
import com.hinodesoftworks.kanagt.util.DatabaseManager;
import com.hinodesoftworks.kanagt.util.Question;
import com.hinodesoftworks.kanagt.util.QuizManager;
import com.hinodesoftworks.kanagt.util.QuizManager.QuizMode;

import java.util.ArrayList;
import java.util.Random;


public class QuizActivity extends ActionBarActivity implements View.OnClickListener,
        QuizManager.QuizListener, QuizResultsDialog.OnPostQuizResultsListener{

    private Button button1, button2, button3, button4;
    private TextView display, progressDisplay;

    private DatabaseManager mDatabaseManager;
    private QuizManager mQuizManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //TODO: LOWER DENSITY SCREENS NEED AN ALT LAYOUT

        Toolbar toolbar = (Toolbar) findViewById(R.id.q_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
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
        progressDisplay = (TextView) findViewById(R.id.quiz_status_bar);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //TODO: make dynamic

        Intent args = this.getIntent();
        QuizManager.QuizMode mode = (QuizManager.QuizMode)args.getSerializableExtra("mode");

        //todo: get value from passed intent
        int totalQuestions = 20;

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

        //get array of questions
        ArrayList<Question> questions = new ArrayList<>();

        //get right answers
        String[] rDisplay = new String[totalQuestions];
        String[] rAnswer = new String[totalQuestions];

        Cursor rightCursor = mDatabaseManager.getQuestionSet(table, totalQuestions);
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

        //add to question list
        for (int i = 0; i < totalQuestions; i++){
            questions.add(new Question(rAnswer[i], rDisplay[i], wrongs.get(i)));
        }

        mQuizManager.setupQuiz(questions, totalQuestions, mode);
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

        progressDisplay.setText("Question " + mQuizManager.getCurrentQuestionNumber() +
                                " of " + mQuizManager.getMaxQuestionNumber());

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

        progressDisplay.setText("Question " + mQuizManager.getCurrentQuestionNumber() +
                " of " + mQuizManager.getMaxQuestionNumber());

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
        QuizResultsDialog dialog = new QuizResultsDialog();
        dialog.setup(mQuizManager.getmQuestions(), mQuizManager.getmQuizMode(),
                        mQuizManager.getScore());
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "quizResults");
    }

    @Override
    public void onQuizResultsClosed() {
        //todo: update stats

        QuizManager.QuizMode mode = mQuizManager.getmQuizMode();

        //only update stats when it is a ranking quiz.
        if (mode == QuizMode.MODE_HIRA_R_QUIZ  || mode == QuizMode.MODE_KATA_R_QUIZ){
            ArrayList<Question> answeredQuestions = mQuizManager.getmQuestions();
            String table = mode == QuizMode.MODE_HIRA_R_QUIZ ? "hiragana" : "katakana";

            for (Question q : answeredQuestions){

                try{
                    mDatabaseManager.updateCharacterProf(table, q.getmDisplayAnswer(), q.isCorrect());

                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }




        this.finish();
    }
}
