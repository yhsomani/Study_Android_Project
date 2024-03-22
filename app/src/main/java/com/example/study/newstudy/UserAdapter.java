package com.example.study.newstudy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.newstudy.Chats.ChatWindowActivity;
import com.example.study.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Users> usersList;
    private static final String DEFAULT_PROFILE_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/study-5ab42.appspot.com/o/man-user-color-icon.png?alt=media&token=0998c3b8-8db6-4db0-ae74-669d2d3b57b1";

    public UserAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersList = usersArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = usersList.get(position);

        // Load profile image using the modified method
        loadProfileImage(user.getProfilepic(), holder.userProfile);

        holder.userName.setText(user.getUserName());
        holder.userStatus.setText(user.getStatus());

        holder.itemView.setOnClickListener(view -> {
            // Start ChatWindowActivity with user details
            Intent intent = new Intent(context, ChatWindowActivity.class);
            intent.putExtra("recipientName", user.getUserName());
            intent.putExtra("recipientImage", user.getProfilepic());
            intent.putExtra("recipientId", user.getUserId());
            intent.putExtra("recipientStatus", user.getStatus());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userProfile;
        TextView userName;
        TextView userStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.userProfile);
            userName = itemView.findViewById(R.id.userName);
            userStatus = itemView.findViewById(R.id.userStatus);
        }
    }

    // Load profile image using Picasso
    private void loadProfileImage(String profileImageUrl, ImageView targetImageView) {
        if (profileImageUrl == null || targetImageView == null) {
            // If the profile image URL or target ImageView is null, do nothing
            return;
        }

        // Use Picasso to load the image into the target ImageView
        Picasso.get().load(profileImageUrl).into(targetImageView, new Callback() {
            @Override
            public void onSuccess() {
                // Image loaded successfully
            }

            @Override
            public void onError(Exception e) {
                // Handle error, set a placeholder image
                targetImageView.setImageResource(android.R.drawable.ic_menu_report_image); // Placeholder image
            }
        });
    }
}
