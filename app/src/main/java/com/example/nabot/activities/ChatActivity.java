package com.example.nabot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.ChatImageListAdapter;
import com.example.nabot.adapter.ChatImageViewAdapter;
import com.example.nabot.adapter.ImageListAdapter;
import com.example.nabot.classes.SquareViewPager;
import com.example.nabot.domain.ChatDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.domain.ChatImageDTO;
import com.example.nabot.util.FireBaseStorage2;
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
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    Button SendButton, imgBtn;
    EditText SendContentText;
    ListView chatlist;
    private ChatDTO chat;
    private ClientDTO client;
    private ContactDTO contact;
    private String roomNum;
    TextView textView;
    List<ChatImageDTO> chatImageArray = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("chat");
    private ArrayAdapter<String> adapter;
    ChatImageViewAdapter chatImageViewAdapter;
    SquareViewPager viewPager;

    List<Uri> imguri = new ArrayList<Uri>();
    ChatImageListAdapter chatimageListAdapter;
    FireBaseStorage2 fireBaseStorage = new FireBaseStorage2();
    List<String> downloadUri = new ArrayList<String>();
    List<String> filepath = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewPager = findViewById(R.id.viewPager2);
        textView = findViewById(R.id.textView2);
        SendButton = (Button) findViewById(R.id.sendbutton);
        chatlist = findViewById(R.id.chatlist);
        chatlist.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        SendContentText = (EditText) findViewById(R.id.sendcontenttext);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chatlist.setAdapter(adapter);
        final Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");
        chat = (ChatDTO) intent.getSerializableExtra("chat");
        imgBtn = findViewById(R.id.imgBtn);

        chatimageListAdapter = new ChatImageListAdapter(this, imguri);

        final int chat_id = 1;

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgintent = new Intent();
                imgintent.setType("image/*");
                imgintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                imgintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgintent, "이미지를선택하세요"), 0);
            }
        });

        final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        chat = new ChatDTO(chat_id, client.getId(), client.getId_name(), SendContentText.getText().toString());
        Call<List<ChatDTO>> call = retrofitRequest.postChat(chat);
        call.enqueue(new Callback<List<ChatDTO>>() {
            @Override
            public void onResponse(Call<List<ChatDTO>> call, Response<List<ChatDTO>> response) {
                RetrofitRequest retrofitRequest1 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<List<ChatDTO>> call1 = retrofitRequest1.getlast_chat();
                call1.enqueue(new RetrofitRetry<List<ChatDTO>>(call1) {
                    @Override
                    public void onResponse(Call<List<ChatDTO>> call, Response<List<ChatDTO>> response) {
                        chat = response.body().get(0);
                        if (chatimageListAdapter.getItem() != null) {
                            downloadUri.clear();
                            downloadUri = fireBaseStorage.UploadFile(chatimageListAdapter.getItem(), chat.getId());
                            List<ChatImageDTO> chatImageInsertDTOS = new ArrayList<ChatImageDTO>();
                            Log.e("qqqqqq", String.valueOf(downloadUri));
                            for (int i = 0; i < downloadUri.size(); i++) {
                                chatImageInsertDTOS.add(new ChatImageDTO(String.valueOf(downloadUri.get(i)), chat.getId(), chatimageListAdapter.getName().get(i)));
                                Log.e("asdasdasdasdasdasdqwe", String.valueOf(chatimageListAdapter.getName().get(i)));
                            }
                            Call<Void> call2 = retrofitRequest.postChat_Image_Multi(chatImageInsertDTOS);
                            call2.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.e("chat_id", String.valueOf(chat.getId()));
                                    Intent intent2 = new Intent();
                                    ChatActivity.this.setResult(RESULT_OK, intent2);
                                    ChatActivity.this.finish();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });
                        } else {
                            Intent intent2 = new Intent();
                            ChatActivity.this.setResult(RESULT_OK, intent2);
                            ChatActivity.this.finish();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ChatDTO>> call, Throwable t) {

            }
        });

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
                String getTime = sdf.format(date);
                chat = new ChatDTO(roomNum, client.getId(), contact.getSomeid(), SendContentText.getText().toString(), getTime);
                Log.e("chat: id -> ", String.valueOf(chat.getId()));
                myRef.child(roomNum).push().setValue(chat);
                SendContentText.setText("");
            }
        });

        myRef.child(roomNum).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
                adapter.add(chatDTO.getSendid() + ": " + chatDTO.getMsg() + "\n " + chatDTO.getDatetime());
                //                ChatImageDTO chatImageDTO = dataSnapshot.getValue(ChatImageDTO.class);
                chatlist.setSelection(adapter.getCount() - 1);

                final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<List<ChatImageDTO>> call1 = retrofitRequest.getChat_image(chat.getId());
                call1.enqueue(new RetrofitRetry<List<ChatImageDTO>>(call1) {
                    @Override
                    public void onResponse(Call<List<ChatImageDTO>> call, Response<List<ChatImageDTO>> response) {
                        chatImageArray = response.body();
                        if (chatImageArray != null) {
                            if (chatImageArray.size() > 0) {
                                for (int i = 0; i < chatImageArray.size(); i++) {
                                    filepath.add(chatImageArray.get(i).getPath());
                                    Log.e("filepath: ", chatImageArray.get(i).getPath());
                                }
                                Log.e("filepath", String.valueOf(filepath));
                                chatImageViewAdapter.imageViewAdapterDown(filepath, chat.getId());

                                viewPager.setAdapter(chatImageViewAdapter);
                                viewPager.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imguri = new ArrayList<Uri>();
//            imguri = chatimageListAdapter.getItem();
//            if (imguri == null) {
                imguri = new ArrayList<Uri>();
//            }
            if (data.getClipData() != null) {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    imguri.add((Uri) data.getClipData().getItemAt(i).getUri());
                }
            }
            if (data.getData() != null) {
                imguri.add((Uri) data.getData());
            }
//            chatimageListAdapter.addItem(imguri);
//            chatlist.setAdapter(chatimageListAdapter);
        }
    }
}
