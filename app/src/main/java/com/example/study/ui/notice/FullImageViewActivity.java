package com.example.study.ui.notice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.study.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageViewActivity extends AppCompatActivity {
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        String image = getIntent().getStringExtra("image");
        photoView = findViewById(R.id.fullscreen_view);
        Glide.with(this).load(image).into(photoView);
    }
}
