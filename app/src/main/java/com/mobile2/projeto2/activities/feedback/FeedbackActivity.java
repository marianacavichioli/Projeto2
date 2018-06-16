package com.mobile2.projeto2.activities.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.wordselector_activity.WordSelectorActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_encerrar)
    public void encerrar(){
        Intent intent = new Intent(this, WordSelectorActivity.class);
        startActivity(intent);
    }

}

