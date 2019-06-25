package com.example.nabot.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.activities.BoardViewActivity;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

public class WritingListAdapter extends BaseAdapter {
    ArrayList<WritingDTO> items = new ArrayList<WritingDTO>();

    public WritingListAdapter() {
    }

    public void addItem(WritingDTO writing) {
        items.add(writing);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public WritingDTO getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        items.remove(position);
    }
    public void clearItem() {
        items.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_writinglist, parent, false);
        TextView tv_title = (TextView) view.findViewById(R.id.writinglist_title);
        final TextView tv_writer = (TextView) view.findViewById(R.id.writinglist_writer);
        TextView tv_write_time = (TextView) view.findViewById(R.id.writinglist_write_time);
        TextView commentCount = (TextView) view.findViewById(R.id.commentCount);
        WritingDTO item = items.get(position);
        tv_title.setText(item.getTitle());
        tv_write_time.setText(item.getUpdate_time());
        tv_writer.setText(item.getWriter_name());
        commentCount.setText(Integer.toString(item.getComment_count()));
        return view;
    }
}