package com.example.nabot.adapter;

import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ChatDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.ContactDTO;

import java.util.ArrayList;

import static android.util.Log.e;

public class ChatAdapterR extends BaseAdapter {
    ArrayList<ChatDTO> items = new ArrayList<>();
    ArrayList<String> item = new ArrayList<>();
    TextView textmsg;

    ContactDTO contactDTO =null;
    ClientDTO clientDTO = null;

    public void getDTO(ContactDTO contactDTO, ClientDTO clientDTO){
        this.contactDTO = new ContactDTO();
        this.clientDTO = new ClientDTO();
        this.contactDTO = contactDTO;
        this.clientDTO = clientDTO;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ChatDTO getItem(int position) {
        e("asdadasdzz", String.valueOf(items.get(position)));
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
        textmsg = (TextView) view.findViewById(R.id.textmsg);
        textmsg.setText(items.get(position).getSendname() + " : " + items.get(position).getMsg() + "\n" + items.get(position).getDatetime());

        if(contactDTO!=null) {
            Log.e("getRecvid", String.valueOf(items.get(position).getRecvid()));
            Log.e("getSomeid",String.valueOf(contactDTO.getSomeid()));

            if (items.get(position).getRecvid() == contactDTO.getSomeid()) {
                textmsg.setGravity(Gravity.RIGHT);
                textmsg.setBackgroundResource(android.R.color.holo_blue_light);

            } else{
                textmsg.setGravity(Gravity.LEFT);
                textmsg.setBackgroundResource(android.R.color.holo_orange_light);

            }
        }

        return view;
    }

    public void addItem(ChatDTO chat) {
        Log.e("asdasdsd","asdasdasdasd");
//        if(clientDTO.getName() == contactDTO.getSomename()) {
//           textmsg.setGravity(Gravity.RIGHT);
//            textmsg.setBackgroundResource(android.R.color.holo_blue_light);
//            items.add(chat);
//        }else{
//            textmsg.setGravity(Gravity.LEFT);
//            textmsg.setBackgroundResource(android.R.color.holo_blue_light);
//            items.add(chat);
//        }
        items.add(chat);
    }
}
