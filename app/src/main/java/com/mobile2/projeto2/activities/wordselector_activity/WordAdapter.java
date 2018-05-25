package com.mobile2.projeto2.activities.wordselector_activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.util.interfaces.ItemSelectedCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> mWordList;
    private ItemSelectedCallback<Word> mCallback;

    public WordAdapter(List<Word> wordList, ItemSelectedCallback<Word> callback) {
        this.mWordList = wordList;
        this.mCallback = callback;
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

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mTextViewWord.setOnClickListener( v -> mCallback.onItemSelected(mWordList.get(getAdapterPosition())));
        }
    }
}
