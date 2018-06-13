package com.mobile2.projeto2.activities.choosetemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.criar_template.CriarTemplateActivity;
import com.mobile2.projeto2.activities.criar_template_video.CriarTemplateActivityVideo;
import com.mobile2.projeto2.activities.wordselector_activity.WordSelectorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseTemplateActivity extends AppCompatActivity {
    @BindView(R.id.management_create_syllable)
    Button mButtonCreateSyllable;
    @BindView(R.id.management_create_video)
    Button mButtonCreateVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_create);
        ButterKnife.bind(this);

        mButtonCreateSyllable.setOnClickListener(v -> goToActivity(CriarTemplateActivity.class));
        mButtonCreateVideo.setOnClickListener(v -> goToActivity(CriarTemplateActivityVideo.class));
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
