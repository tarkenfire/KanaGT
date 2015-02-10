package com.hinodesoftworks.kanagt.util;

public class Question {
    private String mRightAnswer;
    private String mDisplayAnswer;
    private String[] mWrongAnswers;
    private boolean isCorrect = false;
    private String userAnswer;

    public Question(String right, String display, String[] wrongs){
        this.mRightAnswer = right;
        this.mDisplayAnswer = display;
        this.mWrongAnswers = wrongs;
    }

    //mutators
    public void setIsCorrect(boolean isCorrect){
        this.isCorrect = isCorrect;
    }
    public void setUserAnswer(String answer){
        this.userAnswer = answer;
    }

    //accessors
    public String getmRightAnswer() {
        return mRightAnswer;
    }

    public String getmDisplayAnswer() {
        return mDisplayAnswer;
    }

    public String[] getmWrongAnswers() {
        return mWrongAnswers;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    @Override
    public String toString(){
        String holder = "Wrongs: ";

        for (String w : mWrongAnswers){
            holder += " " + w;
        }

        return "Right: " + mRightAnswer+ "Display: " + mDisplayAnswer + " " + holder;
    }
}
