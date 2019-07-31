package com.example.nabot.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.activities.BoardModifyActivity;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends BaseAdapter {
    private final Context context;
    private final ClientDTO clientDTO;
    public CommentListAdapter(Context context, ClientDTO user) {
        this.context = context;
        this.clientDTO = user;
    }

    ArrayList<CommentDTO> items = new ArrayList<CommentDTO>();

    public void addItem(CommentDTO comment) {
        items.add(comment);
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
    public CommentDTO getItem(int position) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_comment, parent, false);

        TextView commentlist_content = (TextView) view.findViewById(R.id.commentlist_content);
        TextView comment_writer = (TextView) view.findViewById(R.id.comment_writer);
        TextView content_write_time = (TextView) view.findViewById(R.id.content_write_time);
        Button comment_modify_btn = (Button) view.findViewById(R.id.comment_modify_btn);
        Button comment_delete_btn = (Button) view.findViewById(R.id.comment_delete_btn);
        final EditText comment_modfiy_text=(EditText)view.findViewById(R.id.comment_modfiy_text);
        final CommentDTO item = items.get(position);

        commentlist_content.setText(item.getContent());

        if(item.getWriter() != clientDTO.getId()) {
            comment_modify_btn.setVisibility(View.GONE);
            comment_delete_btn.setVisibility(View.GONE);
        }

        content_write_time.setText(item.getUpdate_time());
        commentlist_content.setText(item.getContent());
        comment_writer.setText(item.getWriter_name());

        comment_modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_comment_edit);
                final EditText modify_text = dialog.findViewById(R.id.comment_modfiy_text);
                Button cd_cancle= (Button)dialog.findViewById(R.id.cd_cancle);
                Button cd_modify= (Button)dialog.findViewById(R.id.cd_modify);
                modify_text.setText(item.getContent());
                dialog.show();
                System.out.println(""+modify_text.getText().toString());
                cd_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    item.setContent(modify_text.getText().toString());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Timestamp ts = Timestamp.valueOf(formatter.format(new Date()));
                    item.setUpdate_time(ts.toString());
                    notifyDataSetChanged();
                    RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                    Call<Void> call = retrofitRequest.putComment(item);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            dialog.dismiss();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
                    }
                });
                cd_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        comment_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.deleteComment(item.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        removeItem(position);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        removeItem(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }
}