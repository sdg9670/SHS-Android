package com.example.nabot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.domain.ClientDTO;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<ClientDTO> items = new ArrayList<ClientDTO>();

    public UserAdapter() {
    }

    public void addItem(ClientDTO user) {
        items.add(user);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ClientDTO getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setText(items.get(position).getName());
        return  text;
    }
}