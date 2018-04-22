package com.mobile2.projeto2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.ui.customelements.SyllableSelectorLayout;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MainActivity extends AppCompatActivity implements SyllableGameListener{

    SyllableSelectorLayout mSyllableSelector;
    KonfettiView mKonfettiView;

    Word mZebraWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSyllableSelector = findViewById(R.id.arc_layout);
        mSyllableSelector.setGameListener(this);
        mKonfettiView = findViewById(R.id.viewKonfetti);

        mZebraWord = new Word("ZE", "BRA");

        mSyllableSelector.setup(mZebraWord, 2);
    }

    @Override
    public void onHitTheRightSyllable(Syllable syllable) {

    }

    @Override
    public void onGameWon() {
        mKonfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 8f)
                .setFadeOutEnabled(true)
                .setTimeToLive(4000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 0f), new Size(16, 6f))
                .setPosition(mKonfettiView.getX() + mKonfettiView.getWidth() / 2, mKonfettiView.getY() + mKonfettiView.getHeight() / 3)
                .burst(200);
    }
}
