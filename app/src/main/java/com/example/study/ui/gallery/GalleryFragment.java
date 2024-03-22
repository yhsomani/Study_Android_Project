package com.example.study.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("gallery");
        getCategories();
        return view;
    }

    private void getCategories() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey();
                    List<String> imageList = new ArrayList<>();
                    for (DataSnapshot imageSnapshot : categorySnapshot.getChildren()) {
                        String imageUrl = imageSnapshot.getValue(String.class);
                        imageList.add(0, imageUrl);
                    }
                    setupCategory(categoryName, imageList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCategory(String categoryName, List<String> imageList) {
        View categoryView = LayoutInflater.from(getContext()).inflate(R.layout.category_layout, null);
        TextView categoryTitle = categoryView.findViewById(R.id.catagory);
        RecyclerView categoryRecyclerView = categoryView.findViewById(R.id.imageGrid);

        categoryTitle.setText(categoryName);

        GalleryAdapter adapter = new GalleryAdapter(getContext(), imageList);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), calculateSpanCount(imageList.size())));
        categoryRecyclerView.addItemDecoration(new SpacingItemDecoration(1, 1, true));
        categoryRecyclerView.setAdapter(adapter);

        LinearLayout addLayout = getView().findViewById(R.id.addLayout);
        addLayout.addView(categoryView);
    }

    private int calculateSpanCount(int imageCount) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = getResources().getDimensionPixelSize(R.dimen.image_width);
        int spanCount = screenWidth / imageWidth;
        if (spanCount == 0) {
            spanCount = 1;
        }
        return Math.min(spanCount, imageCount);
    }
}