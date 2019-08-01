package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nabot.R;
import com.example.nabot.adapter.BoardSpinnerAdapter;
import com.example.nabot.adapter.DoorlockViewAdapter;
import com.example.nabot.adapter.WritingListAdapter;
import com.example.nabot.classes.Camera;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.DoorlockDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.FireBaseStorage;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class DoorLockActivity extends AppCompatActivity {
    private ClientDTO client = null;
    private BoardDTO board;
    Button doorlockButton;
    List<DoorlockDTO> doorlockDTOS = null;
    ListView doorlcoklistview;
    DoorlockViewAdapter doorlockViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doorlock);
        Intent intent = getIntent();
        doorlockViewAdapter = new DoorlockViewAdapter(this);
        client = (ClientDTO) intent.getSerializableExtra("client");
        doorlockButton = findViewById(R.id.doorlockButton);
        doorlcoklistview = findViewById(R.id.doorlcoklistview);
        doorlcoklistview.setAdapter(doorlockViewAdapter);

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<DoorlockDTO>> call = retrofitRequest.getdoorlock_image(client.getDong(), client.getHo());
        call.enqueue(new RetrofitRetry<List<DoorlockDTO>>(call) {
            @Override
            public void onResponse(Call<List<DoorlockDTO>> call, Response<List<DoorlockDTO>> response) {
                if (response.body() != null) {
                    doorlockDTOS = new ArrayList<DoorlockDTO>();
                    doorlockDTOS = response.body();
                    for (int i = 0; i < doorlockDTOS.size(); i++) {
                        Log.e("time", doorlockDTOS.get(i).getUpload_time());
                        doorlockViewAdapter.addItem(doorlockDTOS.get(i));
                    }
                    doorlockViewAdapter.notifyDataSetChanged();
                    Log.e("doorlockDTOS", String.valueOf(doorlockDTOS.size()));
                }
            }
        });
        doorlcoklistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoorLockDialog doorLockDialog=new DoorLockDialog(DoorLockActivity.this,doorlockViewAdapter.getpath(position));
                doorLockDialog.show();
            }
        });


    }
}