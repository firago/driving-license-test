package com.dfirago.dlt.dashboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dfirago.dlt.R;
import com.dfirago.dlt.dashboard.model.Category;
import com.dfirago.dlt.dashboard.model.TestMode;
import com.dfirago.dlt.training.TrainingActivity;

import butterknife.ButterKnife;

/**
 * Created by Dmytro Firago on 02/07/2017.
 */
public class DashboardActivity extends AppCompatActivity implements
        DashboardFragment.OnFragmentInteractionListener,
        CategoryFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        addOrReplaceFragment(R.id.dashboard_container, DashboardFragment.newInstance());
    }

    @Override
    public void onCategorySelected(Category category) {
        addOrReplaceFragment(R.id.dashboard_container, CategoryFragment.newInstance(category));
    }

    @Override
    public void onRateAppSelected() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    @Override
    public void onTestSelected(Category category, TestMode testMode) {
        startActivity(TrainingActivity.getIntent(this)); // TODO
    }

    private void addOrReplaceFragment(int containerViewId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right,
                R.anim.fade_out, R.anim.slide_in_left, R.anim.fade_out);
        Fragment existingFragment = fragmentManager.findFragmentById(containerViewId);
        if (existingFragment == null) {
            transaction.add(containerViewId, fragment);
        } else {
            transaction.replace(containerViewId, fragment);
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}