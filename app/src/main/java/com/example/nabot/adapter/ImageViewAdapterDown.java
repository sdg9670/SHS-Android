package com.example.nabot.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.nabot.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.http.Url;

public class ImageViewAdapterDown extends PagerAdapter {
    private Context mContext = null;
    ImageView imageView;
    int writing_id=0;
    List<String>  filepath=new ArrayList<String>();
    public ImageViewAdapterDown(Context context) {
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
                view=inflater.inflate(R.layout.page,container,false);
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