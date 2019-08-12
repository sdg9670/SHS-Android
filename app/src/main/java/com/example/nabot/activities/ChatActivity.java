package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.ChatAdapterR;
import com.example.nabot.domain.ChatDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    Button SendButton;
    EditText SendContentText;
    ListView chatlist;
    private ChatDTO chat;
    private ClientDTO client;
    private ContactDTO contact;
    private String roomNum;
    TextView textView, ctx;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("chat");
    private ArrayAdapter<String> adapter;
    private ChatAdapterR chatAdapterR = new ChatAdapterR();
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textView = findViewById(R.id.textView2);
        list = new ArrayList<String>();
        SendButton = (Button) findViewById(R.id.sendbutton);
        chatlist = findViewById(R.id.chatlist);
        chatlist.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        SendContentText = (EditText) findViewById(R.id.sendcontenttext);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chatlist.setAdapter(chatAdapterR);
        final Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");
        chat = (ChatDTO) intent.getSerializableExtra("chat");

        if (client.getId() > contact.getSomeid()) {
            roomNum = contact.getSomeid() + "_" + client.getId();
        } else {
            roomNum = client.getId() + "_" + contact.getSomeid();
        }

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SendContentText.getText().toString().equals(("")))
                    return;

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                final String getTime = sdf.format(date);
                Log.e("getSomeid", String.valueOf(contact.getId()));
                Log.e("getSomeid", String.valueOf(client.getName()));
                Log.e("getSomeid", String.valueOf(client.getName()));
                Log.e("getSomeid", String.valueOf(contact.getSomeid()));
                Log.e("getSomeid", String.valueOf(contact.getSomename()));
                Log.e("getSomeid", String.valueOf(SendContentText.getText().toString()));
                Log.e("getSomeid", String.valueOf(getTime));

                chat = new ChatDTO(roomNum, client.getId(), client.getName(), contact.getSomeid(),contact.getSomename(), SendContentText.getText().toString(), getTime);
                myRef.child(roomNum).push().setValue(chat);
                SendContentText.setText("");

//                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
//                chat = new ChatDTO(client.getId(), contact.getSomeidname(),SendContentText.getText().toString());
//                Call<List<ChatDTO>> call = retrofitRequest.postChat(chat);
//                call.enqueue(new RetrofitRetry<List<ChatDTO>>(call) {
//                    @Override
//                    public void onResponse(Call<List<ChatDTO>> call, Response<List<ChatDTO>> response) {
//                        if(client.getId() == contact.getClientid()) {
//                            String chatchat = client.getName() + SendContentText.getText().toString() + getTime;
//                        }
//                    }
//                });
            }
        });
        myRef.child(roomNum).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("asdasd","3124");
                ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
                chatAdapterR.getDTO(contact, client);
                Log.e("getSendname", String.valueOf(contact.getSomename()));
                Log.e("getName",client.getName());
                chatAdapterR.addItem(chatDTO);

                Log.e("asdasd","312444");
                chatAdapterR.notifyDataSetChanged();
                chatlist.setSelection(chatAdapterR.getCount() - 1);
                Log.e("asdasd","312455");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

