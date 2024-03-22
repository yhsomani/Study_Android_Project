package com.example.study.ui.notice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.study.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private Context context;
    private ArrayList<NoticeData> noticeDataList;
    private NoticeClickListener noticeClickListener;

    public NoticeAdapter(Context context, ArrayList<NoticeData> noticeDataList, NoticeClickListener noticeClickListener) {
        this.context = context;
        this.noticeDataList = noticeDataList;
        this.noticeClickListener = noticeClickListener;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item_layout, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        NoticeData currentItem = noticeDataList.get(position);

        holder.noticeTitle.setText(currentItem.getTitle());
        holder.noticeDate.setText(currentItem.getData());
        holder.noticeTime.setText(currentItem.getTime());

        // Load image using Glide if URL is not empty
        if (currentItem.getImage() != null && !currentItem.getImage().isEmpty()) {
            Glide.with(context).load(currentItem.getImage()).into(holder.noticeImage);
        } else {
            // Handle empty or null image URL
            // For example, you can set a placeholder image or hide the ImageView
            holder.noticeImage.setImageResource(R.drawable.no_data_found_img);
        }
        holder.noticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImageViewActivity.class);
                intent.putExtra("image", currentItem.getImage());
                context.startActivity(intent);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeDataList.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        private final TextView noticeTitle;
        private final TextView noticeDate;
        private final TextView noticeTime;
        private final ImageView noticeImage;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeDate = itemView.findViewById(R.id.date);
            noticeTime = itemView.findViewById(R.id.time);
            noticeImage = itemView.findViewById(R.id.noticeImage);
        }
    }

    public interface NoticeClickListener {
        void onDeleteClick(int position);
    }
}
