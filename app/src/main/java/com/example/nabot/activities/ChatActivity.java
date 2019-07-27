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

import com.example.nabot.R;
import com.example.nabot.domain.ChatDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    private ArrayList<String > list;
    private String chat_user, chat_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        list = new ArrayList<String>();
        SendButton = (Button) findViewById(R.id.sendbutton);
        chatlist = findViewById(R.id.chatlist);
        SendContentText = (EditText) findViewById(R.id.sendcontenttext);
        ChatScroll = (ScrollView) findViewById(R.id.chatscroll);

        final Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");
        chat = (ChatDTO) intent.getSerializableExtra("chat");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        if(client.getId() > contact.getSomeid()) {
            roomNum = contact.getSomeid() + "_" + client.getId();
        }else{
            roomNum = client.getId() + "_" + contact.getSomeid();
        }

        openChat(roomNum);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SendContentText.getText().toString().equals(("")))
                    return ;

                chat = new ChatDTO(roomNum, client.getId(), contact.getSomeid(), SendContentText.getText().toString());
                list.add(String.valueOf(chat));
                Log.e("addString", String.valueOf(chat));
                //myRef.child("chat").child(chat.getRoomnum()).push().setValue(chat);
                myRef.child("chat").child(roomNum).push().setValue(chat);
                //arrayAdapter.add(String.valueOf(chat));

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
//        Iterator i = dataSnapshot.getChildren().iterator();
//        while(i.hasNext()){
//            chat_user = (String) ((DataSnapshot) i.next()).getValue();
//            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
//
//            adapter.add(chat_user+" : "+chat_msg);
//        }
//        adapter.notifyDataSetChanged();
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
//        list.add(chatDTO.getSendid() + ": " + chatDTO.getMsg() + " : " + chatDTO.getDatetime() + "\n");
        adapter.add(chatDTO.getSendid() + ": " + chatDTO.getMsg() + " : " + chatDTO.getDatetime() + "\n");
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter){
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
//        chatlist.setAdapter(adapter);
        adapter.remove(chatDTO.getSendid() + ": " + chatDTO.getMsg() + " : " + chatDTO.getDatetime() + "\n");
//        adapter.notifyDataSetChanged();
    }

    private void openChat(final String roomnum) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        chatlist.setAdapter(adapter);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            String roomName = null;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    roomName = snapshot.getKey();
                    if (roomnum.equals(roomName)) {
                        addMessage(dataSnapshot, adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//        myRef.child("chat").child(roomnum).addChildEventListener(new ChildEventListener() {

    private void showChatList(){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        chatlist.setAdapter(adapter);

        myRef.child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.add(dataSnapshot.getKey());
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
