package com.example.nabot.adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.util.RetrofitRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageViewAdapter extends PagerAdapter {
    private Context mContext = null;
    Uri filepath[]=null;
    int imagecount=0;
    public void getUri(Uri filepath[] , int imagecount){
        this.imagecount=imagecount;
        filepath=new Uri[imagecount];
        for(int i=0; i<imagecount; i++){
            this.filepath[i]=filepath[i];
        }
    }

    public ImageViewAdapter(Context context) {
        mContext = context;
    }


    @Override
    public Object instantiateItem(ViewGroup container ,int position) {
    View view = null;
    if(mContext !=null){
        LayoutInflater inflater=(LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.page,container,false);

        ImageView imageView=(ImageView)view.findViewById(R.id.imgs);
        imageView.setImageResource(R.drawable.logo2);
        if(filepath!=null && imagecount!=0){
            for(int i=0;i<imagecount;i++){
                imageView.setImageURI(filepath[i]);
            }
        }


    }
    container.addView(view);
    return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }

}