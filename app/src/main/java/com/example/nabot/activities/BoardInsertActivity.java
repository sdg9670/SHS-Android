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
import com.example.nabot.adapter.VoteInsertViewListAdapter;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.VoteDTO;
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
    List<Uri> imguri = new ArrayList<Uri>();
    ListView votelist;
    VoteInsertViewListAdapter voteInsertListAdapter;
    int index = 0;
    WritingDTO writingDTO;
    ImageListAdapter imageListAdapter;
    Button button_img, button_vote;
    ListView imagelist;
    FireBaseStorage fireBaseStorage = new FireBaseStorage();
    List<VoteDTO> votearray = null;
    List<String> downloadUri = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardinsert);
        button_img = findViewById(R.id.button_img);
        voteInsertListAdapter = new VoteInsertViewListAdapter(this);
        votelist = findViewById(R.id.votelist);
        votelist.setAdapter(voteInsertListAdapter);
        imageListAdapter = new ImageListAdapter(this, imguri);
        board_insert_boardname = findViewById(R.id.board_insert_boardname);
        board_insert_content = findViewById(R.id.board_insert_content);
        board_insert_btn = findViewById(R.id.board_insert_btn);
        button_vote = findViewById(R.id.button_vote);
        board_insert_title = findViewById(R.id.board_insert_title);
        imagelist = findViewById(R.id.imagelist);
        Intent in = getIntent();
        final ClientDTO clientDTO = (ClientDTO) in.getSerializableExtra("client");
        final BoardDTO boardDTO = (BoardDTO) in.getSerializableExtra("board");
        board_insert_boardname.setText(boardDTO.getName());

        button_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VoteInsertDialog voteInsertDialog=new VoteInsertDialog(BoardInsertActivity.this);
                voteInsertDialog.setDialogListener(new VoteInsertDialog.VoteInsertDialogListener() {
                    @Override
                    public void onPositiveClicked(List<VoteDTO> voteDTOList) {
                       if(voteDTOList!=null){
                           votearray= voteDTOList;
                           voteInsertListAdapter.clearitem();
                           if (votearray.size() != 0) {
                               for (int i = 0; i < votearray.size(); i++) {
                                   voteInsertListAdapter.addItem(votearray.get(i).getName());
                               }
                           }
                       }
                       else
                           votearray = null;
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                voteInsertDialog.show();

            }
        });

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
                        Call<List<WritingDTO>> call1 = retrofitRequest.getlasat_writing();
                        call1.enqueue(new RetrofitRetry<List<WritingDTO>>(call1) {
                            @Override
                            public void onResponse(Call<List<WritingDTO>> call, Response<List<WritingDTO>> response) {
                                writingDTO = response.body().get(0);
                                if (imageListAdapter.getItem() != null) {
                                    downloadUri.clear();
                                    downloadUri = fireBaseStorage.UploadFile(imageListAdapter.getItem(), writingDTO.getId());
                                    List<WritingImageDTO> writingImageInsertDTOS = new ArrayList<WritingImageDTO>();
                                    for (int i = 0; i < downloadUri.size(); i++) {
                                        writingImageInsertDTOS.add(new WritingImageDTO(String.valueOf(downloadUri.get(i)), writingDTO.getId(), imageListAdapter.getName().get(i)));
                                    }
                                    Call<Void> call2 = retrofitRequest.postWriting_Image_Multi(writingImageInsertDTOS);
                                    call2.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (votearray != null) {
                                                for (int i = 0; i < votearray.size(); i++) {
                                                    votearray.get(i).setWriting_id(writingDTO.getId());
                                                    Log.e("자자자", "wiritngid" + votearray.get(i).getWriting_id()
                                                            + votearray.get(i).getName());

                                                }
                                                Call<Void> call3 = retrofitRequest.postWriting_Vote(votearray);
                                                call3.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        Intent intent2 = new Intent();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putSerializable("boardDTO", boardDTO);
                                                        intent2.putExtras(bundle);
                                                        BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                                                        BoardInsertActivity.this.finish();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {

                                                    }
                                                });
                                            } else if (votearray == null) {
                                                Intent intent2 = new Intent();
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("boardDTO", boardDTO);
                                                intent2.putExtras(bundle);
                                                BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                                                BoardInsertActivity.this.finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                        }
                                    });
                                } else if (imageListAdapter.getItem() == null) {
                                    if (votearray != null) {
                                        for (int i = 0; i < votearray.size(); i++) {
                                            votearray.get(i).setWriting_id(writingDTO.getId());
                                            Log.e("자자자", "wiritngid" + votearray.get(i).getWriting_id()
                                                    + votearray.get(i).getName());
                                        }
                                        Call<Void> call3 = retrofitRequest.postWriting_Vote(votearray);
                                        call3.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                Intent intent2 = new Intent();
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("boardDTO", boardDTO);
                                                intent2.putExtras(bundle);
                                                BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                                                BoardInsertActivity.this.finish();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });
                                    } else if (votearray == null || votearray.size()==0) {
                                        Intent intent2 = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("boardDTO", boardDTO);
                                        intent2.putExtras(bundle);
                                        BoardInsertActivity.this.setResult(RESULT_OK, intent2);
                                        BoardInsertActivity.this.finish();
                                    }
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

        if (requestCode == 123 && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("isvoting", false) == true) {
                votearray = new ArrayList<VoteDTO>();
                votearray = (List<VoteDTO>) data.getSerializableExtra("voteDTOS");
                Log.e("ttttt", String.valueOf(votearray.size()));
                voteInsertListAdapter.clearitem();
                if (votearray.size() != 0) {
                    for (int i = 0; i < votearray.size(); i++) {
                        voteInsertListAdapter.addItem(votearray.get(i).getName());
                    }
                }
            } else
                votearray = null;
        }

        if (requestCode == 0 && resultCode == RESULT_OK) {
            imguri = imageListAdapter.getItem();
            if (imguri == null) {
                imguri = new ArrayList<Uri>();
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
            imagelist.setAdapter(imageListAdapter);
        }
    }
}