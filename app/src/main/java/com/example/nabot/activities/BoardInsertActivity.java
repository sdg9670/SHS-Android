package com.example.nabot.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.FireBase;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardInsertActivity extends AppCompatActivity {
    TextView board_insert_boardname, board_insert_content;
    Button board_insert_btn;
    String TAG = "BoardInsertActivity";
    EditText board_insert_title;
    Uri filePath;
    Button button_img;
    ImageView img;
    FireBase fireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardinsert);
        button_img = findViewById(R.id.button_img);
        img = findViewById(R.id.img);
        board_insert_boardname = findViewById(R.id.board_insert_boardname);
        board_insert_content = findViewById(R.id.board_insert_content);
        board_insert_btn = findViewById(R.id.board_insert_btn);
        board_insert_title = findViewById(R.id.board_insert_title);
        Intent in = getIntent();
        final ClientDTO clientDTO = (ClientDTO) in.getSerializableExtra("client");
        final BoardDTO boardDTO = (BoardDTO) in.getSerializableExtra("board");
        board_insert_boardname.setText(boardDTO.getName());

        button_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgintent = new Intent();
                imgintent.setType("image/*");
                imgintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgintent, "이미지를선택하세요"), 0);
            }
        });

        board_insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                WritingDTO writingDTO = new WritingDTO(board_insert_title.getText().toString(), clientDTO.getId(), board_insert_content.getText().toString(), boardDTO.getId());
                Call<Void> call = retrofitRequest.postWriting(writingDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent2 = new Intent();
                        BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                        BoardInsertActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();

            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
                fireBase.UploadFile(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}