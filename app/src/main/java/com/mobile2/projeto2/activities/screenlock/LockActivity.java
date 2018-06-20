package com.mobile2.projeto2.activities.screenlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cesar.andrade on 20/06/18.
 */

public class LockActivity extends AppCompatActivity implements PinLockListener {

    private static final String TAG = "LOCKSCREEN";

    @BindView(R.id.pin_lock_view)
    PinLockView mPinLockView;
    @BindView(R.id.indicator_dots)
    IndicatorDots mIndicatorDots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
        ButterKnife.bind(this);
        mPinLockView.setPinLockListener(this);
        mPinLockView.attachIndicatorDots(mIndicatorDots);
    }

    @Override
    public void onComplete(String pin) {
        Log.d(TAG, "Pin complete: " + pin);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEmpty() {
        Log.d(TAG, "Pin empty");
    }

    @Override
    public void onPinChange(int pinLength, String intermediatePin) {
        Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
    }
}
