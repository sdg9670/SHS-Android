package com.example.nabot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.VoteDTO;

import java.util.ArrayList;


public class VoteInsertViewListAdapter extends BaseAdapter {
    private final Context context;
    EditText votetext;
    ArrayList<String> items = new ArrayList<String>();

    public VoteInsertViewListAdapter(Context context) {
        this.context = context;
    }

    public void addItem(String title) {
        items.add(title);
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

    public ArrayList<String> getitems(){
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
        View view = inflater.inflate(R.layout.flate_writingvoteinsert, parent, false);
        TextView vote_index=(TextView)view.findViewById(R.id.vote_insertText);
        TextView vote_insertText = (TextView) view.findViewById(R.id.vote_insertText);
        Log.e("gggggg",vote_index.getText().toString());
        vote_insertText.setText(items.get(position));

        return view;
    }

}