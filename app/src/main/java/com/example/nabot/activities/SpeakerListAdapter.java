package com.example.nabot.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.R;

import java.util.ArrayList;

public class SpeakerListAdapter extends BaseAdapter {

    ArrayList<String> items = new ArrayList<String>();
    public SpeakerListAdapter() {

        items.add("배현지스피커, on");
        items.add("최우석스피커");
        items.add("유명선스피커");
        items.add("ㅂㅈ");
        items.add("ㄹㄹ");
        items.add("ㅎㅎ");
        items.add("ㅍㅍ");
        items.add("ㅇㄴ");
        items.add("ㄴㅇ");
        items.add("ㅁㅂ");
        items.add("ㅂㅈㄷㄳ");
        items.add("ㅎㅅㅈ슈ㅠ");
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.speakerlistview, parent, false);
        String item = items.get(position);
        TextView nameText = (TextView) view.findViewById(R.id.speakername);
        nameText.setText(item);
        return null;
    }
}
