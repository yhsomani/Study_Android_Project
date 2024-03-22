package com.example.study.newstudy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.study.MainActivity;
import com.example.study.R;

public class SplashActivity extends AppCompatActivity {

    // Duration of the splash screen in milliseconds (3 seconds)
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Load animations
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Get views from the layout
        ImageView logo = findViewById(R.id.logo);
        TextView slogan = findViewById(R.id.slogan);
        TextView name = findViewById(R.id.name);
        TextView own1 = findViewById(R.id.ownOne);
        TextView own2 = findViewById(R.id.ownTwo);

        // Set animations on views
        setAnimation(logo, topAnim);
        setAnimation(name, topAnim);
        setAnimation(slogan, topAnim);
        setAnimation(own1, bottomAnim);
        setAnimation(own2, bottomAnim);

        // Handle splash screen timeout
        new Handler().postDelayed(this::startMainActivity, SPLASH_TIME_OUT);
    }

    // Method to set animation on a view
    private void setAnimation(ImageView view, Animation animation) {
        if (view != null) view.setAnimation(animation);
    }

    // Method to set animation on a view
    private void setAnimation(TextView view, Animation animation) {
        if (view != null) view.setAnimation(animation);
    }

    // Method to start the main activity and finish the splash activity
    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
