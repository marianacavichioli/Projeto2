package com.mobile2.projeto2.ui.customelements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.mobile2.projeto2.SyllableGameListener;
import com.mobile2.projeto2.util.SyllableList;
import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.ogaclejapan.arclayout.ArcLayout;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by cesar on 4/22/2018.
 */

public class SyllableSelectorLayout extends ArcLayout {

    private ExplosionField mExplosionField;
    private List<Syllable> mFillItems = new ArrayList<>();
    private Queue<Syllable> mCorrectSyllables = new ArrayDeque<>();
    private SyllableGameListener gameListener;

    public SyllableSelectorLayout(Context context) {
        super(context);
        mExplosionField = ExplosionField.attach2Window((Activity) context);
    }

    public SyllableSelectorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mExplosionField = ExplosionField.attach2Window((Activity) context);

    }

    public SyllableSelectorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mExplosionField = ExplosionField.attach2Window((Activity) context);

    }

    public SyllableSelectorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mExplosionField = ExplosionField.attach2Window((Activity) context);
    }

    public void setGameListener(SyllableGameListener gameListener) {
        this.gameListener = gameListener;
    }

    public void setup(Word word, int extraItems){
        for (Syllable syllable : word.getSyllables()) {
            mCorrectSyllables.add(syllable);
            generateCorrectShineButton(syllable);
        }

        int finalSize = this.getChildCount() + extraItems;
        while (this.getChildCount() < finalSize) {
            tryToInsertRandomSyllable();
        }
    }

    private ShineButton generateCorrectShineButton(final Syllable syllable) {
        ShineButton button = generateButton(syllable.toString());
        button.setOnClickListener(view -> {
            if (mCorrectSyllables.peek().equals(syllable)) {
                mCorrectSyllables.poll();
                view.setClickable(false);
                gameListener.onHitTheRightSyllable(syllable);
                if (mCorrectSyllables.isEmpty()) {
                    gameListener.onGameWon();
                }
            } else {
                ((ShineButton) view).setChecked(false);
            }
        });
        this.addView(button);
        return button;
    }

    private ShineButton generateButton(String s) {
        ShineButton button = new ShineButton(getContext());
        button.setBtnColor(Color.GRAY);
        button.setBtnFillColor(Color.RED);
        button.setAllowRandomColor(true);
        button.setShapeResource(getResources().getIdentifier("syl_" + s.toLowerCase(),"raw", getContext().getPackageName()));
        LayoutParams layoutParams = new LayoutParams(100, 100);
        button.setLayoutParams(layoutParams);
        return button;
    }

    private void tryToInsertRandomSyllable() {
        Syllable syllable = SyllableList.getRandomSyllable();
        if (mCorrectSyllables.contains(syllable) || mFillItems.contains(syllable)) {
            return;
        }
        mFillItems.add(syllable);
        generateWrongShineButton(syllable.toString());
    }

    private ShineButton generateWrongShineButton(String s) {
        ShineButton button = generateButton(s);
        button.setAnimDuration(0);
        button.setOnClickListener(view -> {
            mExplosionField.explode(view);
            button.setClickable(false);
        });
        this.addView(button);
        return button;
    }
}
