package com.example.study.newstudy.WhiteBoard;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.study.R;
import com.google.android.material.slider.RangeSlider;

import java.io.OutputStream;

import petrov.kristiyan.colorpicker.ColorPicker;

public class WhiteBoardFragment extends Fragment {

    //creating the object of type DrawView
    //in order to get the reference of the View
    private DrawView paint;
    //creating objects of type button
    private ImageButton save,color,stroke,undo,strokeval;
    private Button inc,dnc;
    private EditText distrokeval;
    int value=15;
    int x;
    //creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WhiteBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WhiteBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WhiteBoardFragment newInstance(String param1, String param2) {
        WhiteBoardFragment fragment = new WhiteBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_white_board, container, false);

        //getting the reference of the views from their ids


        paint = view.findViewById(R.id.draw_view);
//        rangeSlider = (RangeSlider) view.findViewById(R.id.rangebar);
        undo = view.findViewById(R.id.btn_undo);
        save = view.findViewById(R.id.btn_save);
        color = view.findViewById(R.id.btn_color);
        stroke = view.findViewById(R.id.btn_stroke);
        inc = view.findViewById(R.id.increment);
        dnc = view.findViewById(R.id.decrement);
        distrokeval = view.findViewById(R.id.strokesize);
        strokeval = view.findViewById(R.id.setvalue);

        distrokeval.setText(String.valueOf(value));


        //        ********************************************
        //creating a OnClickListener for each button, to perform certain actions

        //the undo button will remove the most recent stroke from the canvas
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });
        //the save button will save the current canvas which is actually a bitmap
        //in form of PNG, in the storage
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting the bitmap from DrawView class
                Bitmap bmp=paint.save();
                //opening a OutputStream to write into the file
                OutputStream imageOutStream = null;

                ContentValues cv=new ContentValues();
                //name of the file
                cv.put(MediaStore.Images.Media.DISPLAY_NAME, "drawing.png");
                //type of the file
                cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                //location of the file to be saved
                cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);


                //ge the Uri of the file which is to be v=created in the storage
                Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
                try {
                    //open the output stream with the above uri
                    imageOutStream = getContext().getContentResolver().openOutputStream(uri);
                    //this method writes the files in storage
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream);
                    //close the output stream after use
                    imageOutStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Image Saved", Toast.LENGTH_SHORT).show();


            }
        });
        //the color button will allow the user to select the color of his brush
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker colorPicker = new ColorPicker(getActivity());
                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                            @Override
                            public void setOnFastChooseColorListener(int position, int color) {
                                // get the integer value of color
                                // selected from the dialog box and
                                // set it as the stroke color
                                paint.setColor(color);
                            }
                            @Override
                            public void onCancel() {
                                colorPicker.dismissDialog();
                            }
                        })
                        // set the number of color columns
                        // you want  to show in dialog.
                        .setColumns(5)
                        // set a default color selected
                        // in the dialog
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });
        // the button will toggle the visibility of the RangeBar/RangeSlider
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inc.getVisibility() == View.VISIBLE && dnc.getVisibility() == View.VISIBLE){
                    inc.setVisibility(View.GONE);
                    dnc.setVisibility(View.GONE);
                    distrokeval.setVisibility(View.GONE);
                    strokeval.setVisibility(View.GONE);
                }
                else {
                    inc.setVisibility(View.VISIBLE);
                    dnc.setVisibility(View.VISIBLE);
                    distrokeval.setVisibility(View.VISIBLE);
                    strokeval.setVisibility(View.VISIBLE);
                }

//                if(rangeSlider.getVisibility()==View.VISIBLE)
//                    rangeSlider.setVisibility(View.GONE);
//                else
//                    rangeSlider.setVisibility(View.VISIBLE);
            }
        });


        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value<100){
                    x=value+5;
                    if(x>=0) {
                        value=x;
                        paint.setStrokeWidth(value);
                        distrokeval.setText(String.valueOf(value));
                    }
                }
            }
        });
        dnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value>0){
                    x=value-5;
                    if(x <= 100) {
                        value=x;
                        paint.setStrokeWidth(value);
                        distrokeval.setText(String.valueOf(value));
                    }
                }
            }
        });
        distrokeval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distrokeval.setText("");
            }
        });
        strokeval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x =Integer.parseInt(distrokeval.getText().toString());

                if(x>=0 && x<=100){
                    value=x;
                    distrokeval.setText(String.valueOf(value));
                    paint.setStrokeWidth(value);
                    Toast.makeText(getContext(), "Value set", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Please enter in range 0-100", Toast.LENGTH_SHORT).show();

                }
            }
        });


        //pass the height and width of the custom view to the init method of the DrawView object
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
//        ********************************************

        return view;
    }

}