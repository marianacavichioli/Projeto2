package com.mobile2.projeto2.activities.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.choosetemplate.ChooseTemplateActivity;
import com.mobile2.projeto2.activities.criar_template.CriarTemplateActivity;
import com.mobile2.projeto2.activities.wordselector_activity.WordSelectorActivity;
import com.mobile2.projeto2.activities.wordselector_activity.deletion.WordDeletionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagementActivity extends AppCompatActivity{
    @BindView(R.id.management_button_create)
    Button mButtonGoToCreate;
    @BindView(R.id.management_button_delete)
    Button mButtonGoToDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        ButterKnife.bind(this);

        mButtonGoToCreate.setOnClickListener(v -> goToActivity(ChooseTemplateActivity.class));
        mButtonGoToDelete.setOnClickListener(v -> goToActivity(WordDeletionActivity.class));
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
