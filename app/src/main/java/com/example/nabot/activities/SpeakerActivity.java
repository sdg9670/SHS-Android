package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.nabot.R;
import com.example.nabot.adapter.DoorlockViewAdapter;
import com.example.nabot.adapter.SpeakerViewAdapter;
import com.example.nabot.classes.Camera;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.DoorlockDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class SpeakerActivity extends AppCompatActivity {
    private ClientDTO client = null;
    private BoardDTO board;
    Button remoteCommandButton;
    List<ClientDTO> speakers = null;
    ListView speakerlist;
    SpeakerViewAdapter speakerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        Intent intent = getIntent();
        speakerViewAdapter = new SpeakerViewAdapter(this);
        client = (ClientDTO) intent.getSerializableExtra("client");
        remoteCommandButton = findViewById(R.id.remoteCommandButton);
        speakerlist = findViewById(R.id.speakerlist);
        speakerlist.setAdapter(speakerViewAdapter);


        remoteCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera camera = new Camera("Speaker", "192.168.1.100", 5000);
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                intent.putExtra(VideoActivity.CAMERA, camera);
                startActivity(intent);
            }
        });

        RetrofitRequest retrofitRequest=RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ClientDTO>> call=retrofitRequest.getspeaker(client.getDong(),client.getHo());
        call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
            @Override
            public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                if (response.body() != null) {
                    speakers = new ArrayList<ClientDTO>();
                    speakers = response.body();
                    for (int i = 0; i < speakers.size(); i++) {
                        speakerViewAdapter.addItem(speakers.get(i));
                    }
                    speakerViewAdapter.notifyDataSetChanged();
                }
            }
        });


    }
}