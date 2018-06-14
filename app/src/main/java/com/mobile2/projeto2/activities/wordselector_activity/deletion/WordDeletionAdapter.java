package com.mobile2.projeto2.activities.wordselector_activity.deletion;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Word;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordDeletionAdapter extends RecyclerView.Adapter<WordDeletionAdapter.WordViewHolder> {

    private List<Word> mWordList;
    private List<Word> sellectedWordsForDeletion = new ArrayList<>();

    public WordDeletionAdapter(List<Word> wordList) {
        this.mWordList = wordList;
        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public WordDeletionAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);

        return new WordDeletionAdapter.WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordDeletionAdapter.WordViewHolder holder, int position) {
        Word word = mWordList.get(position);

        holder.mTextViewWord.setText(word.toString());
        holder.deletionCheckbox.setVisibility(View.VISIBLE);
        holder.deletionCheckbox.setClickable(true);
    }

    private boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }

    public List<Word> getSellectedWordsForDeletion() {
        return sellectedWordsForDeletion;
    }

    @Override
    public int getItemCount() {
        return mWordList != null ? mWordList.size() : 0;
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_word)
        TextView mTextViewWord;
        @BindView(R.id.checkBoxImage)
        CheckBox deletionCheckbox;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            deletionCheckbox.setOnClickListener(v -> {
                if (deletionCheckbox.isChecked()) {
                    sellectedWordsForDeletion.add(mWordList.get(getAdapterPosition()));
                } else {
                    sellectedWordsForDeletion.remove(mWordList.get(getAdapterPosition()));
                }
            });
        }
    }
}