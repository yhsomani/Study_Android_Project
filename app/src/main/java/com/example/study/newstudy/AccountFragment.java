package com.example.study.newstudy;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.study.newstudy.Authentication.LoginActivity;
import com.example.study.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class AccountFragment extends Fragment {
    private static final int REQUEST_CODE_PERMISSIONS = 101;

    Button selectImagesBtn, createPdfBtn;
    TextView selectedImagesTV;
    ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    ArrayList<Uri> imageUris = new ArrayList<>();
    private Button updateProfileBtn;
    private TextView textViewName;
    private TextView textViewStudentId;
    private TextView textViewEmail;
    private CircleImageView profileImg;
    private ProgressDialog progressDialog;
    private DatabaseReference userReference;
    private FirebaseUser currentUser;
    private Button logoutBtn;

    public AccountFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initializeViews(view);
        loadUserData();
        selectImagesBtn.setOnClickListener(v -> openImagePicker());
        createPdfBtn.setOnClickListener(v -> createPdf());
        logoutBtn.setOnClickListener(v -> showLogoutDialog());
        return view;
    }

    private void initializeViews(View view) {
        qrCodeIV = view.findViewById(R.id.imageView2);
//        updateProfileBtn = view.findViewById(R.id.updateProfileBtn);
        textViewName = view.findViewById(R.id.textView11);
        textViewStudentId = view.findViewById(R.id.textView13);
        textViewEmail = view.findViewById(R.id.textView15);
        profileImg = view.findViewById(R.id.profileImg);
        logoutBtn = view.findViewById(R.id.logoutbtn);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (currentUser != null) {
            userReference = database.getReference("user").child(currentUser.getUid());
        }

        selectImagesBtn = view.findViewById(R.id.select_images_btn);
        createPdfBtn = view.findViewById(R.id.create_pdf_btn);
        selectedImagesTV = view.findViewById(R.id.selected_images_tv);
    }

    private void loadUserData() {
        if (currentUser != null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading User Data...");
            progressDialog.show();

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    if (snapshot.exists()) {
                        Users user = snapshot.getValue(Users.class);
                        if (user != null) {
                            textViewName.setText("Name: " + user.getUserName());
                            textViewStudentId.setText("Student ID: " + user.getUserId());
                            textViewEmail.setText("Email: " + user.getMail());
                            loadProfileImage(user.getProfilepic());
                            // Check if user.getUserName() is not empty
                            if (!TextUtils.isEmpty(user.getUserName())) {
                                // Get the text from user.getUserName()
                                String text = user.getUserName();

                                // Get WindowManager service from Fragment's context
                                WindowManager manager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);

                                // Get default display
                                Display display = manager.getDefaultDisplay();

                                // Get display dimensions
                                Point point = new Point();
                                display.getSize(point);
                                int width = point.x;
                                int height = point.y;
                                int dimen = width < height ? width : height;
                                dimen = dimen * 3 / 4;

                                // Create QRGEncoder with the text and desired dimensions
                                QRGEncoder qrgEncoder = new QRGEncoder(text, null, QRGContents.Type.TEXT, dimen);

                                try {
                                    // Generate QR code as Bitmap using the correct method
                                    Bitmap bitmap = qrgEncoder.getBitmap();

                                    // Set the generated QR code bitmap to qrCodeIV
                                    qrCodeIV.setImageBitmap(bitmap);
                                } catch (Exception e) {
                                    // Handle exception
                                    Log.e("Tag", e.toString());
                                }
                            } else {
                                // Show a toast message if user.getUserName() is empty
                                Toast.makeText(getContext(), "Username is empty", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Error loading user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void loadProfileImage(String profileImageUrl) {
        if (getContext() != null && profileImg != null) {
            if (profileImageUrl == null || profileImageUrl.isEmpty()) {
                profileImg.setImageResource(R.drawable.man_user_icon);
            } else {
                Glide.with(getContext())
                        .load(profileImageUrl)
                        .into(profileImg);
            }
        }
    }

    private void updateProfile() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();
        progressDialog.dismiss();
        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void showLogoutDialog() {
        Dialog dialog = new Dialog(requireActivity(), R.style.dialoge);
        dialog.setContentView(R.layout.dialog_layout);
        Button no = dialog.findViewById(R.id.nobtn);
        Button yes = dialog.findViewById(R.id.yesbtn);

        yes.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        no.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            imageUris.clear();
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri);
                }
            } else {
                Uri imageUri = data.getData();
                imageUris.add(imageUri);
            }
            selectedImagesTV.setText("Selected Images: " + imageUris.size());
        } else {
            // Handle error
        }
    }

    private static final String PDF_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    private static final String PDF_NAME = "/images.pdf";
    private static final String TOAST_SELECT_IMAGES = "Please select images";
    private static final String TOAST_SUCCESS = "PDF Created and saved to: ";
    private static final String TOAST_ERROR = "Error: ";

//    private void createPdf() {
//        if (imageUris.size() == 0) {
//            showToast("Please select images");
//            return;
//        }
//
//        // Default directory for saving PDF
//        String destinationDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//
//        // PDF Document
//        Document document = new Document();
//
//        try {
//            // Path to save PDF
//            String pdfPath = destinationDirectory + "/images.pdf";
//
//            // Create instance of PdfWriter
//            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(pdfPath)));
//
//            // Open the document to write
//            document.open();
//
//            // Get page size
//            Rectangle pageSize = document.getPageSize();
//
//            for (Uri imageUri : imageUris) {
//                // Get input stream from image URI
//                try (InputStream imageStream = new BufferedInputStream(getContext().getContentResolver().openInputStream(imageUri))) {
//                    // Convert image stream to Bitmap
//                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//
//                    // Scale image to fit the page
//                    float documentWidth = pageSize.getWidth() - document.leftMargin() - document.rightMargin();
//                    float documentHeight = pageSize.getHeight() - document.topMargin() - document.bottomMargin();
//                    float imageWidth = bitmap.getWidth();
//                    float imageHeight = bitmap.getHeight();
//                    float widthScale = documentWidth / imageWidth;
//                    float heightScale = documentHeight / imageHeight;
//                    float scaleFactor = Math.min(widthScale, heightScale);
//                    int scaledWidth = Math.round(imageWidth * scaleFactor);
//                    int scaledHeight = Math.round(imageHeight * scaleFactor);
//
//                    // Calculate X and Y coordinates to center-align the image
//                    float x = (documentWidth - scaledWidth) / 2;
//                    float y = (documentHeight - scaledHeight) / 2;
//
//                    // Resize the Bitmap
//                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
//
//                    // Convert Bitmap to iText Image
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    Image image = Image.getInstance(stream.toByteArray());
//
//                    // Set image position
//                    image.setAbsolutePosition(x, y);
//
//                    // Add image to document
//                    document.add(image);
//                    document.newPage(); // Optional: Start a new page for each image
//                }
//            }
//
//            // Close document
//            document.close();
//
//            showToast("PDF Created and saved to: " + pdfPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            showToast("Error: " + e.getMessage());
//        }
//    }

    private void createPdf() {
        if (imageUris.size() == 0) {
            showToast("Please select images");
            return;
        }

        // Ensure external storage permissions are granted
        if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            return;
        }

        // Path to save PDF
        String pdfPath = PDF_DIRECTORY + PDF_NAME;

        // Check if the PDF already exists
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            showToast("PDF already exists at: " + pdfPath);
            return;
        }

        // PDF Document
        Document document = new Document();

        try {
            // Create instance of PdfWriter
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            // Open the document to write
            document.open();

            // Get page size
            Rectangle pageSize = document.getPageSize();

            for (Uri imageUri : imageUris) {
                // Get input stream from image URI
                try (InputStream imageStream = new BufferedInputStream(getContext().getContentResolver().openInputStream(imageUri))) {
                    // Convert image stream to Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                    // Scale image to fit the page
                    float documentWidth = pageSize.getWidth() - document.leftMargin() - document.rightMargin();
                    float documentHeight = pageSize.getHeight() - document.topMargin() - document.bottomMargin();
                    float imageWidth = bitmap.getWidth();
                    float imageHeight = bitmap.getHeight();
                    float widthScale = documentWidth / imageWidth;
                    float heightScale = documentHeight / imageHeight;
                    float scaleFactor = Math.min(widthScale, heightScale);
                    int scaledWidth = Math.round(imageWidth * scaleFactor);
                    int scaledHeight = Math.round(imageHeight * scaleFactor);

                    // Resize the Bitmap
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);

                    // Convert Bitmap to iText Image
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image image = Image.getInstance(stream.toByteArray());

                    // Center the image on the page
                    image.setAlignment(Image.MIDDLE);
                    image.setScaleToFitHeight(true);
                    document.add(image);
                    document.newPage();
                }
            }

            // Close the document
            document.close();
            showToast("PDF Created and saved to: " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Error: " + e.getMessage());
        }
    }


    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}