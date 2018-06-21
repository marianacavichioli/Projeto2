package com.mobile2.projeto2.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.mobile2.projeto2.Project2Application;

public class Toaster {
    private Toast mToast;
    private Context mContext;

    public Toaster(Context context) {
        this.mContext = context;
    }

    public void toast(@StringRes int id) {
        cancelToast();
        mToast = Toast.makeText(mContext, id, Toast.LENGTH_LONG);
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}