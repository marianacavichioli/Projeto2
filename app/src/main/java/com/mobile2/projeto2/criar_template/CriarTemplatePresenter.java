package com.mobile2.projeto2.criar_template;

import com.mobile2.projeto2.entidades.Template;

public class CriarTemplatePresenter {
    private CriarTemplateView view;

    public CriarTemplatePresenter(CriarTemplateView view) {
        this.view = view;
    }

    public Template getTemplate(String maximo, String minimo, String caminhoFoto) {
        Template template = null;

        if(!maximo.isEmpty() && !minimo.isEmpty()) {
            template = new Template();
            template.setMaximo(maximo);
            template.setMinimo(minimo);
            template.setCaminhoFoto(caminhoFoto);
        }

        return template;
    }
}
