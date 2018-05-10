package com.mobile2.projeto2.activities.SyllableActivity;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.entity.data.WordData;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.sackcentury.shinebuttonlib.ShineButton;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by cesar on 5/5/2018.
 */

public class SyllableActivityActivity extends Activity implements SyllableActivityInterface.View {

    ExplosionField mExplosionField;
    KonfettiView mKonfettiView;

    GridLayout mSyllableButtonsContainer;
    ImageView mImageView;
    LinearLayout mSyllableAnswerContainer;

    SyllableActivityInterface.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_one_exec);

        mSyllableButtonsContainer = findViewById(R.id.activity_templateOne_exec_syllableSelector);
        mImageView = findViewById(R.id.activity_templateOne_exec_image);
        mSyllableAnswerContainer = findViewById(R.id.activity_templateOne_exec_answer);
        mKonfettiView = findViewById(R.id.viewKonfetti);
        mExplosionField = ExplosionField.attach2Window(this);

        mPresenter = new SyllableActivityPresenter(this);
    }

    @Override
    public void addRightButton(Syllable syllable, int indexInTheWord) {
        final Button button = generateButton(syllable.toString());
        button.setOnClickListener(view -> {
            blowKonfetti(view);
            mSyllableAnswerContainer.getChildAt(indexInTheWord).callOnClick();
            view.setVisibility(View.INVISIBLE);
            view.setClickable(false);
        });
        mSyllableButtonsContainer.addView(button);
    }

    private Button generateButton(String s) {
        Button button = new Button(this);
        button.setText(s.toUpperCase());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(32, 32, 32, 32);
        button.setLayoutParams(layoutParams);
        return button;
    }


    private void blowKonfetti(View view) {
        int [] position = new int [2];
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
        });
        mSyllableButtonsContainer.addView(button);
    }

    @Override
    public void setWord(Word word) {
        for (Syllable syllable : word.getSyllables()) {
            mSyllableAnswerContainer.addView(generateShineAnswer(syllable.toString()));
        }
    }

    private ShineButton generateShineAnswer(String s) {
        ShineButton button = new ShineButton(this);
        button.setBtnColor(Color.GRAY);
        button.setBtnFillColor(Color.RED);
        button.setAllowRandomColor(true);
        button.setShapeResource(getResources().getIdentifier("syl_" + s.toLowerCase(), "raw", this.getPackageName()));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
        layoutParams.setMargins(8, 32, 8, 32);
        button.setLayoutParams(layoutParams);
        button.setClickable(false);
        return button;
    }

    @Override
    public void setImage(Uri uri) {

    }

    @Override
    public void onError(String message) {

    }
}
