package com.mobile2.projeto2.activities.wordselector_activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.util.interfaces.ItemSelectedCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> mWordList;
    private List<Word> sellectedWords = new ArrayList<>();

    public WordAdapter(List<Word> wordList) {
        this.mWordList = wordList;
        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);

        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.mTextViewWord.setText(mWordList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mWordList != null ? mWordList.size() : 0;
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_word)
        TextView mTextViewWord;

        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            checkBox.setOnClickListener( v -> {
                if (checkBox.isChecked()){
                    sellectedWords.add(mWordList.get(getAdapterPosition()));
                    Log.d("LISTA DE SELECIONADOS", "adicionando");
                }
                else{
                    sellectedWords.remove(mWordList.get(getAdapterPosition()));
                    Log.d("LISTA DE SELECIONADOS", "removendo");
                }
            });
        }
    }
}
