package com.example.nabot.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nabot.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class ImageViewAdapterDown extends PagerAdapter {
    private Context mContext = null;
    int writing_id=0;
    List<Uri>  filepath=new ArrayList<Uri>();
    public ImageViewAdapterDown(Context context) {
        mContext = context;
    }

    public void imageViewAdapterDown(List<String> filepath , int writing_id){
        this.writing_id=writing_id;
        for(int i=0; i<filepath.size();i++){
            this.filepath.add(Uri.parse(filepath.get(i)));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container ,int position) {
    View view = null;
    if(mContext !=null){
        if(filepath!=null){
                LayoutInflater inflater=(LayoutInflater)mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.page,container,false);
                ImageView imageView=(ImageView)view.findViewById(R.id.imgs);
               imageView.setImageURI(filepath.get(position));
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
        return filepath.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }

}