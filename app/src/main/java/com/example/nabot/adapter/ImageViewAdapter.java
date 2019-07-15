package com.example.nabot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nabot.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends PagerAdapter {
    private Context mContext = null;
    ImageView imageView;
    int writing_id=0;
    List<String>  filepath=new ArrayList<String>();
    public ImageViewAdapter(Context context) {
        mContext = context;
    }



    public void imageViewAdapterDown(List<String> filepath , int writing_id){
        this.writing_id=writing_id;
        this.filepath=filepath;

    }

    @Override
    public Object instantiateItem(final ViewGroup container , int position) {
        View view = null;
        if(mContext !=null){
            if(filepath!=null){
                LayoutInflater inflater=(LayoutInflater)mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.flate_imageview,container,false);
                imageView=(ImageView)view.findViewById(R.id.imgs);

                FirebaseStorage fs=FirebaseStorage.getInstance();
                StorageReference ref=fs.getReference().child(filepath.get(position));
                Log.e("asdasdasdasd", String.valueOf(filepath.get(position)));
                Glide.with(mContext)
                        .load(ref)
                        .into(imageView)
                ;
                container.addView(view);
                notifyDataSetChanged();

            }
        }
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