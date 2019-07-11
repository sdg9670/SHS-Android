package com.example.nabot.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.nabot.R;
import java.util.ArrayList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class SpeakerListAdapter extends BaseAdapter {
    ArrayList<String> items1 = new ArrayList<String>();
    ArrayList<String> items2 = new ArrayList<String>();
    public SpeakerListAdapter() {

        items1.add("배현지오바");
        items1.add("최우석스피커");
        items1.add("유명선스피커");
        items1.add("심동근스피커");
        items1.add("전우탁스피커");
        items1.add("오승미스피커");
        items2.add("on");
        items2.add("off");
        items2.add("off");
        items2.add("off");
        items2.add("on");
        items2.add("on");
    }

    @Override
    public int getCount() {
        return items1.size();
    }

    @Override
    public Object getItem(int position) {
        return items1.get(position);

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.speakerlistview, parent, false);
        String item1 = items1.get(position);
        String item2 = items2.get(position);
        TextView nameText = (TextView) view.findViewById(R.id.speakername);
        TextView onText = (TextView) view.findViewById(R.id.speakeron);
        nameText.setText(item1);
        onText.setText(item2);
        return view;
    }
}
