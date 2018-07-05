package com.mobile2.projeto2.activities.wordselector_activity.deletion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.wordselector_activity.WordAdapter;
import com.mobile2.projeto2.entity.Word;

import java.util.ArrayList;
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
        if (mAdapter.hasSelectedWord()) {
            Toast.makeText(this, "Selecione pelo menos 1 palavra", Toast.LENGTH_SHORT).show();
        } else {
            final List<Word> completeDeletion = new ArrayList<>();
            final List<Word> imageOnlyDeletion = new ArrayList<>();
            final List<Word> videoOnlyDeletion = new ArrayList<>();

            imageOnlyDeletion.addAll(mAdapter.getSellectedWordsForImage());
            videoOnlyDeletion.addAll(mAdapter.getSellectedWordsForVideo());
            completeDeletion.addAll(mAdapter.getSellectedWordsForDeletion());
            if(completeDeletion.isEmpty() && imageOnlyDeletion.isEmpty() && videoOnlyDeletion.isEmpty()){
                Toast.makeText(this, "Selecione pelo menos 1 palavra", Toast.LENGTH_SHORT).show();
            }
            else {
                imageOnlyDeletion.removeAll(completeDeletion);
                videoOnlyDeletion.removeAll(completeDeletion);

                mPresenter.deleteImageWords(imageOnlyDeletion);
                mPresenter.deleteVideoWords(videoOnlyDeletion);
                mPresenter.deleteWords(completeDeletion);

                Toast.makeText(this, "Deletado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
