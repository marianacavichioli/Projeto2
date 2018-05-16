package com.mobile2.projeto2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.criar_template.CriarTemplateActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    private final int CODIGO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.button)
    public void criar_template() {
        Log.d("CLICOU","Foi para criacao");
        Intent criarTemplate = new Intent(MainActivity.this, CriarTemplateActivity.class);
        startActivityForResult(criarTemplate, CODIGO);

    }
}
