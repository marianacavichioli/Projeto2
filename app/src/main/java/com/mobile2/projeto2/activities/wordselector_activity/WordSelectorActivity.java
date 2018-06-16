package com.mobile2.projeto2.activities.wordselector_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.feedback.FeedbackActivity;
import com.mobile2.projeto2.activities.syllable_activity.SyllableActivityActivity;
import com.mobile2.projeto2.activities.video_activity.VideoActivity;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.util.Constans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordSelectorActivity extends AppCompatActivity implements WordSelectorInterface.View {

    WordSelectorInterface.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;
    WordAdapter mAdapter;
    List<Intent> finalActivitiesList = new ArrayList<>();
    List<Intent> activitiesList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordselector);
        ButterKnife.bind(this);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPresenter = new WordSelectorPresenter(this);
        mPresenter.fetchWordList();
    }

    @Override
    public void setWordList(List<Word> wordList) {
        mAdapter = new WordAdapter(wordList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void goToActivities() {
        List<Word> sellectedWordsForImage = mAdapter.getSellectedWordsForImage();
        List<Word> sellectedWordsForVideo = mAdapter.getSellectedWordsForVideo();

        generateIntentsForImage(sellectedWordsForImage);
        generateIntentsForVideo(sellectedWordsForVideo);
        Collections.shuffle(activitiesList);

        Intent intentFeedback = new Intent(this,FeedbackActivity.class);
        finalActivitiesList.add(intentFeedback);

        finalActivitiesList.addAll(activitiesList);

        startActivities(finalActivitiesList.toArray(new Intent[0]));
        activitiesList.clear();
        finalActivitiesList.clear();
    }

    private void generateIntentsForImage(List<Word> sellectedWordsForImage) {
        for (int i = 0; i < sellectedWordsForImage.size(); i++) {
            Intent intent = new Intent(this, SyllableActivityActivity.class);
            intent.putExtra(Constans.EXTRA_WORD_STRING, sellectedWordsForImage.get(i).toString());
            activitiesList.add(intent);
        }
    }

    private void generateIntentsForVideo(List<Word> sellectedWordsForVideo) {
        for (int i = 0; i < sellectedWordsForVideo.size(); i++) {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra(Constans.EXTRA_WORD_STRING, sellectedWordsForVideo.get(i).toString());
            activitiesList.add(intent);
        }
    }

    @Override
    public void onError(String message) {
        finish();
    }

    @OnClick(R.id.btn_iniciar)
    public void iniciar() {
        if (mAdapter.getSellectedWordsForImage().isEmpty() && mAdapter.getSellectedWordsForVideo().isEmpty()) {
            Toast.makeText(WordSelectorActivity.this, "Selecione pelo menos 1 palavra", Toast.LENGTH_SHORT).show();
        } else {
            goToActivities();
        }
    }


}
