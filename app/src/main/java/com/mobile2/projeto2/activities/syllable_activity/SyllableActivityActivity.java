package com.mobile2.projeto2.activities.syllable_activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.util.ActivitiesFeedback;
import com.mobile2.projeto2.util.Constans;
import com.mobile2.projeto2.util.Feedback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by cesar on 5/5/2018.
 */

public class SyllableActivityActivity extends AppCompatActivity implements SyllableActivityInterface.View {

    ExplosionField mExplosionField;
    @BindView(R.id.viewKonfetti)
    KonfettiView mKonfettiView;
    @BindView(R.id.activity_exec_alternatives)
    GridLayout mSyllableButtonsContainer;
    @BindView(R.id.activity_exec_asset)
    ImageView mImageView;
    @BindView(R.id.activity_exec_answer)
    LinearLayout mSyllableAnswerContainer;

    SyllableActivityInterface.Presenter mPresenter;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    int rightCounter;
    private int totalSyllabes;
    int missCounter = 0;
    private String wordString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_one_exec);
        ButterKnife.bind(this);
        mExplosionField = ExplosionField.attach2Window(this);
        mPresenter = new SyllableActivityPresenter(this);

        Intent extras = getIntent();
        if (extras != null) {
            wordString = extras.getStringExtra(Constans.EXTRA_WORD_STRING);

            if (wordString != null && !wordString.isEmpty()) {
                mPresenter.fetchWord(wordString);
                return;
            }
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void addRightButton(final Syllable syllable) {
        final Button button = generateButton(syllable.toString());
        button.setOnClickListener(view -> {
            blowKonfetti(view);
            showAnswerClicked(syllable);
            if (rightCounter >= totalSyllabes) {
                terminateActivity();
            }
            view.setVisibility(View.INVISIBLE);
            view.setClickable(false);
        });
        mSyllableButtonsContainer.addView(button);
    }

    private void terminateActivity() {
        blowKonfetti(mSyllableAnswerContainer);
        ActivitiesFeedback.addFeedback(new Feedback(wordString, Constans.ActType.IMAGE, missCounter));
        mCompositeDisposable.add(Completable.complete().delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish));
    }

    private void showAnswerClicked(Syllable syllable) {
        for (int i = 0; i < mSyllableAnswerContainer.getChildCount(); i++) {
            TextView textView = mSyllableAnswerContainer.getChildAt(i).findViewById(R.id.text);
            String text = textView.getText().toString();
            if (mSyllableAnswerContainer.getChildAt(i).findViewById(R.id.text).getVisibility() != View.VISIBLE &&
                    text.equalsIgnoreCase(syllable.toString())) {
                textView.setVisibility(View.VISIBLE);
                rightCounter++;
                break;
            }
        }
    }

    private Button generateButton(String s) {
        Button button = new Button(this);
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rounded_corner));
        button.setTextColor(Color.WHITE);
        button.setText(s.toUpperCase());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(32, 32, 32, 32);
        button.setLayoutParams(layoutParams);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        return button;
    }


    private void blowKonfetti(View view) {
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        mKonfettiView.build()
                .addColors(Color.BLUE, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(1000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(6, 0f), new Size(8, 6f))
                .setPosition(position[0] + view.getWidth() / 2, position[1] - view.getHeight() / 2)
                .burst(100);
    }

    @Override
    public void addWrongButton(Syllable syllable) {
        Button button = generateButton(syllable.toString());
        button.setOnClickListener(view -> {
            mExplosionField.explode(view);
            button.setClickable(false);
            missCounter++;
        });
        mSyllableButtonsContainer.addView(button);
    }

    @Override
    public void setWord(Word word) {
        this.totalSyllabes = word.syllableCount();
        for (Syllable syllable : word.getSyllables()) {
            mSyllableAnswerContainer.addView(generateAnswer(syllable.toString()));
        }
    }

    private View generateAnswer(String s) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_syllable_answer, null, false);
        TextView textView = view.findViewById(R.id.text);
        textView.setAllCaps(true);
        textView.setText(s);
        textView.setClickable(false);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        textView.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void setAsset(Uri uri) {
        try {
            Picasso.with(this)
                    .load(uri)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
