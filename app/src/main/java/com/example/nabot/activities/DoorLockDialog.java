package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.nabot.R;
import com.example.nabot.adapter.VoteAddListAdapter;
import com.example.nabot.domain.VoteDTO;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DoorLockDialog extends Dialog {
    ImageView doorlock;

    private Context context;


    public DoorLockDialog(Context context ,String path) {
        super(context);
        setContentView(R.layout.dialog_doorlock);
        Log.e("path",path);
        ImageView doorklockimg= findViewById(R.id.doorlockimg);
        FirebaseStorage fs = FirebaseStorage.getInstance();
        StorageReference ref = fs.getReference().child( path);
        Glide.with(context)
                .load(ref)
                .into(doorklockimg)
        ;
    }
}
