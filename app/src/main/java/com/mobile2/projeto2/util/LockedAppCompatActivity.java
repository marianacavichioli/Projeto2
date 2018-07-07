package com.mobile2.projeto2.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.feedback.FeedbackActivity;
import com.mobile2.projeto2.activities.screenlock.LockActivity;
import com.mobile2.projeto2.activities.screenlock.NewPinActivity;
import com.mobile2.projeto2.util.Password;

public abstract class LockedAppCompatActivity extends AppCompatActivity {

    public static final int LOCK_REQUEST_CODE = 666;
    public static final int NOT_UNLOCKED = 222;
    public final static int UNLOCKED = 19203;
    protected Toaster toaster;

    String mPIN = Password.getPIN();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toaster = new Toaster(this);

        if (mPIN.isEmpty() && getCallingActivity() == null) {
            Intent newPin = new Intent(this, NewPinActivity.class);
            startActivityForResult(newPin, LOCK_REQUEST_CODE);
        } else if (!mPIN.isEmpty()) {
            Intent lock = new Intent(this, LockActivity.class);
            startActivityForResult(lock, LOCK_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCK_REQUEST_CODE && resultCode == NOT_UNLOCKED) {
            finish();
        }
    }
}
