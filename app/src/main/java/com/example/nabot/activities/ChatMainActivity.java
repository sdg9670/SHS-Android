package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.nabot.R;
import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.util.RetrofitRequest;

public class ChatMainActivity extends AppCompatActivity {
    ClientDTO client;
    Button LoginButton;
    TextView IdText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);
        LoginButton = (Button) findViewById(R.id.loginButton);
        IdText = (TextView) findViewById(R.id.idText);

        //유저
        Intent intent=getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ChatMainActivity.this, FriendListActivity.class);
                intent2.putExtra("id", IdText.getText());
                startActivity(intent2);
            }
        });
    }
}