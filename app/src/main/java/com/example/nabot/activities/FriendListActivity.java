package com.example.nabot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.adapter.FriendListAdapter;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendListActivity extends Activity {
    ClientDTO client;
    ContactDTO contact;
    List<ContactDTO> contactArray = null;
    private int REQUEST_TEST = 1;
    ListView friendList;
    Button button, button2;
    final FriendListAdapter ladapter = new FriendListAdapter();
    

    ContactDTO name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendList = (ListView) findViewById(R.id.friendList);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        //유저
        Intent intent=getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        Log.e("intentcheck","1234");

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ContactDTO>> call = retrofitRequest.getFriend();
        call.enqueue(new RetrofitRetry<List<ContactDTO>>(call) {
            @Override
            public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                contactArray = response.body();
                if(contactArray!=null) {
                    for (int i = 0; i < contactArray.size(); i++) {
                        ladapter.addItem(contactArray.get(i));
                    }
                    ladapter.notifyDataSetChanged();
                }
            }
        });
        friendList.setAdapter(ladapter);
        ladapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendListActivity.this, AddFriendActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_TEST);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendListActivity.this, CheckFriendActivity.class);
                //유저정보
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("contact",contact);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_TEST);
            }
        });
        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("client",client);
                bundle.putSerializable("contact", contactArray.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final PopupMenu popupMenu = new PopupMenu(FriendListActivity.this, view);
            getMenuInflater().inflate(R.menu.menu_friend_list, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        Toast.makeText(FriendListActivity.this, ladapter.getItem(position) + " 삭제", Toast.LENGTH_SHORT).show();

                        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                        Call<Void> call = retrofitRequest.delFreind(contact.getSomeid());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent intent = new Intent();
                                FriendListActivity.this.setResult(RESULT_OK, intent);
                                FriendListActivity.this.finish();
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Intent intent = new Intent();
                                FriendListActivity.this.setResult(RESULT_OK, intent);
                                FriendListActivity.this.finish();
                            }
                        });
                        break;
                    }
                return false;
                }
            });
            popupMenu.show();
            return true;
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_TEST){
            if(resultCode == RESULT_OK){
                String friendName = data.getStringExtra("friendName");
                ladapter.items.add(name);
                friendList.setAdapter(ladapter);
                ladapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
