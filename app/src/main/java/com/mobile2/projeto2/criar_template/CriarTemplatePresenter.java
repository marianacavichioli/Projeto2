package com.mobile2.projeto2.criar_template;

import com.mobile2.projeto2.entidades.Template;

public class CriarTemplatePresenter {
    private CriarTemplateView view;

    public CriarTemplatePresenter(CriarTemplateView view) {
        this.view = view;
    }

    public Template getTemplate(String caminhoFoto) {
        Template template = null;

        if(!caminhoFoto.isEmpty()) {
            template = new Template();
            template.setCaminhoFoto(caminhoFoto);
        }

        return template;
    }

    public void addFoto() {
        view.tiraFoto();
    }

    public void acessarGaleria() {
        view.abrirGaleria();
    }
}
