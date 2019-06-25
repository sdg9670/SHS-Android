package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardModifyActivity extends AppCompatActivity {
    TextView board_modify_boardname;
    Button board_modify_btn;
    EditText board_modify_title, board_modify_content;
    WritingDTO writingDTO;
    BoardDTO boardDTO;
    ClientDTO clientDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardmodify);
        board_modify_boardname = findViewById(R.id.board_modify_boardname);
        board_modify_content = findViewById(R.id.board_modify_content);
        board_modify_btn = findViewById(R.id.board_modify_btn);
        board_modify_title = findViewById(R.id.board_modify_title);
        Intent intent = getIntent();
        clientDTO = (ClientDTO) intent.getSerializableExtra("client");
        writingDTO = (WritingDTO) intent.getSerializableExtra("writing");
        boardDTO = (BoardDTO) intent.getSerializableExtra("board");

        board_modify_boardname.setText(boardDTO.getName());
        board_modify_content.setText(writingDTO.getContent());
        board_modify_title.setText(writingDTO.getTitle());
        board_modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                writingDTO = new WritingDTO(writingDTO.getId(), board_modify_title.getText().toString(), board_modify_content.getText().toString());
                Call<Void> call = retrofitRequest.putWriting(writingDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("writing", writingDTO);
                        intent2.putExtras(bundle);
                        BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                        BoardModifyActivity.this.finish();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("writing", writingDTO);
                        intent2.putExtras(bundle);
                        BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                        BoardModifyActivity.this.finish();
                    }
                });
            }
        });


    }
}