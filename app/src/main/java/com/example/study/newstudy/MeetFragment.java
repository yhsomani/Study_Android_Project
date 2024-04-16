package com.example.study.newstudy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.study.R;

//import org.jitsi.meet.sdk.JitsiMeet;
//import org.jitsi.meet.sdk.JitsiMeetActivity;
//import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MeetFragment extends Fragment {

    //    private EditText secretCodeBox;
//    private Button joinBtn, shareBtn, createBtn;
    private WebView webView;
    private ProgressBar progressBar;

    public MeetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initializeJitsiMeet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meet, container, false);
        webView = view.findViewById(R.id.webview);
        progressBar = view.findViewById(R.id.progressBar);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://internship.shankerfoundationtrust.org/jitsi-meet/");
        return view;
    }

//    private void initializeViews(View view) {
//        secretCodeBox = view.findViewById(R.id.secretCodeBox);
//        createBtn = view.findViewById(R.id.createBtn);
//        joinBtn = view.findViewById(R.id.joinBtn);
//        shareBtn = view.findViewById(R.id.shareBtn);
//    }

//    private void initializeJitsiMeet() {
//        try {
//            URL serverURL = new URL("https://meet.jit.si");
//            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
//                    .setServerURL(serverURL)
//                    .setFeatureFlag("welcomepage.enabled", false)
//                    .setFeatureFlag("prejoinpage.enabled", false)
//                    .setFeatureFlag("lobby-mode.enabled", false)
//                    .setFeatureFlag("ask-to-join.enabled", false)
//                    .build();
//            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }

//    private void joinMeeting() {
//        if (isValidMeetingCode()) {
//            JitsiMeetConferenceOptions options = buildConferenceOptions();
//            JitsiMeetActivity.launch(requireContext(), options);
//        } else {
//            // Show an error or prompt for a valid meeting code
//            showToast("Please enter a valid meeting code");
//        }
//    }

//    private void createMeeting() {
//        if (isValidMeetingCode()) {
//            JitsiMeetConferenceOptions options = buildConferenceOptions();
//            JitsiMeetActivity.launch(requireContext(), options);
//        } else {
//            // Show an error or prompt for a valid meeting code
//            showToast("Please enter a valid meeting code");
//        }
//    }

    // Helper method to check if the meeting code is valid
//    private boolean isValidMeetingCode() {
//        String meetingCode = secretCodeBox.getText().toString().trim();
//        return !meetingCode.isEmpty();
//    }
//
//    // Helper method to display a toast message
//    private void showToast(String message) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
//    }


//    private JitsiMeetConferenceOptions buildConferenceOptions() {
//        return new JitsiMeetConferenceOptions.Builder()
//                .setRoom(secretCodeBox.getText().toString())
//                .setConfigOverride("requireDisplayName", false)
//                .setFeatureFlag("welcomepage.enabled", false)
//                .setFeatureFlag("prejoinpage.enabled", false)
//                .setFeatureFlag("lobby-mode.enabled", false)
//                .setFeatureFlag("ask-to-join.enabled", false)
//                .build();
//    }

//    private void shareMeetingCode() {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        String shareBody = "Required Meeting Code: " + secretCodeBox.getText().toString();
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
//        startActivity(intent);
//    }
}
