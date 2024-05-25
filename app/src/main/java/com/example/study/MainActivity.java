package com.example.study;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.study.newstudy.Authentication.LoginActivity;
import com.example.study.ui.ebook.EbookActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);
        }
        setContentView(R.layout.activity_main);
// Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        setupViews();
        setupNavigation();
    }
    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is not authenticated, redirect to LoginActivity and finish MainActivity
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
    private void setupViews() {
        navController = Navigation.findNavController(this, R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        // Check if support action bar is not null before using it
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

//    private void setupViews() {
//        navController = Navigation.findNavController(this, R.id.frame_layout);
//        drawerLayout = findViewById(R.id.drawerLayout);
//        NavigationView navigationView = findViewById(R.id.navigation_view);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        navigationView.setNavigationItemSelectedListener(this);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//    }

    private void setupNavigation() {
        toggle.syncState();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    // Handle options item selected event
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Pass the selected item to ActionBarDrawerToggle
        if (toggle != null && toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle navigation item selected event for navigation drawer
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Get the selected item id
//        int id = item.getItemId();
//
//        // Handle different menu item clicks
//        if (id == R.id.navigation_home) {
////            com.example.study.ui.home.HomeFragment
//        }
//        else if (id == R.id.navigation_ebook) {
//            startActivity(new Intent(this, EbookActivity.class));
//        }
//        else if (id == R.id.navigation_faculty) {
////            com.example.study.ui.faculty.FacultyFragment
//        }
//        else if (id == R.id.navigation_gallery) {
////            com.example.study.ui.gallery.GalleryFragment
//        }
//        else if (id == R.id.navigation_about) {
////            com.example.study.ui.about.AboutFragment
//        }
//        else if (id == R.id.navigation_developers) {
//            Toast.makeText(this, "Developer Clicked", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Get the selected item id
        int id = item.getItemId();

        // Handle different menu item clicks
        if (id == R.id.navigation_home) {
            // Navigate to the HomeFragment
            navController.navigate(R.id.navigation_home);
        } else if (id == R.id.navigation_ebook) {
            // Start EbookActivity
            startActivity(new Intent(this, EbookActivity.class));
        } else if (id == R.id.navigation_faculty) {
            // Navigate to the FacultyFragment
            navController.navigate(R.id.navigation_faculty);
        } else if (id == R.id.navigation_gallery) {
            // Navigate to the GalleryFragment
            navController.navigate(R.id.navigation_gallery);
        } else if (id == R.id.navigation_about) {
            // Navigate to the AboutFragment
            navController.navigate(R.id.navigation_about);
        } else if (id == R.id.navigation_developers) {
            // Show a toast indicating that developers were clicked
            navController.navigate(R.id.navigation_developers);
        }

        // Close the navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}