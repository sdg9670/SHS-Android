package com.example.nabot.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.WritingImageDTO;

import java.util.ArrayList;
import java.util.List;


public class ImageListAdapterModify extends BaseAdapter {
    private final Context context;
    ArrayList<String> items = null;
    List<WritingImageDTO> writingImgArray =new ArrayList<WritingImageDTO>();
    int id=0;
    List<Uri> uri= null;
    List<String> name =new ArrayList<String>();
    List<WritingImageDTO> destroyid=new ArrayList<WritingImageDTO>();

    public  List<WritingImageDTO> getDestroyposition(){
        if(destroyid!=null)
        return destroyid;
        else
            return null;
    }
    public ImageListAdapterModify(Context context  , int id ,List<WritingImageDTO> writingImgArray) {
        this.context = context;
        this.id=id;
        this.writingImgArray=writingImgArray;
    }

    public void setItems(List<WritingImageDTO> writingImgArray )
    {
        items=new ArrayList<String>();
        for(int i=0; i <writingImgArray.size(); i++){
            items.add(writingImgArray.get(i).getName());
        }
    }

    public  void addItems(List<Uri> uri){
        this.uri=new ArrayList<Uri>();
        this.uri=uri;
        for(int i=0; i<uri.size();i++){
            items.add(uri.get(i).toString());
            String[] spl = uri.get(i).toString().split("/");
            name.add(spl[spl.length-1]);
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public Uri getItem(int position) {
        notifyDataSetChanged();
        return Uri.parse(items.get(position));
    }


    public List<String>getName(){
        notifyDataSetChanged();
        return name;}

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        name.remove(position);
        items.remove(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_imagelist, parent, false);
        TextView imagename = (TextView) view.findViewById(R.id.imagename);
        ImageButton imagecancel= (ImageButton)view.findViewById(R.id.imagecancel);

        if(writingImgArray!=null && items.size()<=writingImgArray.size()) {
            imagename.setText(writingImgArray.get(position).getName());
        }
        if(items.size()>writingImgArray.size() && uri!=null){
            String[] spl = String.valueOf(items.get(position)).split("/");
            imagename.setText(spl[spl.length-1]);
        }

        imagecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    destroyid.add(writingImgArray.get(position));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                writingImgArray.remove(position);
                removeItem(position);
                notifyDataSetChanged();
            }
        });
        return  view;
    }

}