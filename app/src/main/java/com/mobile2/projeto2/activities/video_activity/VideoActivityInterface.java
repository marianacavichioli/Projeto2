package com.mobile2.projeto2.activities.video_activity;

import android.net.Uri;

public interface VideoActivityInterface {
    public interface View {
        void addRightButton(String word);

        void addWrongButton(String word);

        void setAsset(Uri uri);

        void onError(String message);
    }

    public interface Presenter {
        void fetchWord(String wordString);
    }
}
