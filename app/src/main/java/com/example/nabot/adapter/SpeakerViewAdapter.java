package com.example.nabot.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.DoorlockDTO;

import java.util.ArrayList;


public class SpeakerViewAdapter extends BaseAdapter {
    private final Context context;
    ArrayList<ClientDTO> items = new ArrayList<ClientDTO>();

    public SpeakerViewAdapter(Context context) {
        this.context = context;
    }

    public void addItem(ClientDTO clientDTO) {
        items.add(clientDTO);
        notifyDataSetChanged();
    }

    public void clearitem(){
      items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return  items.size();
    }

    public ArrayList<ClientDTO> getitems(){
        return items;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_speaker, parent, false);
        TextView speakertext = (TextView) view.findViewById(R.id.speakertext);
        speakertext.setText(items.get(position).getName());
        return view;
    }

}