package com.mobile2.projeto2.activities.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.criar_template.CriarTemplateActivity;
import com.mobile2.projeto2.activities.management.ManagementActivity;
import com.mobile2.projeto2.activities.screenlock.NewPinActivity;
import com.mobile2.projeto2.activities.syllable_activity.SyllableActivityActivity;
import com.mobile2.projeto2.activities.wordselector_activity.WordSelectorActivity;
import com.mobile2.projeto2.activities.ajuda.AjudaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cesar on 5/22/2018.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_button_management)
    Button mButtonGoToManagement;
    @BindView(R.id.main_button_activities)
    Button mButtonGoToActivities;
    @BindView(R.id.main_button_help)
    ImageView mButtonGoToHelp;
    @BindView(R.id.icon_lock)
    ImageView mButtonGoToLockPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mButtonGoToManagement.setOnClickListener(v -> goToActivity(ManagementActivity.class));
        mButtonGoToHelp.setOnClickListener(v -> goToActivity(AjudaActivity.class));
        mButtonGoToActivities.setOnClickListener(v -> goToActivity(WordSelectorActivity.class));
        mButtonGoToLockPassword.setOnClickListener(v -> goToActivity(NewPinActivity.class));
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
