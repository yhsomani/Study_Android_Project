package com.example.study.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.study.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    SliderView sliderView;
    private Timer timer;
    ImageView map;
    private int currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = view.findViewById(R.id.slider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.FILL);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setScrollTimeInSec(1);

        setSliderViews();
        // Start auto-slide
        startAutoSlide();

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(v.getContext());
            }
        });

        return view;
    }

    private void openMap(Context context) {
        Uri uri = Uri.parse("geo:0,0?q=Shrimati Kashibai Navale College of Engineering (Administration Building)");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // If Google Maps app is not installed, open the map in a web browser
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop auto-slide when the fragment is destroyed
        stopAutoSlide();
    }

    private void startAutoSlide() {
        // Initialize a new Timer
        timer = new Timer();

        // Set the schedule function and rate
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Increment position
                        if (currentPosition == sliderView.getSliderAdapter().getCount() - 1) {
                            currentPosition = 0;
                        } else {
                            currentPosition++;
                        }

                        // Set current position
                        sliderView.setCurrentPagePosition(currentPosition);
                    }
                });
            }
        }, 3000, 3000); // Auto-slide every 5 seconds
    }

    private void stopAutoSlide() {
        // Cancel the Timer when fragment is destroyed
        if (timer != null) {
            timer.cancel();
        }
    }

    private void setSliderViews() {
        SliderAdapter adapter = new SliderAdapter();
        adapter.addItem(new SliderItem(R.drawable.bg_intro));
        adapter.addItem(new SliderItem(R.drawable.image_2));
        adapter.addItem(new SliderItem(R.drawable.image_3));

        sliderView.setSliderAdapter(adapter);
    }

    private static class SliderItem {
        String imageUrl;
        int imageResourceId;

        // Constructor for URL
        SliderItem(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        // Constructor for resource ID
        SliderItem(int imageResourceId) {
            this.imageResourceId = imageResourceId;
        }

        String getImageUrl() {
            return imageUrl;
        }

        int getImageResourceId() {
            return imageResourceId;
        }

        boolean isUrl() {
            return imageUrl != null;
        }
    }

    private static class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {
        private List<SliderItem> items = new ArrayList<>();

        void addItem(SliderItem item) {
            items.add(item);
        }

        @Override
        public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
            return new SliderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
            SliderItem item = items.get(position);
            viewHolder.bind(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        static class SliderViewHolder extends SliderViewAdapter.ViewHolder {
            ImageView imageView;

            SliderViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageViewSlider);
            }

            void bind(SliderItem item) {
                // Load image using Glide or any other image loading library
                if (item.isUrl()) {
                    Glide.with(itemView.getContext())
                            .load(item.getImageUrl())
                            .into(imageView);
                } else {
                    imageView.setImageResource(item.getImageResourceId());
                }
            }
        }
    }
}
