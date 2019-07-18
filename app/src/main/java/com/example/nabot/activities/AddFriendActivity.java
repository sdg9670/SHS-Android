package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    Button agree, button, button2, backBtn;
    ListView friendlistview;
    List<ClientDTO> contactArray = null;
    CheckFriendActivity checkFriendActivity = new CheckFriendActivity();
    private int REQUEST_TEST = 1;
    final AddFriendAdapter friendAdapter = new AddFriendAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        friendlistview = findViewById(R.id.friendlistview);
        agree = findViewById(R.id.agree);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        backBtn = findViewById(R.id.backBtn);
        final Intent in = getIntent();
        in.getStringExtra("check");

        Intent intent = getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        contact= (ContactDTO)intent.getSerializableExtra("contact");

        if(friendAdapter!=null){
            RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
            Call<List<ClientDTO>> call = retrofitRequest.getFriendList();
            call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
                 @Override
                 public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                     contactArray = response.body();
                     if(contactArray!=null) {
                         for (int i = 0; i < contactArray.size(); i++) {
                             friendAdapter.addItem(contactArray.get(i));
                         }
                     }
                     friendAdapter.notifyDataSetChanged();
                 }
             });
        }
        friendlistview.setAdapter(friendAdapter);
        friendAdapter.notifyDataSetChanged();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFriendActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("contact",contact);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_TEST);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckFriendActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("contact",contact);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_TEST);
            }
        });

        if(agree != null) {
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                RetrofitRequest retrofitRequest2 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                contact = new ContactDTO(contact.getSomeid());
                Call<List<ContactDTO>> call = retrofitRequest2.postFriend(contact);
                call.enqueue(new Callback<List<ContactDTO>>() {
                    @Override
                    public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                        contact=response.body().get(0);

                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.getSerializable("client");
                        bundle.getSerializable("contact");
                        contact.setSomeid(client.getId());
                        friendAdapter.addItem(friendAdapter.getContactDTO());
                        friendAdapter.notifyDataSetChanged();
                        friendlistview.setSelection(friendAdapter.getCount()-1);
                    }
                    @Override
                    public void onFailure(Call<List<ContactDTO>> call, Throwable t) {

                    }
                });
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_TEST){
            if(resultCode == RESULT_OK){
                friendlistview.setAdapter(friendAdapter);
                friendAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}