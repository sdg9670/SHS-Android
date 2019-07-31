package com.example.nabot.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabot.R;

import java.util.ArrayList;
import java.util.List;

public class ChatImageListAdapter extends BaseAdapter {
    private final Context context;
    private  List<Uri> imagel=new ArrayList<Uri>();
    ArrayList<String> items = null;
    List<String> name =new ArrayList<String>();

    public ChatImageListAdapter(Context context, List<Uri> imagel) {
        this.context = context;
        this.imagel = imagel;
    }
   public void addItem( List<Uri> imagel)
    {
        items=new ArrayList<String>();
        name=new ArrayList<String>();
        for(int i=0; i <imagel.size(); i++){
            items.add(imagel.get(i).toString());
            String[] spl = imagel.get(i).toString().split("/");
            name.add(spl[spl.length-1]);
        }
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

    public  List<Uri> getItem(){
       List<Uri> uri=new ArrayList<Uri>();
       if(items!=null) {
           for (int i = 0; i < items.size(); i++) {
               uri.add(Uri.parse(items.get(i)));
           }
           return uri;
       }
       else{
           return  null;
       }
    }

    public List<String>getName(){
        return name;}

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
        View view = inflater.inflate(R.layout.flate_imagelist, parent, false);
        TextView imagename = (TextView) view.findViewById(R.id.imagename);
        Button imagecancel= (Button)view.findViewById(R.id.imagecancel);
        String[] spl = String.valueOf(items.get(position)).split("/");
        imagename.setText(spl[spl.length-1]);
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
