package com.example.zwq.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.exampe.zwq.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.exampe.zwq.geoquiz.answer_shown";
    private static final String EXTRA_SURPLUS_TEXT = "com.exampe.zwq.geoquiz.surplus_text";
    private static int Surplus_Text = 3;

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mShowApiTextView;

    private static final String FIRST_BUG = "first_bug";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShow(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int version = Integer.valueOf(Build.VERSION.SDK_INT);
        Surplus_Text = getIntent().getIntExtra(EXTRA_SURPLUS_TEXT, 3);

        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                Surplus_Text--;
                setAnswerShowResult(true);
            }
        });

        mShowApiTextView = (TextView) findViewById(R.id.api_level);
        mShowApiTextView.setText("API LEVEL:" + version);

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(FIRST_BUG, false);
            if (mAnswerIsTrue) {
                setAnswerShowResult(mAnswerIsTrue);
            }
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putBoolean(FIRST_BUG, mAnswerIsTrue);

    }

    /**调用setResult(int, Intent)方法将结果代码以及intent打包。
     *
     * @param isAnswerShow 问题给出答案
     */

    public void setAnswerShowResult(boolean isAnswerShow) {
        Intent date = new Intent();
        date.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShow);
        date.putExtra(EXTRA_SURPLUS_TEXT, Surplus_Text);
        setResult(RESULT_OK, date);
    }


}
