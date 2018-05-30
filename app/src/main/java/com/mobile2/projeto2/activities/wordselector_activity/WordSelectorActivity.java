package com.mobile2.projeto2.activities.wordselector_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.syllable_activity.SyllableActivityActivity;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.util.Constans;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSelectorActivity extends AppCompatActivity implements WordSelectorInterface.View {

    WordSelectorInterface.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;
    WordAdapter mAdapter;


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

    private void goToSyllableActivity(Word item) {
        Intent intent = new Intent(this, SyllableActivityActivity.class);
        intent.putExtra(Constans.EXTRA_WORD_STRING, item.toString());
        startActivity(intent);
    }

    @Override
    public void onError(String message) {
        finish();
    }

}
