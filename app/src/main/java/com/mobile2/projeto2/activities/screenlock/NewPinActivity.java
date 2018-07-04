package com.mobile2.projeto2.activities.screenlock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.mobile2.projeto2.R;
import com.mobile2.projeto2.util.Constans;
import com.mobile2.projeto2.util.LockedAppCompatActivity;
import com.mobile2.projeto2.util.Password;
import com.mobile2.projeto2.util.Toaster;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewPinActivity extends LockedAppCompatActivity implements PinLockListener {
    private static final String TAG = "LOCKSCREEN";

    @BindView(R.id.pin_lock_view)
    PinLockView mPinLockView;
    @BindView(R.id.indicator_dots)
    IndicatorDots mIndicatorDots;

    String newPin = "";
    Toaster mToaster = new Toaster(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
        ButterKnife.bind(this);
        mPinLockView.setPinLockListener(this);
        mPinLockView.setPinLength(4);
        mIndicatorDots.setPinLength(mPinLockView.getPinLength());
        mPinLockView.attachIndicatorDots(mIndicatorDots);

        if (Password.getPIN().isEmpty()) {
            mToaster.toast(R.string.enter_new_password);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (newPin.isEmpty()) {
            mToaster.toast(R.string.enter_new_password);
        }
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onComplete(String pin) {
        if (newPin.isEmpty()) {
            newPin = pin;
            mToaster.toast(R.string.confirm_password);
            mPinLockView.resetPinLockView();
        } else if (newPin.equals(pin)){
            SharedPreferences sharedPref = getSharedPreferences(Constans.SHAREDPREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constans.PASSWORD_KEY, pin);
            editor.commit();
            Password.setPIN(pin);
            finish();
        } else {
            newPin = "";
            mPinLockView.resetPinLockView();
            mToaster.toast(R.string.password_confirmation_failed);
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
        mToaster.cancelToast();
        super.finish();
    }
}
