package com.mobile2.projeto2.activities.AddActivity;

import com.mobile2.projeto2.entity.Atividade;

public class AddActivityPresenter {
    AddActivityView addActivityView;

    public AddActivityPresenter(AddActivityView addActivityView){
        this.addActivityView = addActivityView;
    }

    public Atividade getAtividade(String nome, String descricao, String tag1,
                                  String tag2, String tag3){
        Atividade atividade = null;

        if (!nome.isEmpty() && !descricao.isEmpty() && (tag1.isEmpty() || tag2.isEmpty() || tag3.isEmpty())){
            atividade = new Atividade();
            atividade.setNomeAtv(nome);
            atividade.setDescAtv(descricao);
            atividade.setTag1(tag1);
            atividade.setTag2(tag2);
            atividade.setTag3(tag3);
        }
        return atividade;
    }

    public void showActivity(Atividade atividade){
        if (atividade != null){
            addActivityView.showInfo(atividade);
        }
    }


}
