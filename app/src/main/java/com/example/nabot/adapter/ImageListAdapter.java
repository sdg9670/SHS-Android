package com.example.nabot.adapter;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.util.RetrofitRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListAdapter extends BaseAdapter {
    private final Context context;
    private  List<Uri> imagel=new ArrayList<Uri>();
    ArrayList<String> items = null;
    public ImageListAdapter(Context context, List<Uri> imagel) {
        this.context = context;
        this.imagel = imagel;
    }


    public void addItem( List<Uri> imagel)
    {
        items=new ArrayList<String>();;
        for(int i=0; i <imagel.size(); i++){
            Log.e("itemsss",imagel.get(i).toString().substring(imagel.get(i).toString().lastIndexOf("/")));
            items.add(imagel.get(i).toString().substring(imagel.get(i).toString().lastIndexOf("/")));
        }
        Log.e("a123sd", String.valueOf(imagel));
        Log.e("asdads",String.valueOf(imagel.size()));
    }


    @Override
    public int getCount() {
        Log.e("itemsize", String.valueOf(items.size()));
        return items.size();
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
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_imageinsert, parent, false);
        TextView imagename = (TextView) view.findViewById(R.id.imagename);
        ImageButton imagecancel= (ImageButton)view.findViewById(R.id.imagecancel);
        imagename.setText(String.valueOf(items.get(position)));
        Log.e("asd", String.valueOf(imagel.get(position)));
        imagecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                notifyDataSetChanged();
            }
        });

        return  view;
    }

}
