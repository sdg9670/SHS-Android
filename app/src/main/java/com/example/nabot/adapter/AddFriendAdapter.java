package com.example.nabot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.google.android.gms.common.api.Api;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddFriendAdapter extends BaseAdapter {
    public ArrayList<ClientDTO> items = new ArrayList<ClientDTO>();
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
        TextView idText = view.findViewById(R.id.idText);
        idText.setText((items.get(position).getId())+(items.get(position).getName()));
        return view;
    }
    public void addItem(ClientDTO client) {
        items.add(client);
    }
}
