package com.example.nabot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.nabot.domain.CurtainDTO;

import java.util.ArrayList;

public class CurtainSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    Context context;
    private ArrayList<CurtainDTO> items = new ArrayList<CurtainDTO>();

    public CurtainSpinnerAdapter(Context ctx) {
        context = ctx;
    }
    public void addItem(CurtainDTO data){
        items.add(data);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CurtainDTO getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
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
