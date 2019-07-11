package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
                         client.setId(client.getId());
                         client.setName(client.getName());
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
                    Class<List<ContactDTO>> call = retrofitRequest.getFriendCheck(contact.getSomeid());
                    Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("client", client);
                    bundle.putSerializable("contact", contact);
                    intent.putExtras(bundle);
                    Log.e("Friendid", addFriend.getText().toString());
                    //intent.putExtra("friendName",addFriend.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
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