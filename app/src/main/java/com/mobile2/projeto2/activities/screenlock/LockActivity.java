package com.mobile2.projeto2.activities.screenlock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.bumptech.glide.Glide;
import com.mobile2.projeto2.R;
import com.mobile2.projeto2.util.LockedAppCompatActivity;
import com.mobile2.projeto2.util.Password;
import com.mobile2.projeto2.util.Toaster;
import com.mobile2.projeto2.util.UIHidedAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cesar.andrade on 20/06/18.
 */

public class LockActivity extends UIHidedAppCompatActivity implements PinLockListener {

    private static final String TAG = "LOCKSCREEN";

    @BindView(R.id.pin_lock_view)
    PinLockView mPinLockView;
    @BindView(R.id.indicator_dots)
    IndicatorDots mIndicatorDots;
    @BindView(R.id.gif)
    ImageView mGifImageView;

    String mPIN = Password.getPIN();
    Toaster mToaster = new Toaster(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(R.drawable.entregar_celuluar)
                .into(mGifImageView);

        mPinLockView.setPinLockListener(this);
        mPinLockView.setPinLength(4);
        mIndicatorDots.setPinLength(mPinLockView.getPinLength());
        mPinLockView.attachIndicatorDots(mIndicatorDots);
    }

    @Override
    public void onComplete(String pin) {
        Log.d(TAG, "Pin complete: " + pin);
        if (pin.equals(mPIN)) {
            setResult(LockedAppCompatActivity.UNLOCKED);
            finish();
        } else {
            mPinLockView.resetPinLockView();
            mToaster.toast(R.string.incorrect_password);
        }
    }

    @Override
    public void onEmpty() {
        Log.d(TAG, "Pin empty");
    }

    @Override
    public void onPinChange(int pinLength, String intermediatePin) {
        Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
    }

    @Override
    public void onBackPressed() {
        setResult(LockedAppCompatActivity.NOT_UNLOCKED);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        mToaster.cancelToast();
    }
}
