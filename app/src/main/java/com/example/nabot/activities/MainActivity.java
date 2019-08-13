package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.classes.Camera;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
    ClientDTO client;
    private long time= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent=getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        Button speakerButton = (Button) findViewById(R.id.speakerButton);
        Button doorlockButton = (Button) findViewById(R.id.doorlockButton);
        Button chatButton = (Button) findViewById(R.id.buttonVote);
        Button boardButton = (Button) findViewById(R.id.boardButton);
        Button logoutButton = (Button) findViewById(R.id.logout);
        Button windowButton = (Button) findViewById(R.id.windowButton);
        Button curtainButton = (Button) findViewById(R.id.curtainButton);
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
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SpeakerActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        doorlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DoorLockActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor seditor = getSharedPreferences("login", MODE_PRIVATE).edit();
                seditor.putInt("id", -1);
                seditor.commit();
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                client.setFcm("널");
                Call<Void> call = retrofitRequest.updateFCM(client);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WindowActivity.class);
                startActivity(intent);
            }
        });
        curtainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CurtainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }

}