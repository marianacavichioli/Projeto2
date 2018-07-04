package com.mobile2.projeto2.activities.criar_template;

import android.graphics.Bitmap;

import com.mobile2.projeto2.activities.wordselector_activity.deletion.WordDeletionInterface;
import com.mobile2.projeto2.entity.Word;

import java.util.List;

public interface CriarTemplateInterface {
    interface View {
        void setSyllableList(List<String> syllableList);

        void onWordSaved();

        void onError(String errorMessage);
    }

    interface Presenter {
        void getSyllables();

        void saveWord(Word word);

        void copyImageToAppFolder(Bitmap bmp, String caminhoFoto);

        List<String> validateFields(boolean fotoAnexada, List<String> silabas);
    }

}
