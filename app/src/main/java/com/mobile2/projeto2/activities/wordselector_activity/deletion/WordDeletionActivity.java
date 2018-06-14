package com.mobile2.projeto2.activities.wordselector_activity.deletion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordDeletionActivity extends AppCompatActivity implements WordDeletionInterface.View {
    WordDeletionInterface.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;
    WordDeletionAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordselector_for_deletion);
        ButterKnife.bind(this);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPresenter = new WordDeletionPresenter(this);
        mPresenter.fetchWordList();
    }

    @Override
    public void setWordList(List<Word> wordList) {
        mAdapter = new WordDeletionAdapter(wordList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onWordsDeleted() {
        finish();
    }

    @Override
    public void onError(String message) {
        finish();
    }

    @OnClick(R.id.btn_deletar)
    public void deletar() {
        if (mAdapter.getSellectedWordsForDeletion().isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos 1 palavra", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.deleteWords(mAdapter.getSellectedWordsForDeletion());
        }
    }

}
