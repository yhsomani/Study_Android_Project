package com.example.study.ui.faculty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private List<TeacherData> list;
    private Context context;
    private DatabaseReference databaseReference;

    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
        // Setting up the database reference
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        TeacherData item = list.get(position);
        holder.nameTextView.setText(item.getName());
        holder.emailTextView.setText(item.getEmail());
        holder.postTextView.setText(item.getPost());

        // Check if the image path is not empty or null before loading
        if (item.getImage() != null && !item.getImage().isEmpty()) {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } else {
            // Handle empty or null image path
            holder.imageView.setImageResource(R.drawable.man_user_icon); // Set a default image
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, postTextView;
        Button deleteData;
        ImageView imageView;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.teacherName);
            emailTextView = itemView.findViewById(R.id.teacherEmail);
            postTextView = itemView.findViewById(R.id.teacherPost);
            imageView = itemView.findViewById(R.id.teacherProfileImage);
        }
    }

    // Method to display confirmation dialog for deletion
    private void displayConfirmationDialog(final TeacherData teacher) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this teacher?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the department node reference
                DatabaseReference departmentRef = databaseReference.child("teacher").child(teacher.getCategory());

                // Remove the teacher's node from the department
                departmentRef.child(teacher.getKey()).removeValue();

                // Notify adapter of data change
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}