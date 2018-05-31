package com.mobile2.projeto2.activities.wordselector_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.criar_template.CriarTemplateActivity;
import com.mobile2.projeto2.activities.syllable_activity.SyllableActivityActivity;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.util.Constans;

import java.util.ArrayList;
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

    private void goToSyllableActivity(List<Word> itemList){
        Intent[] intents = new Intent[itemList.size()];
        for(int i=0;i<itemList.size();i++){
            Intent intent = new Intent(this, SyllableActivityActivity.class);
            intent.putExtra(Constans.EXTRA_WORD_STRING, itemList.get(i).toString());
            intents[i] = intent;
        }
        startActivities(intents);
    }

    @Override
    public void onError(String message) {
        finish();
    }

    @OnClick(R.id.btn_iniciar)
    public void iniciar() {
        if(mAdapter.getSellectedWords().isEmpty()){
            Toast.makeText(WordSelectorActivity.this, "Selecione pelo menos 1 palavra", Toast.LENGTH_SHORT).show();
        }
        else{
            goToSyllableActivity(mAdapter.getSellectedWords());
        }
    }


}
