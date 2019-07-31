package com.example.nabot.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendList = (ListView) findViewById(R.id.friendList);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.buttonVote);

        //유저
        Intent intent=getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        contact = (ContactDTO)intent.getSerializableExtra("contact");
        Log.e("intentcheck","1234");

        refreshFriendList();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(FriendListActivity.this);
                dialog.setContentView(R.layout.dialog_addfriend);
                Button ok = (Button) dialog.findViewById(R.id.add_friend_ok);
                Button cancle = (Button) dialog.findViewById(R.id.add_friend_cancle);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText idname = dialog.findViewById(R.id.add_friend_text);
                        Log.e("체크", client.getId_name() + ": " + idname.getText().toString());
                        if(idname.getText().toString() == null || client.getId_name().equals(idname.getText().toString()))
                        {
                            Toast.makeText(FriendListActivity.this, "추가할 수 없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return ;
                        }
                        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                        Call<List<ContactDTO>> calls = retrofitRequest.getFriendCheck2(idname.getText().toString());
                        calls.enqueue(new RetrofitRetry<List<ContactDTO>>(calls) {
                            @Override
                            public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                                if(response.body().size() == 0)
                                    Toast.makeText(FriendListActivity.this, "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                else {
                                    EditText idname = dialog.findViewById(R.id.add_friend_text);
                                    RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                                    Call<List<ContactDTO>> calls = retrofitRequest.getFriendCheck(client.getId(), idname.getText().toString());
                                    calls.enqueue(new RetrofitRetry<List<ContactDTO>>(calls) {
                                        @Override
                                        public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                                            if(response.body().size() > 0)
                                                Toast.makeText(FriendListActivity.this, "이미 친구 요청한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                            else {
                                                EditText idname = dialog.findViewById(R.id.add_friend_text);
                                                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                                                Call<Void> calls = retrofitRequest.postFriend(new ContactDTO(client.getId(), idname.getText().toString()));
                                                calls.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        refreshFriendList();
                                                        dialog.dismiss();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {

                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendListActivity.this, RequestFriendListActivity.class);
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
                        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                        //수정할것
                        Call<Void> call = retrofitRequest.delFreind(client.getId(), ladapter.getItem(position).getSomeid());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                ladapter.removeItem(position);
                                ladapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TEST)
        {
            if (resultCode == RESULT_OK) {
            } else if (resultCode == RESULT_CANCELED) {
                refreshFriendList();
            }
        }
    }

    public  void refreshFriendList() {
        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ContactDTO>> call = retrofitRequest.getFriend(client.getId());
        call.enqueue(new RetrofitRetry<List<ContactDTO>>(call) {
            @Override
            public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                ladapter.items.clear();
                contactArray = response.body();
                Log.e("test1", ""+contactArray.size());
                if(contactArray!=null) {
                    for (int i = 0; i < contactArray.size(); i++) {
                        ladapter.addItem(contactArray.get(i));
                        Log.e("test", ""+contactArray.get(i).getSomeid());
                    }
                }

                friendList.setAdapter(ladapter);
                ladapter.notifyDataSetChanged();
            }
        });
    }
}
