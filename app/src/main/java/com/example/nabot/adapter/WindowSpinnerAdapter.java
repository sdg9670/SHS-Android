package com.example.nabot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.nabot.domain.WindowDTO;

import java.util.ArrayList;

public class WindowSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    Context context;
    private ArrayList<WindowDTO> items = new ArrayList<WindowDTO>();

    public WindowSpinnerAdapter(Context ctx) {
        context = ctx;
    }
    public void addItem(WindowDTO data){
        items.add(data);
    }

    @Override
    public int getCount() {
        return items.size();
    }
    public WindowDTO getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setText(String.valueOf(items.get(position).getId()));
        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setText(String.valueOf(items.get(position).getId()));
        return text;
    }
}

