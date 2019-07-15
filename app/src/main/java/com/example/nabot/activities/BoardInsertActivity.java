package com.example.nabot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.nabot.R;
import com.example.nabot.adapter.ImageListAdapter;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.domain.WritingImageDTO;
import com.example.nabot.util.FireBaseStorage;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardInsertActivity extends AppCompatActivity {
    TextView board_insert_boardname, board_insert_content;
    Button board_insert_btn;
    EditText board_insert_title;
    List<Uri> imguri= new ArrayList<Uri>();
    WritingDTO writingDTO;
    ImageListAdapter imageListAdapter;
    Button button_img;
    ListView imagelist;
    FireBaseStorage fireBaseStorage = new FireBaseStorage();
    WritingImageDTO writingImageDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardinsert);
        button_img = findViewById(R.id.button_img);
        imageListAdapter = new ImageListAdapter(this, imguri);
        board_insert_boardname = findViewById(R.id.board_insert_boardname);
        board_insert_content = findViewById(R.id.board_insert_content);
        board_insert_btn = findViewById(R.id.board_insert_btn);
        board_insert_title = findViewById(R.id.board_insert_title);
        imagelist=findViewById(R.id.imagelist);
        Intent in = getIntent();
        final ClientDTO clientDTO = (ClientDTO) in.getSerializableExtra("client");
        final BoardDTO boardDTO = (BoardDTO) in.getSerializableExtra("board");
        board_insert_boardname.setText(boardDTO.getName());

        button_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgintent = new Intent();
                imgintent.setType("image/*");
                imgintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                imgintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgintent, "이미지를선택하세요"), 0);
            }
        });

        board_insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                writingDTO = new WritingDTO(board_insert_title.getText().toString(), clientDTO.getId(), board_insert_content.getText().toString(), boardDTO.getId());
                Call<Void> call = retrofitRequest.postWriting(writingDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        RetrofitRequest retrofitRequest1 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                        Call<List<WritingDTO>> call1=retrofitRequest1.getlasat_writing();
                        call1.enqueue(new RetrofitRetry<List<WritingDTO>>(call1) {
                            @Override
                            public void onResponse(Call<List<WritingDTO>> call, Response<List<WritingDTO>> response) {
                                writingDTO = response.body().get(0);
                                if(imageListAdapter.getItem()!=null){
                                    List<String>downloadUri=fireBaseStorage.UploadFile(imageListAdapter.getItem(), writingDTO.getId());
                                    List<WritingImageDTO> writingImages = new ArrayList<WritingImageDTO>();
                                    for(int i=0; i<downloadUri.size();i++) {
                                        Log.e("eff", downloadUri.get(i));
                                        writingImages.add(new WritingImageDTO(String.valueOf(downloadUri.get(i)), writingDTO.getId()));
                                    }
                                    Call<Void>call2=retrofitRequest.postWriting_Image_Multi(writingImages);
                                    call2.enqueue(new Callback<Void>() {
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
                                }else{
                                    Intent intent2 = new Intent();
                                    BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                                    BoardInsertActivity.this.finish();
                                }
                            }
                        });
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
            imguri=imageListAdapter.getItem();
            if(imguri ==null){
                imguri=new ArrayList<Uri>();
            }
            if (data.getClipData() != null) {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        imguri.add((Uri) data.getClipData().getItemAt(i).getUri());
                    }
                }
                if (data.getData() != null) {
                    imguri.add((Uri) data.getData());
                }
            imageListAdapter.addItem(imguri);
                Log.e("imguri", String.valueOf(imguri.size()));
            imagelist.setAdapter(imageListAdapter);
        }
        }
    }
