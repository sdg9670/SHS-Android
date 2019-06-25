package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nabot.R;
import com.example.nabot.classes.Camera;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ClientDTO client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ClientDTO>> call = retrofitRequest.getClient(3);
        call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
            @Override
            public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                client = response.body().get(0);
            }
        });



        setContentView(R.layout.activity_main);
        Button speakerButton = (Button) findViewById(R.id.speakerButton);
        Button doorlockButton = (Button) findViewById(R.id.doorlockButton);
        Button boardButton = (Button) findViewById(R.id.boardButton);
        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera camera = new Camera("Speaker", "192.168.1.100", 6000);
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(VideoActivity.CAMERA, camera);
                startActivity(intent);
            }
        });
        doorlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera camera = new Camera("DoorLock", "192.168.1.101", 6001);
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(VideoActivity.CAMERA, camera);
                startActivity(intent);
            }
        });

    }
}
