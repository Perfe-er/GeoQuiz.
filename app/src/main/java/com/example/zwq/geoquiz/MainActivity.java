package com.example.zwq.geoquiz;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFlaseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_africa,true),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
            new Question(R.string.question_mideast,true),
            new Question(R.string.question_oceans,true)
    };
    private int mCurrentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuestionTextView = (TextView)findViewById(R.id.question_text) ;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,10);
                toast.show();
            }
        });

        mFlaseButton = (Button)findViewById(R.id.false_button);
        mFlaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(MainActivity.this,R.string.incorrect_toact,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,10);
                toast.show();
            }
        });
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
            }
        });
    }
}
