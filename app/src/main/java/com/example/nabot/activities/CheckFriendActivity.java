package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.adapter.CheckFriendAdapter;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

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

    private View header;
    private LayoutInflater inflater;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_friend);

        agree = findViewById(R.id.agree);
        disagree = findViewById(R.id.disagree);
        backBtn = findViewById(R.id.backBtn);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        checklist = findViewById(R.id.checklist);
        inflater = getLayoutInflater();
        header = inflater.inflate(R.layout.layout_check_friend, null);
        if (header != null) {
            Log.v("00", "00");
        }
        agree = header.findViewById(R.id.agree);
        disagree = header.findViewById(R.id.disagree);

        Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");

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

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ContactDTO>> call = retrofitRequest.getFriendCheckList();
        call.enqueue(new RetrofitRetry<List<ContactDTO>>(call) {
            @Override
            public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                contactArray = response.body();
                checklist.setAdapter(checkadapter);
                for (int i = 0; i < contactArray.size(); i++) {
                    checkadapter.addItem(contactArray.get(i));
                }
                checkadapter.notifyDataSetChanged();
            }
        });
        checkadapter.notifyDataSetChanged();

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.putFriendCheck(contact.getSomeid());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
//                        contactArray.add(contact);
//                        checklist.setAdapter(checkadapter);
//                        checkadapter.notifyDataSetChanged();

                        bundle.putSerializable("contact", contact);
                        intent2.putExtras(bundle);
                        CheckFriendActivity.this.setResult(RESULT_OK, intent2);
                        CheckFriendActivity.this.finish();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contact", contact);
                        intent2.putExtras(bundle);
                        CheckFriendActivity.this.setResult(RESULT_OK, intent2);
                        CheckFriendActivity.this.finish();
//                        Intent intent2 = new Intent();
//                        Bundle bundle = new Bundle();
//                        contactArray.add(contact);
//                        checklist.setAdapter(checkadapter);
//                        checkadapter.notifyDataSetChanged();
//                        bundle.putSerializable("contact", contact);
//                        intent2.putExtras(bundle);
                    }
                });
                checkadapter.notifyDataSetChanged();
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.delFreind(contact.getSomeid());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent();
                        checklist.setAdapter(checkadapter);
                        CheckFriendActivity.this.setResult(RESULT_OK, intent);
                        CheckFriendActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Intent intent = new Intent();
                        checklist.setAdapter(checkadapter);
                        CheckFriendActivity.this.setResult(RESULT_OK, intent);
                        CheckFriendActivity.this.finish();
                    }
                });
                checkadapter.notifyDataSetChanged();
            }
        });
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
                Toast.makeText(this, "친구신청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
