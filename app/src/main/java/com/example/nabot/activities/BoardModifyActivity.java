package com.example.nabot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.ImageListAdapterModify;
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

public class BoardModifyActivity extends AppCompatActivity {
    TextView board_modify_boardname;
    Button board_modify_btn;
    EditText board_modify_title, board_modify_content;
    WritingDTO writingDTO;
    BoardDTO boardDTO;
    ClientDTO clientDTO;
    List<WritingImageDTO> writingImgArray;
    List<Uri> imguri = null;
    ImageListAdapterModify imageListAdapterModify;
    ListView imagelist;
    Button button_img;
    FireBaseStorage fireBaseStorage = new FireBaseStorage();
    List<String> downloadUri = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardmodify);
        board_modify_boardname = findViewById(R.id.board_modify_boardname);
        board_modify_content = findViewById(R.id.board_modify_content);
        board_modify_btn = findViewById(R.id.board_modify_btn);
        board_modify_title = findViewById(R.id.board_modify_title);
        button_img = findViewById(R.id.button_img);
        imagelist = findViewById(R.id.imagelist);
        Intent intent = getIntent();
        clientDTO = (ClientDTO) intent.getSerializableExtra("client");
        writingDTO = (WritingDTO) intent.getSerializableExtra("writing");
        boardDTO = (BoardDTO) intent.getSerializableExtra("board");
        writingImgArray = (List<WritingImageDTO>) intent.getSerializableExtra("writingImgArray");
        imageListAdapterModify = new ImageListAdapterModify(this, writingImgArray);
        imageListAdapterModify.setItems(writingImgArray);
        imagelist.setAdapter(imageListAdapterModify);
        board_modify_boardname.setText(boardDTO.getName());
        board_modify_content.setText(writingDTO.getContent());
        board_modify_title.setText(writingDTO.getTitle());
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

        board_modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                writingDTO = new WritingDTO(writingDTO.getId(), board_modify_title.getText().toString(), board_modify_content.getText().toString());
                Call<Void>call=retrofitRequest.putWriting(writingDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(imageListAdapterModify.getDestroyposition()!=null){
                            Call<Void>call2=retrofitRequest.deleteWriting_Image(imageListAdapterModify.getDestroyposition());
                            call2.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(imageListAdapterModify.getItem()!=null){
                                        downloadUri.clear();
                                        Log.e("씨빨1", String.valueOf(imageListAdapterModify.getItem().size()));
                                        downloadUri=fireBaseStorage.UploadFile(imageListAdapterModify.getItem(),writingDTO.getId());
                                        List<WritingImageDTO> writingImageDTOS=new ArrayList<WritingImageDTO>();
                                        for(int i=0; i<downloadUri.size();i++){
                                            writingImageDTOS.add(new WritingImageDTO(String.valueOf(downloadUri.get(i)),writingDTO.getId(),imageListAdapterModify.getName().get(i)));
                                        }
                                        Call<Void>call1=retrofitRequest.postWriting_Image_Multi(writingImageDTOS);
                                        call1.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                Intent intent2 = new Intent();
                                                BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                                                BoardModifyActivity.this.finish();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });
                                    }
                                    else{
                                        Intent intent2 = new Intent();
                                        BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                                        BoardModifyActivity.this.finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                        else{
                          if(imageListAdapterModify.getItem()!=null){
                              downloadUri.clear();
                              downloadUri=fireBaseStorage.UploadFile(imageListAdapterModify.getItem(),writingDTO.getId());
                              List<WritingImageDTO> writingImageDTOS=new ArrayList<WritingImageDTO>();
                          for(int i=0; i<downloadUri.size();i ++){
                              writingImageDTOS.add(new WritingImageDTO(String.valueOf(downloadUri.get(i)),writingDTO.getId(),imageListAdapterModify.getName().get(i)));
                              Call<Void>call1=retrofitRequest.postWriting_Image_Multi(writingImageDTOS);
                              call1.enqueue(new Callback<Void>() {
                                  @Override
                                  public void onResponse(Call<Void> call, Response<Void> response) {
                                      Intent intent2 = new Intent();
                                      BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                                      BoardModifyActivity.this.finish();
                                  }

                                  @Override
                                  public void onFailure(Call<Void> call, Throwable t) {

                                  }
                              });
                          }

                          }
                          else{
                              Intent intent2 = new Intent();
                              BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                              BoardModifyActivity.this.finish();
                          }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Intent intent2 = new Intent();
                        BoardModifyActivity.this.setResult(RESULT_OK, intent2);
                        BoardModifyActivity.this.finish();
                    }
                });





            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imguri=imageListAdapterModify.getItem();
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
            imageListAdapterModify.addItems(imguri);
            imagelist.setAdapter(imageListAdapterModify);
        }
    }
}