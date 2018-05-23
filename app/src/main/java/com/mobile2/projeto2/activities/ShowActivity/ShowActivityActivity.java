package com.mobile2.projeto2.activities.ShowActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile2.projeto2.entity.Atividade;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivityActivity extends AppCompatActivity implements ShowActivityView{

    @BindView(R.id.actName) TextView nome;
    @BindView(R.id.actDesc) TextView descricao;
    @BindView(R.id.actTag1) TextView tag1;
    @BindView(R.id.actTag1) TextView tag2;
    @BindView(R.id.actTag1) TextView tag3;
    @BindView(R.id.startButton) Button startButton;

    ShowActivityPresenter showActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activity);
        ButterKnife.bind(this);

        showActivityPresenter = new ShowActivityPresenter(this);
        showActivityPresenter.showActivity((Atividade) getIntent().getSerializableExtra("exibir_atividade"));
    }

    @Override
    public void showInfo(Atividade atividade) {
        nome.setText(atividade.getNomeAtv());
        descricao.setText(atividade.getDescAtv());
        tag1.setText(atividade.getTag1());
        tag2.setText(atividade.getTag2());
        tag3.setText(atividade.getTag3());
    }

    @OnClick (R.id.startButton) public void comecaAtv(){
        //TODO: colocar a inicialização da atividade aqui !!
    }
}
