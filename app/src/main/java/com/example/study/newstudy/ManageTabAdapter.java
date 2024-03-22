package com.example.study.newstudy;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.study.newstudy.Authentication.LoginFragment;
import com.example.study.newstudy.Authentication.RegisterFragment;
import com.example.study.R;

public class ManageTabAdapter extends FragmentPagerAdapter {

    private final Context context;
    private final int totalTabs;

    // Constructor to initialize the context and totalTabs
    public ManageTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // Use BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT for better performance
        this.context = context;
        this.totalTabs = totalTabs;
    }

    // Return the fragment for each position
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Create and return a new instance of LoginFragment for the first tab
                return new LoginFragment();
            case 1:
                // Create and return a new instance of RegisterFragment for the second tab
                return new RegisterFragment();
            default:
                // Return null for unknown position
                return null;
        }
    }

    // Return the total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

    // Add this method to set the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.login_tab_title); // Replace with your login tab title resource
            case 1:
                return context.getString(R.string.register_tab_title); // Replace with your register tab title resource
            default:
                return super.getPageTitle(position);
        }
    }

}
