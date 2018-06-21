package com.mobile2.projeto2.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobile2.projeto2.activities.screenlock.LockActivity;

import static com.mobile2.projeto2.util.LockedAppCompatActivity.LOCK_REQUEST_CODE;
import static com.mobile2.projeto2.util.LockedAppCompatActivity.UNLOCKED;

public abstract class LeaveLockedAppCompatActivity extends UIHidedAppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent lock = new Intent(this, LockActivity.class);
        startActivityForResult(lock, LOCK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCK_REQUEST_CODE && resultCode == UNLOCKED) {
            ActivityCompat.finishAffinity(this);
        }
    }
}
