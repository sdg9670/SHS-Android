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

    ListView checklist;
    Button agree, disagree, backBtn;
    List<ContactDTO> contactArray = null;
    final CheckFriendAdapter checkadapter = new CheckFriendAdapter();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_friend);
        checklist = findViewById(R.id.checklist);
        agree = findViewById(R.id.agree);
        disagree = findViewById(R.id.disagree);
        backBtn = findViewById(R.id.backBtn);

        Intent intent = getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");

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
        if(agree!=null) {
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetrofitRequest retrofitRequest2 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                    contact = new ContactDTO(contact.getSomeid(), client.getName());
                    Call<List<ContactDTO>> call = retrofitRequest2.getFriendCheck(contact.getSomeid());
                    call.enqueue(new Callback<List<ContactDTO>>() {
                        @Override
                        public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                            checkadapter.removeItem(contact);
                            contact = response.body().get(0);
                            checkadapter.notifyDataSetChanged();
                            checklist.setAdapter(checkadapter);
                            Intent intent2 = new Intent();
                            CheckFriendActivity.this.setResult(RESULT_OK, intent2);
                            CheckFriendActivity.this.finish();
                        }
                        @Override
                        public void onFailure(Call<List<ContactDTO>> call, Throwable t) {

                        }
                    });
                }
            });
        }
        if(disagree!=null) {
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
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
