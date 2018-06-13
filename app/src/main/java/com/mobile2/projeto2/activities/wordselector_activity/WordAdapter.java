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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> mWordList;
    private List<Word> sellectedWordsForImage = new ArrayList<>();
    private List<Word> sellectedWordsForVideo = new ArrayList<>();

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
        Word word = mWordList.get(position);

        holder.mTextViewWord.setText(word.toString());
        if (isValid(word.getImageFilePath())) {
            holder.imageCheckBox.setVisibility(View.VISIBLE);
            holder.imageCheckBox.setClickable(true);
        }
        if (isValid(word.getVideoFilePath())) {
            holder.videoCheckBox.setVisibility(View.VISIBLE);
            holder.videoCheckBox.setClickable(true);
        }
    }

    private boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }

    public List<Word> getSellectedWordsForImage() {
        return sellectedWordsForImage;
    }

    public List<Word> getSellectedWordsForVideo() {
        return sellectedWordsForVideo;
    }

    @Override
    public int getItemCount() {
        return mWordList != null ? mWordList.size() : 0;
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_word)
        TextView mTextViewWord;
        @BindView(R.id.checkBoxImage)
        CheckBox imageCheckBox;
        @BindView(R.id.checkBoxVideo)
        CheckBox videoCheckBox;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imageCheckBox.setOnClickListener(v -> {
                if (imageCheckBox.isChecked()){
                    sellectedWordsForImage.add(mWordList.get(getAdapterPosition()));
                }
                else{
                    sellectedWordsForImage.remove(mWordList.get(getAdapterPosition()));
                }
            });
            videoCheckBox.setOnClickListener(v -> {
                if (videoCheckBox.isChecked()){
                    sellectedWordsForVideo.add(mWordList.get(getAdapterPosition()));
                }
                else{
                    sellectedWordsForVideo.remove(mWordList.get(getAdapterPosition()));
                }
            });
        }
    }
}
