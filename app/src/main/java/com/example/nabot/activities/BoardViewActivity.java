package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.CommentListAdapter;
import com.example.nabot.adapter.ImageViewAdapter;
import com.example.nabot.classes.SquareViewPager;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.CommentDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.domain.WritingImageDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.inflate;

public class BoardViewActivity extends AppCompatActivity {
    TextView board_view_title, textView, board_view_user, board_view_text, board_writedate;
    EditText comment_text;
    ListView board_commentlist;
    Button board_view_modify_btn, board_view_delete_btn, commentinsert;
    WritingDTO writingDTO;
    ClientDTO clientDTO;
    List<WritingImageDTO> writingImgArray = null;
    List<String> filepath = new ArrayList<String>();

    CommentDTO commentDTO;
    static final int BoardModifyActivitycode = 3;
    CommentListAdapter commentListAdapter;
    SquareViewPager viewPager;
    ImageViewAdapter imageViewAdapter;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardview);
        viewPager = findViewById(R.id.viewPager2);
        board_view_title = findViewById(R.id.board_view_title);
        textView = findViewById(R.id.textView);
        imageViewAdapter = new ImageViewAdapter(this);
        board_view_user = findViewById(R.id.board_view_writer);
        board_view_text = findViewById(R.id.board_view_text);
        board_writedate = findViewById(R.id.board_writedate);
        board_view_modify_btn = findViewById(R.id.board_view_modify_btn);
        board_view_delete_btn = findViewById(R.id.board_view_delete_btn);
        comment_text = findViewById(R.id.comment_text);
        commentinsert = findViewById(R.id.commentinsert);
        board_commentlist = findViewById(R.id.board_commentlist);
        final Intent intent = getIntent();
        writingDTO = (WritingDTO) intent.getSerializableExtra("writing");
        final BoardDTO boardDTO = (BoardDTO) intent.getSerializableExtra("board");
        clientDTO = (ClientDTO) intent.getSerializableExtra("client");

        if (clientDTO.getId() != writingDTO.getWriter()) {
            board_view_modify_btn.setVisibility(View.INVISIBLE);
            board_view_delete_btn.setVisibility(View.INVISIBLE);
        }
        board_view_title.setText(writingDTO.getTitle());
        board_view_text.setText(writingDTO.getContent());
        board_writedate.setText(writingDTO.getUpdate_time());
        board_view_user.setText(writingDTO.getWriter_name());


        final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<CommentDTO>> call = retrofitRequest.getComment(writingDTO.getId());
        call.enqueue(new RetrofitRetry<List<CommentDTO>>(call) {
            @Override
            public void onResponse(Call<List<CommentDTO>> call, Response<List<CommentDTO>> response) {
                List<CommentDTO> commentDTO = response.body();
                commentListAdapter = new CommentListAdapter(BoardViewActivity.this, clientDTO);
                for (CommentDTO i : commentDTO) {
                    commentListAdapter.addItem(i);
                }
                board_commentlist.setAdapter(commentListAdapter);
                Call<List<WritingImageDTO>> call1 = retrofitRequest.getWriting_Image(writingDTO.getId());
                call1.enqueue(new RetrofitRetry<List<WritingImageDTO>>(call1) {
                    @Override
                    public void onResponse(Call<List<WritingImageDTO>> call, Response<List<WritingImageDTO>> response) {
                        writingImgArray = response.body();
                        if (writingImgArray.size() > 0) {
                            for (int i = 0; i < writingImgArray.size(); i++) {
                                filepath.add(writingImgArray.get(i).getPath());
                            }
                            Log.e("filepath", String.valueOf(filepath));
                            imageViewAdapter.imageViewAdapterDown(filepath, writingDTO.getId());
                            viewPager.setAdapter(imageViewAdapter);
                            viewPager.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        board_view_modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(BoardViewActivity.this, BoardModifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("writingImgArray", (Serializable) writingImgArray);
                bundle.putSerializable("writing", writingDTO);
                bundle.putSerializable("client", clientDTO);
                bundle.putSerializable("board", boardDTO);
                in.putExtras(bundle);
                startActivityForResult(in, BoardModifyActivitycode);

            }
        });
        board_view_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.deleteWriting(writingDTO.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent();
                        BoardViewActivity.this.setResult(RESULT_OK, intent);
                        BoardViewActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Intent intent = new Intent();
                        BoardViewActivity.this.setResult(RESULT_OK, intent);
                        BoardViewActivity.this.finish();
                    }
                });
            }
        });
        commentinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CommentDTO commentDTO = new CommentDTO(clientDTO.getId(), comment_text.getText().toString(), writingDTO.getId());
                comment_text.setText("");
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                Call<Void> call = retrofitRequest.postComment(commentDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        commentDTO.setWriter_name(clientDTO.getName());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        commentDTO.setUpdate_time(sdf.format(new Date()));
                        commentListAdapter.addItem(commentDTO);
                        commentListAdapter.notifyDataSetChanged();
                        board_commentlist.setSelection(commentListAdapter.getCount() - 1);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        commentDTO.setWriter_name(clientDTO.getName());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        commentDTO.setUpdate_time(sdf.format(new Date()));
                        commentListAdapter.addItem(commentDTO);
                        commentListAdapter.notifyDataSetChanged();
                        board_commentlist.setSelection(commentListAdapter.getCount() - 1);
                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        BoardViewActivity.this.setResult(RESULT_OK, intent);
        BoardViewActivity.this.finish();
    }


}