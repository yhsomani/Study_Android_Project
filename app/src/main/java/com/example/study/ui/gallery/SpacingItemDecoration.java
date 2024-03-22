package com.example.study.ui.gallery;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontalSpacing;
    private int verticalSpacing;
    private boolean includeEdge;

    public SpacingItemDecoration(int horizontalSpacing, int verticalSpacing, boolean includeEdge) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        if (includeEdge) {
            outRect.left = horizontalSpacing;
            outRect.right = horizontalSpacing;
            outRect.top = verticalSpacing;
            outRect.bottom = verticalSpacing;
        } else {
            if (position == 0) {
                outRect.left = 0;
                outRect.right = horizontalSpacing;
            } else if (position == itemCount - 1) {
                outRect.left = horizontalSpacing;
                outRect.right = 0;
            } else {
                outRect.left = horizontalSpacing;
                outRect.right = horizontalSpacing;
            }

            if (position < itemCount) {
                outRect.top = verticalSpacing;
            }
        }
    }
}