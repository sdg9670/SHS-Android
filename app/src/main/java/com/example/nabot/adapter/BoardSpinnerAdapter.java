package com.example.nabot.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.nabot.domain.BoardDTO;

import java.util.ArrayList;

public class BoardSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    Context context;
    private ArrayList<BoardDTO> items = new ArrayList<BoardDTO>();

    public BoardSpinnerAdapter(Context ctx) {
        context = ctx;
    }

    public void addItem(BoardDTO board) {
        items.add(board);
    }

    public int getItemPosition(int id) {
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public BoardDTO getItem(int position) {
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
        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setText(items.get(position).getName());
        return text;
    }
}