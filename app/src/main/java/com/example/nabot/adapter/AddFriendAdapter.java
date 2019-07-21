package com.example.nabot.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.activities.AddFriendActivity;
import com.example.nabot.activities.CheckFriendActivity;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendAdapter extends BaseAdapter {
    public ArrayList<ClientDTO> items = new ArrayList<ClientDTO>();
    List<ContactDTO> contactList = null;
    final CheckFriendAdapter checkadapter = new CheckFriendAdapter();
    final CheckFriendActivity checkFriendActivity = new CheckFriendActivity();
    private Context context;
    private Intent intent2;
    ClientDTO client;
    ContactDTO contactDTO;

    public AddFriendAdapter(){

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ClientDTO getItem(int position) {
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
        final Button friendagree = view.findViewById(R.id.friendagree);
        return view;
    }


    public void addItem(ClientDTO client) {
        items.add(client);
    }
    public void clearItem() {
        items.clear();
    }

    public ClientDTO addClientId(){
        return client;
    }
    public ClientDTO getContactDTO(ClientDTO client){
        contactDTO.setSomeid(client.getId());
        return client;
    }

    public void ChangeId(){
        contactDTO.setSomeid(client.getId());
    }

}
