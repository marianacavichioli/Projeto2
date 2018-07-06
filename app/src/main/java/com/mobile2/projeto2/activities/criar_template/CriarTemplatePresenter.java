package com.mobile2.projeto2.activities.criar_template;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.mobile2.projeto2.entidades.Template;
import com.mobile2.projeto2.entity.Word;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import permissions.dispatcher.NeedsPermission;

public class CriarTemplatePresenter {
    private CriarTemplateView view;
    private Context context;

    public CriarTemplatePresenter(CriarTemplateView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public Template getTemplate(String caminhoFoto) {
        Template template = null;

        if(!caminhoFoto.isEmpty()) {
            template = new Template();
            template.setCaminhoFoto(caminhoFoto);
        }

        return template;
    }


    public void escreveAsImagens(Bitmap bmp, String caminhoFoto) {
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

    public List<String> validar(Boolean fotoAnexada, String palavra) {
        List<String> mensagens = new ArrayList<String>();
        if (!fotoAnexada) {
            mensagens.add("Uma foto deve ser adicionada");
        }
        if (palavra.trim().length() == 0) {
            mensagens.add("Uma palavra deve ser preenchida");
        }
        if (palavra.trim().length() > 19) {
            mensagens.add("Uma palavra deve conter no máximo 19 letras.");
        }
        if (palavra.matches("^[a-zA-Z\\u00C0-\\u00FF/]*$") == false) {
            mensagens.add("Palavra só pode conter letras e barras / ");
        }

        return (mensagens.isEmpty() ? null : mensagens);
    }

}
