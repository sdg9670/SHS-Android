package com.example.nabot.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.VoteDTO;
import com.example.nabot.domain.VoteWheterDTO;
import com.example.nabot.domain.WritingDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardVoteViewResultActivity extends AppCompatActivity {
    LinearLayout vote_result;
    ClientDTO clientDTO;
    WritingDTO writingDTO;
    List<VoteDTO> voteDTOS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writingvoteviewresult);
        vote_result=findViewById(R.id.vote_result);
        Intent intent=getIntent();
        clientDTO= (ClientDTO) intent.getSerializableExtra("clientDTO");
        writingDTO= (WritingDTO) intent.getSerializableExtra("writingDTO");
        Log.e("qweqweqwe",String.valueOf(clientDTO.getId()));
        Log.e("asdasdads", String.valueOf(writingDTO.getId()));
        final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<VoteDTO>> call = retrofitRequest.getWriting_Vote(writingDTO.getId());
        call.enqueue(new RetrofitRetry<List<VoteDTO>>(call) {
            @Override
            public void onResponse(Call<List<VoteDTO>> call, Response<List<VoteDTO>> response) {
                voteDTOS=response.body();
                int votescount=-1;
                if(voteDTOS!=null){
                    votescount=0;
                    for(int i=0; i<voteDTOS.size();i++){
                        votescount+=voteDTOS.get(i).getAmount();
                    }
                    for(int i=1 ;i<=voteDTOS.size();i++){
                        Log.e("votescount", String.valueOf(votescount));
                        Double d= Double.valueOf(voteDTOS.get(i-1).getAmount());
                        Double s=Double.valueOf(votescount);
                        Double percent=d/s*100.0;
                        TextView textView=new TextView(BoardVoteViewResultActivity.this);
                        textView.setTextSize(19);
                        textView.setBackgroundColor(Color.GRAY);
                        textView.setTextColor(Color.WHITE);
                        textView.setPadding(1,1,1,3);
                        textView.setText("타이틀"+voteDTOS.get(i-1).getName() +"투표수"+voteDTOS.get(i-1)
                                .getAmount()+"퍼센트지"+String.format("%.2f",percent));
                        vote_result.addView(textView);
                    }
                }
            }
        });
    }
}

