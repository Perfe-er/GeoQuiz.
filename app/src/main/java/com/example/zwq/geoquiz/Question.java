package com.example.zwq.geoquiz;

public class Question {
    private int mTextResId;
    private  boolean mAnswerTrue;
    private int mIsAnswered;



    public Question(int textResId, boolean answerTrue, int isAnswered){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsAnswered = isAnswered;
    }

    public int getIsAnswered() {
        return mIsAnswered;
    }

    public void setIsAnswered(int isAnswered) {
        mIsAnswered = isAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
