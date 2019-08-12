package com.example.nabot.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ChatDTO;
import com.example.nabot.domain.ContactDTO;

import java.util.ArrayList;

public class ChatAdapterR extends BaseAdapter { ArrayList<ChatDTO> items = new ArrayList<>();
//    ArrayList<String> item = new ArrayList<>();

    ContactDTO contactDTO =null;

    public void getDTO(ContactDTO contactDTO){
        this.contactDTO = new ContactDTO();
        this.contactDTO = contactDTO;
        Log.e("asdasqe", String.valueOf(contactDTO.getClientid()));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ChatDTO getItem(int position) {
        Log.e("asdadasdzz", String.valueOf(items.get(position)));
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_chat_r, parent, false);
        final TextView textmsg = (TextView) view.findViewById(R.id.textmsg);
        textmsg.setText(items.get(position).getSendname() + " : " + items.get(position).getMsg() + "\n" + items.get(position).getDatetime());

        if(contactDTO!=null) {
            Log.e("로로로", items.get(position).getSendname() + " " + items.get(position).getRecvname() + " " + contactDTO.getSomename());
            if (items.get(position).getRecvname().equals(contactDTO.getSomename())) {
                Log.e("롸", items.get(position).getMsg());
                textmsg.setGravity(Gravity.RIGHT);
            } else {
                Log.e("좌", items.get(position).getMsg());
                textmsg.setGravity(Gravity.LEFT);

            }
        }
        return view;
    }

    public void addItem(ChatDTO chat) {
        items.add(chat);


    }
}
