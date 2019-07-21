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
import com.example.nabot.activities.CheckFriendActivity;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckFriendAdapter extends BaseAdapter {
    public ArrayList<ContactDTO> items = new ArrayList<ContactDTO>();

    public Button agree;
    public Button disagree;
    public CheckFriendAdapter() {

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_check_friend, parent, false);
        final TextView idText = (TextView) view.findViewById(R.id.idText);
        idText.setText(String.valueOf(items.get(position).getSomeid()));
        return view;
    }

    public void addItem(ContactDTO contact) {
        items.add(contact);
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    public void addFreind(ContactDTO contact){
        contact.setNewmsg(contact.getNewmsg());
        items.add(contact);
    }
}
