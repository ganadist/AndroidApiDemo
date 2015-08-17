package com.example.android.apis;

import android.app.TabActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppCompatTabActivity extends TabActivity implements AppCompatCallback {
    private AppCompatDelegate mAppCompatDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAppCompatDelegate = AppCompatDelegate.create(this, this);
        mAppCompatDelegate.installViewFactory();
        mAppCompatDelegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mAppCompatDelegate.onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return mAppCompatDelegate.getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        mAppCompatDelegate.setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return mAppCompatDelegate.getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mAppCompatDelegate.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        mAppCompatDelegate.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mAppCompatDelegate.setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        mAppCompatDelegate.addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mAppCompatDelegate.onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        mAppCompatDelegate.setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mAppCompatDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAppCompatDelegate.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppCompatDelegate.onDestroy();
    }

    public void invalidateOptionsMenu() {
        mAppCompatDelegate.invalidateOptionsMenu();
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {

        return null;
    }
}
