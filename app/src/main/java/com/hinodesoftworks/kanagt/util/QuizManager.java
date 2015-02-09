package com.hinodesoftworks.kanagt.util;

import java.util.ArrayList;

public class QuizManager {

    public enum QuizMode{MODE_HIRA_P_QUIZ, MODE_HIRA_R_QUIZ, MODE_KATA_P_QUIZ, MODE_KATA_R_QUIZ}


    private QuizListener mListener;
    private String[] rights;
    private String[] displayRights;
    private ArrayList<String[]> wrongs;
    private boolean isQuizSetup = false;
    private int questionCounter = 0;
    private int maxQuestions = 0;
    private int correct = 0, incorrect = 0;


    public QuizManager(){

    }

    public void setListener(QuizListener listener){
        this.mListener = listener;
    }

    //quiz methods - called from hosting activity
    public void setupQuiz(String[] displayRights, String[] rights, ArrayList<String[]> wrongs,
                          int maxQuestions){
        this.displayRights = displayRights;
        this.rights = rights;
        this.wrongs = wrongs;
        this.maxQuestions = maxQuestions;

        isQuizSetup = true;
    }

    public void startQuiz(){
        if (!isQuizSetup) return;

        mListener.onQuizStarted(rights[questionCounter], displayRights[questionCounter], wrongs.get(questionCounter));

    }

    public void checkAnswer(String answer){
        String check = rights[questionCounter];

        //check answer
        if (check.matches(answer))
            correct++;
        else
            incorrect++;

        if (++questionCounter != maxQuestions){
            mListener.onNextQuestion(rights[questionCounter], displayRights[questionCounter], wrongs.get(questionCounter));
        }
        else{
            mListener.onQuizEnded(correct, incorrect);
        }
    }

    //interface
    public interface QuizListener{
        public void onQuizStarted(String right, String rightDisplay, String[] wrongs);
        public void onNextQuestion(String right, String rightDisplay, String[] wrongs);
        public void onQuizEnded(int correct, int incorrect);
    }

}
