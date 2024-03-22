package com.example.study.newstudy.Chats;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWindowActivity extends AppCompatActivity {

    private String receiverImg, receiverUid, receiverName, senderUid, receiverStatus;
    private CircleImageView profile;
    private TextView receiverNameTextView, receiverStatusTextView;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private ImageButton sendButton;
    private EditText textMessage;
    private RecyclerView messageRecyclerView;
    private ArrayList<MessageModel> messagesArrayList;
    private MessagesAdapter messagesAdapter;

    private String senderRoom, receiverRoom;

     static String senderImg;
     static String receiverImgStatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        receiverName = getIntent().getStringExtra("recipientName");
        receiverImg = getIntent().getStringExtra("recipientImage");
        receiverUid = getIntent().getStringExtra("recipientId");
        receiverStatus = getIntent().getStringExtra("recipientStatus");


        messagesArrayList = new ArrayList<>();

        sendButton = findViewById(R.id.sendButton);
        textMessage = findViewById(R.id.textMessage);
        receiverNameTextView = findViewById(R.id.receiverName);
        receiverStatusTextView = findViewById(R.id.receiverStatus);
        profile = findViewById(R.id.userImage);
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(this,messagesArrayList);
        messageRecyclerView.setAdapter(messagesAdapter);


        Picasso.get().load(receiverImg).into(profile);
        receiverNameTextView.setText(receiverName);
        receiverStatusTextView.setText(receiverStatus);

        senderUid =  firebaseAuth.getUid();

        senderRoom = senderUid+receiverUid;
        receiverRoom = receiverUid+senderUid;



        DatabaseReference  reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference  chatreference = database.getReference().child("chats").child(senderRoom).child("messages");


        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messages = dataSnapshot.getValue(MessageModel.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg= snapshot.child("profilepic").getValue().toString();
                receiverImgStatic=receiverImg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textMessage.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(ChatWindowActivity.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                    return;
                }
                textMessage.setText("");
                Date date = new Date();
                MessageModel messagess = new MessageModel(message,senderUid,date.getTime());

                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .child("messages")
                                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
            }
        });

    }
}