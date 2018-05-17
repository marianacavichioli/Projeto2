package com.mobile2.projeto2.activities.ActivitiesList;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesListPresenter {

    ActivitiesListView activitiesListView;
    private List<Atividade> activityList = new ArrayList<>();

    public ActivitiesListPresenter (ActivitiesListView activitiesListView) {this.activitiesListView = activitiesListView;}

    void addActivity(Atividade atividade){
        activityList.add(atividade);
        activitiesListView.updateList(activityList);
    }
}
