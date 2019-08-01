package com.example.nabot.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.DoorlockDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class DoorlockViewAdapter extends BaseAdapter {
    private final Context context;
    ArrayList<DoorlockDTO> items = new ArrayList<DoorlockDTO>();

    public DoorlockViewAdapter(Context context) {
        this.context = context;
    }

    public void addItem(DoorlockDTO doorlockDTO) {
        items.add(doorlockDTO);
        notifyDataSetChanged();
    }
    public  String getpath(int position){
       return items.get(position).getPath();
    }

    public void clearitem(){
      items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return  items.size();
    }

    public ArrayList<DoorlockDTO> getitems(){
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
        View view = inflater.inflate(R.layout.flate_doorlockview, parent, false);
        TextView doorlocktext = (TextView) view.findViewById(R.id.doorlocktext);
        doorlocktext.setText("\nupdate_time:  "+items.get(position).getUpload_time()
                +"                      "+"                "+items.get(position).getState()+"\n");
        return view;
    }

}