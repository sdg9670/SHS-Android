package com.example.nabot.activities;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.ImageViewAdapter;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.FireBaseStorage;
import com.example.nabot.util.RetrofitRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardInsertActivity extends AppCompatActivity {
    TextView board_insert_boardname, board_insert_content;
    Button board_insert_btn;
    EditText board_insert_title;
    Uri multiuri[]=null;
    Uri singleuri=null;
    WritingDTO writingDTO;
    Button button_img;
    Bitmap bitmap[];
    int imgcount=0;
    FireBaseStorage fireBaseStorage =new FireBaseStorage();
    ViewPager viewPager;
    ImageViewAdapter imageViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardinsert);
        viewPager=findViewById(R.id.viewPager);
        imageViewAdapter=new ImageViewAdapter(this);
        button_img = findViewById(R.id.button_img);
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
                imgintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                imgintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgintent, "이미지를선택하세요"), 0);
            }
        });

        board_insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                 writingDTO = new WritingDTO(board_insert_title.getText().toString(), clientDTO.getId(), board_insert_content.getText().toString(), boardDTO.getId());
                Call<List<WritingDTO>> call = retrofitRequest.postWriting(writingDTO);
                call.enqueue(new Callback<List<WritingDTO>>() {
                    @Override
                    public void onResponse(Call<List<WritingDTO>> call, Response<List<WritingDTO>> response) {
                        writingDTO=response.body().get(0);
                        Intent intent2 = new Intent();
                        BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                        BoardInsertActivity.this.finish();
                    }
                    @Override
                    public void onFailure(Call<List<WritingDTO>> call, Throwable t) {

                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imgcount=data.getClipData().getItemCount();
            multiuri=new Uri[imgcount];
            for(int i=0; i<imgcount; i++){
                multiuri[i]=data.getClipData().getItemAt(i).getUri();
                Log.e("multiuri", String.valueOf(multiuri[i]));
                Log.e("imgcount", String.valueOf(imgcount));
            }
            imageViewAdapter.getUri(multiuri,imgcount);
            viewPager.setAdapter(imageViewAdapter);

            }
        }
    }



///file path = uri 역할