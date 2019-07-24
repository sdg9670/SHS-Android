package com.example.nabot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.util.RetrofitRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFriendAdapter extends BaseAdapter {
    public ArrayList<ContactDTO> items = new ArrayList<ContactDTO>();

    public Button agree;
    public Button disagree;
    public RequestFriendAdapter() {

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
        View view = inflater.inflate(R.layout.layout_request_friend_list, parent, false);
        TextView idText = (TextView) view.findViewById(R.id.idText);
        idText.setText(items.get(position).getSomename() + " ( " + items.get(position).getSomeidname() + " )");
        TextView idText2 = (TextView) view.findViewById(R.id.idText2);
        idText2.setText(items.get(position).getSomedong() + "동 " + items.get(position).getSomeho() + "호");
        Button ok = (Button) view.findViewById(R.id.agree);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> calls = retrofitRequest.postFriend(new ContactDTO(items.get(position).getClientid(), items.get(position).getSomeidname()));
                calls.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        items.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        Button cancle = (Button) view.findViewById(R.id.disagree);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> calls = retrofitRequest.delRequestFreind(items.get(position).getClientid(), items.get(position).getSomeid());
                calls.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        items.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

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
