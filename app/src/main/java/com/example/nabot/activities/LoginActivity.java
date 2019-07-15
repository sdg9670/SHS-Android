package com.example.nabot.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;
import com.google.android.gms.common.api.Api;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getSharedPreferences("login", MODE_PRIVATE).getInt("id", -1);
        if(id != -1)
        {
            RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
            Call<List<ClientDTO>> call = retrofitRequest.getClient(id);
            call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
                @Override
                public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                    if(response.body() != null && response.body().size() != 0) {
                        ClientDTO clientDTO = response.body().get(0);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle =new Bundle();
                        bundle.putSerializable("client",clientDTO);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        else {
            setContentView(R.layout.activity_login);
            Button loginButton = (Button) findViewById(R.id.login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView idText = (TextView) findViewById(R.id.loginID);
                    TextView passwordText = (TextView) findViewById(R.id.loginPassword);
                    String id = idText.getText().toString();
                    String password = passwordText.getText().toString();
                    if (id == null || id == "" || password == null || password == "") {
                        Toast.makeText(LoginActivity.this, "아이디와 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {

                        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                        Call<List<ClientDTO>> call = retrofitRequest.loginCheck(id, password);
                        call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
                            @Override
                            public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {

                                if (response.body() != null && response.body().size() != 0) {
                                    ClientDTO clientDTO = response.body().get(0);
                                    SharedPreferences.Editor seditor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                    seditor.putInt("id", clientDTO.getId());
                                    seditor.commit();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("client", clientDTO);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "잘못된 계정 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
