package com.example.nabot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ChatDTO;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
    ArrayList<ChatDTO> items = new ArrayList<>();

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_chat, parent, false);
        final TextView textmsg = (TextView)view. findViewById(R.id.textmsg);
        textmsg.setText(items.get(position).getMsg());
        return convertView;
    }
}
