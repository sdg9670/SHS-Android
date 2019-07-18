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
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckFriendActivity extends Activity {
    ClientDTO client;
    ContactDTO contact;
    private int REQUEST_TEST = 1;
    public ListView checklist;
    Button agree, disagree, backBtn, button, button2;
    List<ContactDTO> contactArray = null;
    public CheckFriendAdapter checkadapter = new CheckFriendAdapter();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_friend);
        checklist = findViewById(R.id.checklist);
        agree = findViewById(R.id.agree);
        disagree = findViewById(R.id.disagree);
        backBtn = findViewById(R.id.backBtn);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        Intent intent = getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");

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

        if(checkadapter != null) {
            RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
            Call<List<ContactDTO>> call = retrofitRequest.getFriendCheckList();
            call.enqueue(new RetrofitRetry<List<ContactDTO>>(call) {
                @Override
                public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                    contactArray = response.body();
                    if (contactArray != null) {
                        for (int i = 0; i < contactArray.size(); i++) {
                            checkadapter.addItem(contactArray.get(i));
                        }
                    }
                    checkadapter.notifyDataSetChanged();
                }
            });
            checklist.setAdapter(checkadapter);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_TEST){
            if(resultCode == RESULT_OK){
                checklist.setAdapter(checkadapter);
                checkadapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
