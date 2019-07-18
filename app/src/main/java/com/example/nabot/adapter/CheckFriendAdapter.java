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
    private ContactDTO contact;
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
        agree = view.findViewById(R.id.agree);
        disagree = view.findViewById(R.id.disagree);
        idText.setText(String.valueOf(items.get(position).getSomeid()));
        final ContactDTO item = items.get(position);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.putFriendCheck(item.getSomeid());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contact",contact);
                        intent2.putExtras(bundle);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.delFreind(item.getSomeid());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        removeItem(position);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        removeItem(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return view;
    }

    public void addItem(ContactDTO contact) {
        items.add(contact);
    }

    public void removeItem(int position) {
        items.remove(position);
    }
}
