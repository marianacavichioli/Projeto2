package com.mobile2.projeto2.activities.AddActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile2.projeto2.entity.Atividade;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivityActivity extends AppCompatActivity implements AddActivityView{

    AddActivityPresenter addActivityPresenter;

    @BindView(R.id.txtNome) public EditText nome;
    @BindView(R.id.txtDescricao) public EditText descricao;
    @BindView(R.id.txtTag1) public EditText tag1;
    @BindView(R.id.txtTag2) public EditText tag2;
    @BindView(R.id.txtTag3) public EditText tag3;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        ButterKnife.bind(this);
        addActivityPresenter = new AddActivityPresenter(this);
        addActivityPresenter.showActivity((Atividade) getIntent().getSerializableExtra("exibir_atividade"));
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_atividade, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_save){
            addAtividade();
            return true;
        }
        return false;
    }
    @Override public void showInfo(Atividade atividade) {
        nome.setText(atividade.getNomeAtv());
        descricao.setText(atividade.getDescAtv());
        tag1.setText(atividade.getTag1());
        tag2.setText(atividade.getTag2());
        tag3.setText(atividade.getTag3());
    }
    @Override public void showToast() {
        Toast toast = Toast.makeText(AddActivityActivity.this, "Impossível abrir o recurso", Toast.LENGTH_LONG);
        toast.show();
    }

    public void addAtividade(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("atividade", addActivityPresenter.getAtividade(nome.getText().toString(), descricao.getText().toString(), tag1.getText().toString(), tag2.getText().toString(), tag3.getText().toString()));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
