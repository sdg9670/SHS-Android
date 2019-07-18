package com.example.nabot.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendAdapter extends BaseAdapter {
    public ArrayList<ContactDTO> items = new ArrayList<ContactDTO>();
    List<Void> contactList = null;
    Button agree;

    final Intent intent2 = new Intent();
    final ClientDTO client= (ClientDTO)intent2.getSerializableExtra("client");
    final ContactDTO contactDTO= (ContactDTO)intent2.getSerializableExtra("contact");

    public AddFriendAdapter() {
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ContactDTO getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_add_friend, parent, false);
        final TextView idText = view.findViewById(R.id.idText);
        idText.setText(String.valueOf(items.get(position).getId()));


        agree  = (Button) view.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitRequest retrofitRequest2 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<List<ContactDTO>> call = retrofitRequest2.postFriend(contactDTO);
                call.enqueue(new Callback<List<ContactDTO>>() {
                    @Override
                    public void onResponse(Call<List<ContactDTO>> call, Response<List<ContactDTO>> response) {
                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
                        contactDTO.setSomeid(client.getId());
                        bundle.putSerializable("client",client);
                        bundle.putSerializable("contact",contactDTO);
                        intent2.putExtras(bundle);
                    }
                    @Override
                    public void onFailure(Call<List<ContactDTO>> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }


    public void addItem(ContactDTO contact) {
        items.add(contact);
    }
    public void clearItem() {
        items.clear();
    }
    public ContactDTO getContactDTO (){
        return contactDTO;
    }

}
