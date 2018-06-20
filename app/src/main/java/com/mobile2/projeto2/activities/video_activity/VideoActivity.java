package com.mobile2.projeto2.activities.video_activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.util.ActivitiesFeedback;
import com.mobile2.projeto2.util.Constans;
import com.mobile2.projeto2.util.Feedback;

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

public class VideoActivity extends AppCompatActivity implements VideoActivityInterface.View {
    ExplosionField mExplosionField;
    @BindView(R.id.viewKonfetti)
    KonfettiView mKonfettiView;
    @BindView(R.id.activity_exec_alternatives)
    LinearLayout mAlternativesButtonsContainer;
    @BindView(R.id.activity_exec_asset)
    VideoView mVideoView;

    VideoActivityInterface.Presenter mPresenter;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String wordString;
    private int missCounter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_two_exec);
        ButterKnife.bind(this);
        mExplosionField = ExplosionField.attach2Window(this);
        mPresenter = new VideoActivityPresenter(this);
        mVideoView.setOnPreparedListener(mp -> mp.setLooping(true));

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
    public void addRightButton(String word) {
        final Button button = generateButton(word);
        button.setOnClickListener(view -> {
            terminateActivity(view);
            view.setClickable(false);
        });
        mAlternativesButtonsContainer.addView(button);
    }

    private void terminateActivity(View view) {
        blowKonfetti(view);
        ActivitiesFeedback.addFeedback(new Feedback(wordString, Constans.ActType.VIDEO, missCounter));
        mCompositeDisposable.add(Completable.complete().delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish));
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


    private void blowKonfetti(android.view.View view) {
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
    public void addWrongButton(String word) {
        Button button = generateButton(word);
        button.setOnClickListener(view -> {
            missCounter++;
            mExplosionField.explode(view);
            button.setClickable(false);
        });
        mAlternativesButtonsContainer.addView(button);
    }

    @Override
    public void setAsset(Uri uri) {
        mVideoView.setVideoURI(uri);
        mVideoView.start();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

}
