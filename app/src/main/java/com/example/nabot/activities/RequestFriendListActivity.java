package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.adapter.RequestFriendAdapter;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RequestFriendListActivity extends Activity {
    ClientDTO client;
    ContactDTO contact;
    private int REQUEST_TEST = 1;
    public ListView checklist;
    public RequestFriendAdapter checkadapter = new RequestFriendAdapter();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_friend_list);

        checklist = findViewById(R.id.checklist);

        Intent intent = getIntent();
        client = (ClientDTO) intent.getSerializableExtra("client");
        contact = (ContactDTO) intent.getSerializableExtra("contact");

        refreshFriendList();

    }

    public  void refreshFriendList() {
        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ContactDTO>> call = retrofitRequest.getRequestFriendList(client.getId());
        call.enqueue(new RetrofitRetry<List<ContactDTO>>(call) {
            @Override
            public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                checkadapter.items.clear();
                Log.e("test1", ""+response.body().size());
                if(response.body()!=null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        checkadapter.addItem(response.body().get(i));
                        Log.e("test", ""+response.body().get(i).getSomeid());
                    }
                }

                checklist.setAdapter(checkadapter);
                checkadapter.notifyDataSetChanged();
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
