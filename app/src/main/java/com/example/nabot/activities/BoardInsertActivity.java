package com.example.nabot.activities;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nabot.R;
import com.example.nabot.adapter.ImageViewAdapter;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.domain.WritingImageDTO;
import com.example.nabot.util.FireBaseStorage;
import com.example.nabot.util.RetrofitRequest;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardInsertActivity extends AppCompatActivity {
    TextView board_insert_boardname, board_insert_content;
    Button board_insert_btn;
    EditText board_insert_title;
    List<Uri> multiUri =null;
    Uri singleuri = null;
    WritingDTO writingDTO;
    Button button_img;
    ImageView singleimg;
    int imgcount = 0;
    FireBaseStorage fireBaseStorage = new FireBaseStorage();
    ViewPager viewPager;
    ImageViewAdapter imageViewAdapter;
    WritingImageDTO writingImageDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardinsert);
        viewPager = findViewById(R.id.viewPager);
        imageViewAdapter = new ImageViewAdapter(this);
        button_img = findViewById(R.id.button_img);
        board_insert_boardname = findViewById(R.id.board_insert_boardname);
        board_insert_content = findViewById(R.id.board_insert_content);
        board_insert_btn = findViewById(R.id.board_insert_btn);
        board_insert_title = findViewById(R.id.board_insert_title);
        singleimg = findViewById(R.id.singleimg);
        Intent in = getIntent();
        final ClientDTO clientDTO = (ClientDTO) in.getSerializableExtra("client");
        final BoardDTO boardDTO = (BoardDTO) in.getSerializableExtra("board");
        board_insert_boardname.setText(boardDTO.getName());

        button_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiUri=new ArrayList<Uri>();
                singleuri=null;
                Intent imgintent = new Intent();
                imgintent.setType("image/*");
                imgintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                imgintent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(imgintent, "이미지를선택하세요"), 0);
            }
        });

        board_insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                writingDTO = new WritingDTO(board_insert_title.getText().toString(), clientDTO.getId(), board_insert_content.getText().toString(), boardDTO.getId());
                Call<Void> call = retrofitRequest.postWriting(writingDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        RetrofitRequest retrofitRequest1 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                        Call<List<WritingDTO>> call1 = retrofitRequest1.getlasat_writing();
                        call1.enqueue(new Callback<List<WritingDTO>>() {
                            @Override
                            public void onResponse(Call<List<WritingDTO>> call, Response<List<WritingDTO>> response) {
                                writingDTO = response.body().get(0);
                                RetrofitRequest retrofitRequest2 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                                if (multiUri != null) {
                                    fireBaseStorage.MultiUploadFile(multiUri, writingDTO.getId());
                                    for(int i=0; i<multiUri.size();i++){
                                        writingImageDTO=new WritingImageDTO(String.valueOf(fireBaseStorage.Firebase_MultiUri().get(i)),writingDTO.getId());
                                        Log.e("writingImageDTO", String.valueOf(writingImageDTO));
                                        Call<Void>call4=retrofitRequest2.postWriting_Image(writingImageDTO);
                                        call4.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                            }
                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                            }
                                        });
                                    }
                                }
                                if (singleuri != null) {
                                    fireBaseStorage.SingleUploadFile(singleuri, writingDTO.getId());
                                    writingImageDTO=new WritingImageDTO(fireBaseStorage.Firebase_SingleUri(),writingDTO.getId());
                                    Log.e("야야야", String.valueOf(writingImageDTO.getPath()));
                                    Call<Void>call3=retrofitRequest.postWriting_Image(writingImageDTO);
                                    call3.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                }
                                Intent intent2 = new Intent();
                                BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                                BoardInsertActivity.this.finish();
                            }

                            @Override
                            public void onFailure(Call<List<WritingDTO>> call, Throwable t) {

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
            //사진 여러장 클릭 시
            if (data.getClipData() != null) {
                singleimg.setImageURI(null);

                imgcount = data.getClipData().getItemCount();
                for (int i = 0; i < imgcount; i++) {
                    multiUri.add((Uri) data.getClipData().getItemAt(i).getUri());
                }
                imageViewAdapter.setUri(multiUri);
                viewPager.setAdapter(imageViewAdapter);
                singleuri=null;
            }  if (data.getData() != null ){
                viewPager.setAdapter(null);
                singleuri = data.getData();
                Log.e("single", String.valueOf(singleuri));
                singleimg.setImageURI(singleuri);
                multiUri=null;
            }
        }
    }
}



