package com.example.study.ui.faculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {
    DatabaseReference databaseReference;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_faculty, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher");

        loadTeacherData();

        return view;
    }

    private void loadTeacherData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout parentLayout = view.findViewById(R.id.parentLayout);
                parentLayout.removeAllViews(); // Clear previous views

                for (DataSnapshot departmentSnapshot : dataSnapshot.getChildren()) {
                    String departmentName = departmentSnapshot.getKey();
                    List<TeacherData> teachers = new ArrayList<>();
                    for (DataSnapshot teacherSnapshot : departmentSnapshot.getChildren()) {
                        // Convert the DataSnapshot to TeacherData object
                        String key = teacherSnapshot.getKey();
                        String name = teacherSnapshot.child("name").getValue(String.class);
                        String email = teacherSnapshot.child("email").getValue(String.class);
                        String post = teacherSnapshot.child("post").getValue(String.class);
                        String image = teacherSnapshot.child("image").getValue(String.class);
                        String category = teacherSnapshot.child("category").getValue(String.class);
                        // Create a new TeacherData object
                        TeacherData teacher = new TeacherData(name, email, post, image, category, key);

                        teachers.add(teacher);
                    }
                    displayDepartmentWithTeachers(parentLayout, departmentName, teachers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    private void displayDepartmentWithTeachers(LinearLayout parentLayout, String departmentName, List<TeacherData> teachers) {
        View departmentLayout = LayoutInflater.from(getContext()).inflate(R.layout.department_layout, parentLayout, false);

        TextView departmentTextView = departmentLayout.findViewById(R.id.departmentTextView);
        departmentTextView.setText(departmentName);

        RecyclerView recyclerView = departmentLayout.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        TeacherAdapter adapter = new TeacherAdapter(teachers, getContext());
        recyclerView.setAdapter(adapter);

        parentLayout.addView(departmentLayout);
    }
}