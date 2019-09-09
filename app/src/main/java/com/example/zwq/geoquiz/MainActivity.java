package com.example.zwq.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT=0;
    private int correctAnswer=0;
    private int answerLength=0;
    private boolean mIsCheat;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_australia,true,0),
            new Question(R.string.question_africa,true,0),
            new Question(R.string.question_americas,true,0),
            new Question(R.string.question_asia,true,0),
            new Question(R.string.question_mideast,true,0),
            new Question(R.string.question_oceans,true,0)
    };
    private int mCurrentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate(Bundle)called");
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            int[] answerList =savedInstanceState.getIntArray(KEY_INDEX);
            for(int i=0;i<mQuestionBank.length;i++){
                mQuestionBank[i].setIsAnswered(answerList[i]);
            }

        }

        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this,answerTrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

            }
        });
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheat=false;
                updateQuestion();
                answerLength++;
                if(answerLength==mQuestionBank.length){
                    double j=correctAnswer/mQuestionBank.length;
                    double score=j*100;
                    Toast.makeText(MainActivity.this,"score="+score+"%",Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateQuestion();
        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheat=false;
                mCurrentIndex = (mCurrentIndex-1)%mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                answerLength--;
                if(answerLength==mQuestionBank.length){
                    double j=correctAnswer/mQuestionBank.length;
                    double score=j*100;
                    Toast.makeText(MainActivity.this,"score="+score+"%",Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent date){
        if(resultCode!=Activity.RESULT_OK){return;}
        if(requestCode==REQUEST_CODE_CHEAT){
            if(date==null){
                return;
            }
        }
        mIsCheat=CheatActivity.wasAnswerShow(date);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        int[] answerList = new int[mQuestionBank.length];
        for(int i=0 ;i<answerList.length;i++ ){
            answerList[i]=mQuestionBank[i].getIsAnswered();
        }
        savedInstanceState.putIntArray(KEY_INDEX,answerList);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    public void ButtonEnable(){
        if(mQuestionBank[mCurrentIndex].getIsAnswered()!=0){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }


    private void updateQuestion(){
        mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        ButtonEnable();
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId=0;
        if(mIsCheat){
            messageResId=R.string.judgment_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                mQuestionBank[mCurrentIndex].setIsAnswered(1);
                messageResId = R.string.correct_toast;
                correctAnswer++;
            } else {
                mQuestionBank[mCurrentIndex].setIsAnswered(-1);
                messageResId = R.string.incorrect_toast;
            }
        }
            ButtonEnable();
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }

}
