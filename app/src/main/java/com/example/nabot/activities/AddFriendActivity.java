package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nabot.R;
import com.example.nabot.adapter.AddFriendAdapter;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends Activity {
    ClientDTO client;
    ContactDTO contact;
    EditText addFriend;
    Button agree,cenBtn;
    ListView friendlistview;
    List<ClientDTO> contactArray = null;
    final AddFriendAdapter friendAdapter = new AddFriendAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        addFriend = findViewById(R.id.addFriendEdit);
        friendlistview = findViewById(R.id.friendlistview);
        agree = findViewById(R.id.agree);
        cenBtn = findViewById(R.id.cenBtn);
        final Intent in = getIntent();
        in.getStringExtra("check");

        Intent intent = getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        contact= (ContactDTO)intent.getSerializableExtra("contact");

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ClientDTO>> call = retrofitRequest.getFriendList();
        call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
             @Override
             public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                 if(contactArray!=null) {
                     contactArray = response.body();
                     for (int i = 0; i < contactArray.size(); i++) {
                         friendAdapter.addItem(contactArray.get(i));
                     }
                     friendAdapter.notifyDataSetChanged();
                 }
             }
         });
        friendlistview.setAdapter(friendAdapter);
        friendAdapter.notifyDataSetChanged();

        if(agree != null) {
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                contact = new ContactDTO(contact.getSomeid(), client.getName());
                Call<List<ContactDTO>> call = retrofitRequest.postFriend(contact);
                call.enqueue(new Callback<List<ContactDTO>>() {
                    @Override
                    public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                        contact=response.body().get(0);
                        friendAdapter.notifyDataSetChanged();
                        friendlistview.setAdapter(friendAdapter);
                        Intent intent2 = new Intent();
                        AddFriendActivity.this.setResult(RESULT_OK, intent2);
                        AddFriendActivity.this.finish();
                    }
                    @Override
                    public void onFailure(Call<List<ContactDTO>> call, Throwable t) {

                    }
                });
                }
            });
        }
        cenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}