package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.adapter.CheckFriendAdapter;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CheckFriendActivity extends Activity {
    ClientDTO client;
    ContactDTO contactDTO;
    Serializable contact;

    ListView checklist;
    Button agree, disagree;
    List<ContactDTO> contactArray = null;
    final CheckFriendAdapter checkadapter = new CheckFriendAdapter();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checklist = findViewById(R.id.checklist);
        agree = findViewById(R.id.agree);
        disagree = findViewById(R.id.disagree);

        Intent intent = getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        contact = intent.getSerializableExtra("contact");

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ContactDTO>> call = retrofitRequest.getFriendCheckList();
        call.enqueue(new RetrofitRetry<List<ContactDTO>>(call) {
            @Override
            public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                contactArray = response.body();
                try {
                    for (int i = 0; i < contactArray.size(); i++) {
                        checkadapter.addItem(contactArray.get(i));
                    }
                    checkadapter.notifyDataSetChanged();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        checklist.setAdapter(checkadapter);


        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("contact",contact);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        disagree.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkadapter.idText.setVisibility(View.GONE);
                checkadapter.agree.setVisibility(View.GONE);
                checkadapter.disagree.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


}
