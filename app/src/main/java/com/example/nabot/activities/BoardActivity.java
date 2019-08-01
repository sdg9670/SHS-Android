package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.nabot.R;
import com.example.nabot.adapter.BoardSpinnerAdapter;
import com.example.nabot.adapter.WritingListAdapter;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;


public class BoardActivity extends AppCompatActivity {
    private ClientDTO client;
    private BoardDTO board;
    static final int BoardInserActivityCode = 1;
    static final int BoardViewActivityCode = 2;

    List<BoardDTO> boardArray = null;
    List<WritingDTO> writingArray = null;
    Spinner boardspinner;
    BoardSpinnerAdapter spinnerAdapter;
    Button boardinsertbtn;
    WritingListAdapter listAdapter;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BoardInserActivityCode && resultCode == RESULT_OK) {
            BoardDTO boardDTO=  new BoardDTO();
            boardDTO=(BoardDTO)data.getSerializableExtra("boardDTO");
            board.setId(boardDTO.getId());
            refreshBoard(spinnerAdapter.getItemPosition(board.getId()));
        }
        else if (requestCode == BoardViewActivityCode && resultCode == RESULT_OK) {
            refreshBoard(spinnerAdapter.getItemPosition(board.getId()));
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board);
        boardinsertbtn = (Button) findViewById(R.id.boardinsertbtn);

        Intent intent2= getIntent();
        final  int isModify=intent2.getIntExtra("isModify",-1);

        Intent intent=getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");

        boardspinner = (Spinner) findViewById(R.id.boardspinner);
        spinnerAdapter = new BoardSpinnerAdapter(BoardActivity.this);

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<BoardDTO>> call = retrofitRequest.getBoard();
        call.enqueue(new RetrofitRetry<List<BoardDTO>>(call) {
            @Override
            public void onResponse(Call<List<BoardDTO>> call, Response<List<BoardDTO>> response) {
                boardArray = response.body();
                for (int i = 0; i < boardArray.size(); i++) {
                    spinnerAdapter.addItem(boardArray.get(i));
                };
                boardspinner.setAdapter(spinnerAdapter);


                boardspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        refreshBoard(position);
                          final int masterid=111;
                        Log.e("qqqqqq", String.valueOf(spinnerAdapter.getItem(position))  );
                        if(spinnerAdapter.getItem(position).getId()==masterid){
                            boardinsertbtn.setVisibility(View.INVISIBLE);
                        }
                }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        final ListView writingList = (ListView) findViewById(R.id.board_writinglist);
        listAdapter = new WritingListAdapter();
        writingList.setAdapter(listAdapter);

        writingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(getApplicationContext(), BoardViewActivity.class);
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("client",client);
                    Log.e("asdasdadadasd", String.valueOf(client.getId()));
                    bundle.putSerializable("writing", listAdapter.getItem(position));
                    bundle.putSerializable("board",board);
                    intent.putExtras(bundle);
                startActivityForResult(intent,BoardViewActivityCode);
            }
        });
        boardinsertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(BoardActivity.this, BoardInsertActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                bundle.putSerializable("board", board);;
                in.putExtras(bundle);
                startActivityForResult(in, BoardInserActivityCode);

            }
        });
        if(isModify==31){
            board=new BoardDTO();
            board=(BoardDTO)intent2.getSerializableExtra("board");
            client=(ClientDTO)intent2.getSerializableExtra("client");
            for(int i=0; i<spinnerAdapter.getCount();i++){
                if(board.getId()==spinnerAdapter.getItem(i).getId()){
                    refreshBoard(spinnerAdapter.getItemPosition(board.getId()));
                }
            }
        }


    }

    private void refreshBoard(int position) {
        listAdapter.clearItem();
        board = spinnerAdapter.getItem(position);
        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);

        Call<List<WritingDTO>> call = retrofitRequest.getWriting(board.getId());
        call.enqueue(new RetrofitRetry<List<WritingDTO>>(call) {
            @Override
            public void onResponse(Call<List<WritingDTO>> call, Response<List<WritingDTO>> response) {
                writingArray = response.body();
                for (int i = 0; i < writingArray.size(); i++) {
                    listAdapter.addItem(writingArray.get(i));
                }
                listAdapter.notifyDataSetChanged();
            }
        });

    }
}