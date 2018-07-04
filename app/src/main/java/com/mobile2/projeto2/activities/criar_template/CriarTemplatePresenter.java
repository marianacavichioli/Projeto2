package com.mobile2.projeto2.activities.criar_template;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.mobile2.projeto2.entidades.Template;
import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CriarTemplatePresenter implements CriarTemplateInterface.Presenter {
    private CriarTemplateInterface.View view;


    public CriarTemplatePresenter(CriarTemplateInterface.View view) {
        this.view = view;
    }

    @Override
    public void copyImageToAppFolder(Bitmap bmp, String caminhoFoto) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            FileOutputStream fos = new FileOutputStream(caminhoFoto);
            fos.write(stream.toByteArray());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> validateFields(boolean fotoAnexada, List<String> silabas) {
        List<String> mensagens = new ArrayList<String>();
        if (!fotoAnexada) {
            mensagens.add("Uma foto deve ser adicionada");
        }
        if (silabas.isEmpty()) {
            mensagens.add("Uma palavra deve ser preenchida");
        }

        return (mensagens.isEmpty() ? null : mensagens);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSyllables() {
        GeneralRepository.getAllSyllables()
                .map(list -> {
                    List<String> stringList = new ArrayList<>();
                    for (Syllable syllable : list) {
                        stringList.add(syllable.toString().toUpperCase());
                    }
                    return stringList;
                }).subscribe(strings -> view.setSyllableList(strings), throwable -> {
            throwable.printStackTrace();
            view.onError(throwable.getMessage());
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveWord(Word word) {
        GeneralRepository.saveWord(word)
                .subscribe(view::onWordSaved, throwable -> {
                    throwable.printStackTrace();
                    view.onError(throwable.getMessage());
                });
    }
}
