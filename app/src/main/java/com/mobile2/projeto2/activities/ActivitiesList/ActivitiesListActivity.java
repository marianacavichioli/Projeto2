package com.mobile2.projeto2.activities.ActivitiesList;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobile2.projeto2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesListActivity extends AppCompatActivity implements ActivitiesListView{

    @BindView(R.id.rv_atividades) RecyclerView rvAtividades;
    private ActivitiesListPresenter activitiesListPresenter;
    private final int CODIGO = 123;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);
        ButterKnife.bind(this);
        activitiesListPresenter = new ActivitiesListPresenter(this);
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add:
                // TODO: fazer troca de tela para adicionar uma atividade
                //Intent adicionarAtividade = new Intent(ActivitiesListActivity.this, AddActivityActivity.class);
                //startActivityForResult(adicionarAtividade, CODIGO);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODIGO && resultCode == Activity.RESULT_OK){
            Atividade atividade = (Atividade) data.getSerializableExtra("atividade");
            if(atividade != null){
                activitiesListPresenter.addActivity(atividade);
            }
        }
    }
    @Override public void updateList(List<Atividade> activitiesList) {
        ActivitiesListAdapter activitiesListAdapter = new ActivitiesListAdapter(activitiesList, this);
        activitiesListAdapter.setRecyclerInterface(new RecyclerInterface() {
            @Override
            public void onClick(View view, int position) {
                //TODO: fazer troca de tela para visualizar o item clidado
                //Intent exibirAtividade = new Intent(ActivitiesListActivity.this, ShowActivityActivity.class);
                //exibirContato.putExtra("exibir_atividade", activitiesList.get(position));
                //startActivity(exibirAtividade);
            }
        });
    }
}
