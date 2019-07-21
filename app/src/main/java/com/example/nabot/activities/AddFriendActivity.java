package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.adapter.AddFriendAdapter;
import com.example.nabot.adapter.CheckFriendAdapter;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends Activity {
    ClientDTO client;
    ContactDTO contact;
    Button friendrequest, cenBtn, button, button2;
    ListView friendlistview;
    List<ClientDTO> contactArray = null;
    AddFriendAdapter friendAdapter = new AddFriendAdapter();

    CheckFriendActivity checkFriendActivity;
    CheckFriendAdapter checkFriendAdapter = new CheckFriendAdapter();

    private int REQUEST_TEST = 1;

    private View header, header2;
    private LayoutInflater inflater;
    private ListView checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        friendlistview = findViewById(R.id.friendlistview);

        cenBtn = findViewById(R.id.backBtn);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        final Intent in = getIntent();
        in.getStringExtra("check");

        inflater = getLayoutInflater();
        header = inflater.inflate(R.layout.layout_add_friend, null);
        header2 = inflater.inflate(R.layout.activity_check_friend, null);

        if(header != null) {Log.v("00","00");}
        friendrequest = header.findViewById(R.id.friendagree);
        final ListView checklist = header2.findViewById(R.id.checklist);

        final Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ClientDTO>> call = retrofitRequest.getFriendList();
        call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
            @Override
            public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                contactArray = response.body();
                friendlistview.setAdapter(friendAdapter);
                for (int i = 0; i < contactArray.size(); i++) {
                    friendAdapter.addItem(contactArray.get(i));
                }
                friendAdapter.notifyDataSetChanged();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFriendActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_TEST);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckFriendActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_TEST);
            }
        });

        friendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            contact = new ContactDTO(client.getId());
            RetrofitRequest retrofitRequest2 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
            final Call<List<ContactDTO>> call = retrofitRequest2.postFriend(contact);
            call.enqueue(new Callback<List<ContactDTO>>() {
                Intent intent2 = new Intent();
                @Override
                public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                    contact.setSomeid(client.getId());
                    checklist.setAdapter(checkFriendAdapter);
                    checkFriendAdapter.addItem(contact);
                    checkFriendAdapter.notifyDataSetInvalidated();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contact", contact);
                    bundle.putSerializable("client", client);
                    intent2.putExtras(bundle);
                    startActivityForResult(intent2, REQUEST_TEST);
                }

                @Override
                public void onFailure(Call<List<ContactDTO>> call, Throwable t) {
                    contact.setSomeid(client.getId());
                    checklist.setAdapter(checkFriendAdapter);
                    checkFriendAdapter.addItem(contact);
                    checkFriendAdapter.notifyDataSetInvalidated();
                }
            });
          }
        });
        checklist.setAdapter(checkFriendAdapter);
        checkFriendAdapter.addItem(contact);
        checkFriendAdapter.notifyDataSetInvalidated();
        cenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_TEST) {
            if (resultCode == RESULT_OK) {
                checklist.setAdapter(checkFriendAdapter);
                checkFriendAdapter.addItem(contact);
                checkFriendAdapter.notifyDataSetInvalidated();
            } else {
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
