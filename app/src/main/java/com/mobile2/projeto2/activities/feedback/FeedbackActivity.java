package com.mobile2.projeto2.activities.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.wordselector_activity.WordSelectorActivity;
import com.mobile2.projeto2.util.ActivitiesFeedback;
import com.mobile2.projeto2.util.Feedback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends AppCompatActivity{

    @BindView(R.id.tentativas)
    public TextView tentativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        List<Feedback> feedbacks = ActivitiesFeedback.getFeedbacks();
        String stringFeedback = "";

        ButterKnife.bind(this);

        for(int i = 0; i< feedbacks.size(); i++){
            if (feedbacks.get(i).getType().toString() == "IMAGE"){
                stringFeedback += "Imagem ";
            }
            else{
                stringFeedback += "Vídeo ";
            }
            stringFeedback += feedbacks.get(i).getWord() + ": " + feedbacks.get(i).getMissCounter() + "\n";
        }
        tentativas.setText(stringFeedback);
    }



    @OnClick(R.id.btn_encerrar)
    public void encerrar(){
        ActivitiesFeedback.cleanFeedbacks();
        Intent intent = new Intent(this, WordSelectorActivity.class);
        startActivity(intent);
    }

}

