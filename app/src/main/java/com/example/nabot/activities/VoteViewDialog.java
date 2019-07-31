
package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.domain.CheckVoteDTO;
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

public class VoteViewDialog extends Dialog {
    private Context context;

    RadioGroup vote_radiogroup;
    TextView vote_view_text;
    Button btnvoting;
    RadioGroup radioGroup;
    VoteWheterDTO voteWheterDTO = null;
    Boolean ischekced = false;
    LinearLayout vote_result2, vote_result3,voting_linearlayout;

    public VoteViewDialog(final Context context, final List<VoteDTO> voteDTOS, final WritingDTO writingDTO, final ClientDTO clientDTO) {
        super(context);
        setContentView(R.layout.dialog_vote);
        voting_linearlayout=findViewById(R.id.voting_linearlayout);
        vote_result2 = findViewById(R.id.vote_result2);
        vote_result3 = findViewById(R.id.vote_result_all);
        btnvoting = findViewById(R.id.btnvoting);
        radioGroup = findViewById(R.id.vote_radiogroup);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        vote_result2.setVisibility(View.GONE);
        vote_result3.setVisibility(View.GONE);
        final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<CheckVoteDTO>> call = retrofitRequest.check_Vote(writingDTO.getId(), clientDTO.getId());
        call.enqueue(new RetrofitRetry<List<CheckVoteDTO>>(call) {
            @Override
            public void onResponse(Call<List<CheckVoteDTO>> call, Response<List<CheckVoteDTO>> response) {
                if (response.body().size() == 0 || response.body() == null) {
                    //투표안함
                    voting_linearlayout.setVisibility(View.VISIBLE);
                    vote_result2.setVisibility(View.VISIBLE);
                    vote_result3.setVisibility(View.GONE);
                    for (int i = 1; i <= voteDTOS.size(); i++) {
                        RadioButton radioButton = new RadioButton(context);
                        radioButton.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        radioButton.setId(voteDTOS.get(i - 1).getId());
                        radioButton.setEms(20);
                        radioButton.setText(voteDTOS.get(i - 1).getName());
                        radioGroup.addView(radioButton);
                    }
                }
                else if( response.body().size() !=0 || response.body()!=null){
                    resultvote(context,writingDTO);
                }
            }
        });
        btnvoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ischekced==true && voteWheterDTO!=null){
                    Call<Void> call1 = retrofitRequest.postVoteWheter(voteWheterDTO);
                    call1.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            resultvote(context, writingDTO);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
                else{
                    Toast.makeText(context, "항목을 체크해주세요123123", Toast.LENGTH_SHORT).show();

                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                ischekced = checkedRadioButton.isChecked();
                if (ischekced == true) {
                    for (int i = 0; i < voteDTOS.size(); i++) {
                        Log.e("eeeee", checkedRadioButton.getText().toString());
                        if (voteDTOS.get(i).getId() == checkedRadioButton.getId()) {
                            Log.e("eeeee22222", voteDTOS.get(i).getName());
                            voteWheterDTO = new VoteWheterDTO(voteDTOS.get(i).getId(), clientDTO.getId());
                        }
                    }
                } else {
                    voteWheterDTO = null;
                    Toast.makeText(context, "항목을 체크해주세요123123", Toast.LENGTH_SHORT).show();
                    ischekced = false;
                }
            }
        });


    }


    private  void resultvote(final Context context,final  WritingDTO writingDTO){
        //투표햇음
        voting_linearlayout.setVisibility(View.GONE);
        vote_result3.setVisibility(View.VISIBLE);
        final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<VoteDTO>> call2 = retrofitRequest.getWriting_Vote(writingDTO.getId());
        call2.enqueue(new RetrofitRetry<List<VoteDTO>>(call2) {
            @Override
            public void onResponse(Call<List<VoteDTO>> call, Response<List<VoteDTO>> response) {
                List<VoteDTO> voteDTOSget = response.body();
                voteDTOSget = response.body();
                int votescount = -1;
                if (voteDTOSget != null) {
                    votescount = 0;
                    int max=-1;
                    int maxid=-1;
                    for (int i = 0; i < voteDTOSget.size(); i++) {
                        if(voteDTOSget.get(i).getAmount()>max){
                            max=voteDTOSget.get(i).getAmount();
                            maxid=voteDTOSget.get(i).getId();
                        }
                        votescount += voteDTOSget.get(i).getAmount();
                    }
                    for (int i = 1; i <= voteDTOSget.size(); i++) {
                        Double d = Double.valueOf(voteDTOSget.get(i - 1).getAmount());
                        Double s = Double.valueOf(votescount);
                        Double percent = d / s * 100.0;
                      LinearLayout layout1=new LinearLayout(context);
                        LinearLayout layout2=new LinearLayout(context);
                      layout1.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                       layout1.setOrientation(LinearLayout.HORIZONTAL);
                        layout1.setPadding(5,5,5,5);
                        layout2.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layout2.setOrientation(LinearLayout.HORIZONTAL);
                        layout2.setPadding(5,5,5,5);
                       layout1.setId(voteDTOSget.get(i-1).getId());
                        TextView textView_title = new TextView(context);
                        TextView textView_votecount = new TextView(context);
                        TextView textView_votepercent= new TextView(context);
                        textView_title.setTextSize(30);
                        layout1.setPadding(10,10,10,10);
                        layout2.setPadding(10,10,10,10);
                        textView_votecount.setPadding(5,0,5,0);
                        textView_votepercent.setPadding(5,0,5,0);
                        textView_title.setText(voteDTOSget.get(i - 1).getName());
                        textView_votecount.setText("투표수" + voteDTOSget.get(i - 1).getAmount());
                        textView_votepercent.setText("                " +
                                "                " +String.format("%.2f", percent)+"%");
                        layout1.addView(textView_title);
                        layout2.addView(textView_votecount);
                        layout2.addView(textView_votepercent);
                        if(maxid==layout1.getId()){
                            layout1.setBackgroundColor(Color.GRAY);
                            layout2.setBackgroundColor(Color.GRAY);
                            textView_title.setTextColor(Color.WHITE);
                            textView_votecount.setTextColor(Color.WHITE);
                            textView_votepercent.setTextColor(Color.WHITE);
                        }
                        vote_result3.addView(layout1);
                        vote_result3.addView(layout2);

                    }
            }
            }
        });
    }
}

