package com.example.study.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.study.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private ViewPager viewPagerVar;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_dep_comp, "Computer Engineering", "Focuses on designing and implementing computer hardware and software, offering a B.E. program covering computer architecture, algorithms, and programming languages, with labs providing hands-on experience."));
        list.add(new BranchModel(R.drawable.ic_dep_civil, "Civil Engineering", "Deals with planning and constructing infrastructure projects, offering a B.E. program covering structural engineering, surveying, and water resource management, with labs equipped for practical training."));
        list.add(new BranchModel(R.drawable.ic_dep_mecanical, "Mechanical Engineering", "Applies principles to design and operate mechanical systems, offering a B.E. program covering thermodynamics, fluid mechanics, and robotics, with well-equipped workshops for hands-on learning."));
        list.add(new BranchModel(R.drawable.ic_dep_entc, "Electronics & Telecommunication Engineering", "Integrates electronics with communication technologies, offering a B.E. program covering circuit design, signal processing, and control systems, with advanced labs for practical experimentation."));

        adapter = new BranchAdapter(getContext(), list);

        viewPagerVar = view.findViewById(R.id.viewPager);
        viewPagerVar.setAdapter(adapter);

        // Set image from drawable resource
        ImageView imageView = view.findViewById(R.id.collegeImage);
        imageView.setImageResource(R.drawable.image_4); // Replace "your_image_drawable" with your actual drawable resource
        return view;
    }
}
