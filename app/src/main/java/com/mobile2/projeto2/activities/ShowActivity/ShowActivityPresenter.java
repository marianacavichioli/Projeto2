package com.mobile2.projeto2.activities.ShowActivity;

import com.mobile2.projeto2.entity.Atividade;

public class ShowActivityPresenter {
    ShowActivityView showActivityView;

    public ShowActivityPresenter (ShowActivityView showActivityView){
        this.showActivityView =showActivityView;
    }

    public void showActivity(Atividade atividade) {
        if (atividade != null){
            showActivityView.showInfo(atividade);
        }
    }
}
