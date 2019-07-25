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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabot.R;
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
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    Button SendButton;
    EditText SendContentText;
    ScrollView ChatScroll;
    ListView chatlist;
    private ChatDTO chat;
    private ClientDTO client;
    private ContactDTO contact;
    private String roomNum;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        SendButton = (Button) findViewById(R.id.sendbutton);
        chatlist = findViewById(R.id.chatlist);
        SendContentText = (EditText) findViewById(R.id.sendcontenttext);
        ChatScroll = (ScrollView) findViewById(R.id.chatscroll);

        final Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");
        chat = (ChatDTO) intent.getSerializableExtra("chat");

        if(client.getId() > contact.getSomeid()) {
            roomNum = contact.getSomeid() + "_" + client.getId();
        }else{
            roomNum = client.getId() + "_" + contact.getSomeid();
        }

        openChat(roomNum);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("chatCheck","123214124124");
//                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
//                Call<List<ChatDTO>> call = retrofitRequest.postChat(new ChatDTO(client.getId(), contact.getSomeidname(), SendContentText.getText().toString()));
//                Log.e("chatchat","okman");
//                call.enqueue(new RetrofitRetry<List<ChatDTO>>(call) {
//                    @Override
//                    public void onResponse(Call<List<ChatDTO>> call, Response<List<ChatDTO>> response) {
//                        msg.append(SendContentText.getText().toString() + "\n");
//                        SendContentText.setText("");
//                        ChatScroll.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                ChatScroll.fullScroll(ScrollView.FOCUS_DOWN);
//                            }
//                        });
//                    }
//                    @Override
//                    public void onFailure(Call<List<ChatDTO>> call, Throwable t) {
//
//                    }
//                });
                if(SendContentText.getText().toString().equals(("")))
                    return ;

                chat = new ChatDTO(roomNum, client.getId(), contact.getSomeidname(), SendContentText.getText().toString());
                myRef.child("chat").child(chat.getRoomnum()).push().setValue(chat);
                SendContentText.setText("");

                ChatScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        ChatScroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }
    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter){
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add(chatDTO.getSendid()+ ": "+chatDTO.getMsg()+" : "+chatDTO.getDatetime());
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter){
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getSendid()+ ": "+chatDTO.getMsg()+" : "+chatDTO.getDatetime());
    }

    private void openChat(String roomnum) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chatlist.setAdapter(adapter);

        myRef.child("chat").child(roomnum).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG","s:"+s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
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
