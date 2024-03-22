package com.example.study.newstudy.Authentication;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.study.newstudy.ManageTabAdapter;
import com.example.study.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        addTabsToTabLayout();
        ManageTabAdapter adapter = createTabAdapter();
        configureViewPager(adapter);
        configureTabLayout(adapter);
    }

    private void addTabsToTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.login_tab_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.register_tab_title)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private ManageTabAdapter createTabAdapter() {
        return new ManageTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
    }

    private void configureViewPager(ManageTabAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void configureTabLayout(final ManageTabAdapter adapter) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
