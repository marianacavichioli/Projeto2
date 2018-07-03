package com.mobile2.projeto2.activities.select_level;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobile2.projeto2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLevelActivity extends AppCompatActivity{

    private int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.level1)
    public void startLevel1(){
        //TODO: FAZER REALMENTE A QUANTIDADE DE SILABAS CERTAS APARECEREM
        level = 1;
        finish();
    }


    @OnClick(R.id.level2)
    public void startLevel2(){
        //TODO: FAZER REALMENTE A QUANTIDADE DE SILABAS CERTAS APARECEREM
        level = 2;
        finish();
    }


    @OnClick(R.id.level3)
    public void startLevel3(){
        //TODO: FAZER REALMENTE A QUANTIDADE DE SILABAS CERTAS APARECEREM
        level = 3;
        finish();
    }
}
