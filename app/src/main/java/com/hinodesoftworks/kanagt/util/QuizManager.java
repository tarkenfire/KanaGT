package com.hinodesoftworks.kanagt.util;

import android.os.SystemClock;

import java.util.ArrayList;

public class QuizManager {

    public enum QuizMode{MODE_HIRA_P_QUIZ, MODE_HIRA_R_QUIZ, MODE_KATA_P_QUIZ, MODE_KATA_R_QUIZ}

    private QuizListener mListener;
    private QuizMode mQuizMode;
    private ArrayList<Question> mQuestions;
    private boolean isQuizSetup = false;
    private int questionCounter = 0;
    private int maxQuestions = 0;
    private int correct = 0, incorrect = 0;

    long startTime = 0, endTime = 0;
    long timeTaken;


    public QuizManager(){

    }

    public void setListener(QuizListener listener){
        this.mListener = listener;
    }

    //quiz methods
    public void setupQuiz(ArrayList<Question> questions, int maxQuestions, QuizMode mode){
        this.mQuestions = questions;
        this.maxQuestions = maxQuestions;
        this.mQuizMode = mode;

        this.isQuizSetup = true;
    }

    public void startQuiz(){
        if (!isQuizSetup) return;

        Question question = mQuestions.get(questionCounter);

        mListener.onQuizStarted(question.getmRightAnswer(), question.getmDisplayAnswer(),
                            question.getmWrongAnswers());

        startTime = SystemClock.elapsedRealtime();
    }

    public void checkAnswer(String answer){
        Question currentQuestion = mQuestions.get(questionCounter);
        String check = currentQuestion.getmRightAnswer();
        currentQuestion.setUserAnswer(answer);

        //check answer
        if (check.matches(answer)){
            correct++;
            currentQuestion.setIsCorrect(true);
        }
        else{
            incorrect++;
            currentQuestion.setIsCorrect(false);
        }

        if (++questionCounter != maxQuestions){
            currentQuestion = mQuestions.get(questionCounter);
            mListener.onNextQuestion(currentQuestion.getmRightAnswer(),
                            currentQuestion.getmDisplayAnswer(), currentQuestion.getmWrongAnswers());
        }
        else{
            quizEnded();
        }
    }

    private void quizEnded(){
        endTime = SystemClock.elapsedRealtime();

        timeTaken = endTime - startTime;

        mListener.onQuizEnded(correct, incorrect, timeTaken);
    }

    //accessors
    public ArrayList<Question> getmQuestions() {
        return mQuestions;
    }

    public QuizMode getmQuizMode() {
        return mQuizMode;
    }

    public int[] getScore(){
        int[] holder = new int[2];
        holder[0] = correct;
        holder[1] = incorrect;
        return holder;
    }

    public int getCurrentQuestionNumber(){
        return questionCounter + 1;
    }

    public int getMaxQuestionNumber(){
        return maxQuestions;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    //interface
    public interface QuizListener{
        public void onQuizStarted(String right, String rightDisplay, String[] wrongs);
        public void onNextQuestion(String right, String rightDisplay, String[] wrongs);
        public void onQuizEnded(int correct, int incorrect, long timeTaken);
    }

}
