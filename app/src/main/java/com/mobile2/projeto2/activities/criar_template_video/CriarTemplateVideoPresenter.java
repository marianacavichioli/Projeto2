package com.mobile2.projeto2.activities.criar_template_video;

import com.mobile2.projeto2.entidades.Template;

public class CriarTemplateVideoPresenter {
    private CriarTemplateVideoView view;

    public CriarTemplateVideoPresenter(CriarTemplateVideoView view) {
        this.view = view;
    }

    public Template getTemplate(String caminhoVideo) {
        Template template = null;

        if(!caminhoVideo.isEmpty()) {
            template = new Template();
            template.setCaminhoVideo(caminhoVideo);
        }

        return template;
    }

    public void addVideo() {
        view.tiraVideo();
    }

    public void acessarGaleria() {
        view.abrirGaleria();
    }
}
