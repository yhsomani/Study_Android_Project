package com.example.study.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public class NoticeFragment extends Fragment implements NoticeAdapter.NoticeClickListener {

    private RecyclerView noticeRecycler;
    private ArrayList<NoticeData> noticeDataList;
    private NoticeAdapter noticeAdapter;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        noticeRecycler = view.findViewById(R.id.noticeRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        noticeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeDataList = new ArrayList<>();
        noticeAdapter = new NoticeAdapter(getContext(), noticeDataList, this);
        noticeRecycler.setAdapter(noticeAdapter);

        // Show ProgressBar before fetching data
        progressBar.setVisibility(View.VISIBLE);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notice");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeDataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeData noticeData = snapshot.getValue(NoticeData.class);
                    noticeDataList.add(0, noticeData);
                }
                noticeAdapter.notifyDataSetChanged();
                // Hide ProgressBar after data is loaded
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                // Hide ProgressBar in case of error
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void onDeleteClick(int position) {
        // Handle delete click event here
    }
}
