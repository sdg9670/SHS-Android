package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class BoardVoteViewActivity extends AppCompatActivity {
    RadioGroup vote_radiogroup;
    TextView vote_view_text, votetitle;
    Button btnvoting;
    List<VoteDTO> voteDTOS = new ArrayList<VoteDTO>();
    WritingDTO writingDTO = new WritingDTO();
    ClientDTO clientDTO;
    RadioGroup radioGroup;
    VoteWheterDTO voteWheterDTO = null;
    Boolean ischekced = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_vote);
        Intent intent = getIntent();
        voteDTOS = (List<VoteDTO>) intent.getSerializableExtra("voteDTOS_1");
        writingDTO = (WritingDTO) intent.getSerializableExtra("writing_1");
        clientDTO = (ClientDTO) intent.getSerializableExtra("client_1");
        vote_radiogroup = findViewById(R.id.vote_radiogroup);
        btnvoting = findViewById(R.id.btnvoting);
        votetitle = findViewById(R.id.votetitle);
        votetitle.setText(writingDTO.getTitle());
        radioGroup = findViewById(R.id.vote_radiogroup);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        for (int i = 1; i <= voteDTOS.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(voteDTOS.get(i - 1).getId());
            radioButton.setText(voteDTOS.get(i - 1).getName());
            radioGroup.addView(radioButton);
        }
        btnvoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ischekced == true && voteWheterDTO != null) {
                    Log.e("ischecked", String.valueOf(ischekced));
                    Log.e("votewheter", String.valueOf(voteWheterDTO));
                    final RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                    Call<List<CheckVoteDTO>> call = retrofitRequest.check_Vote(writingDTO.getId(), clientDTO.getId());
                    call.enqueue(new RetrofitRetry<List<CheckVoteDTO>>(call) {
                        @Override
                        public void onResponse(Call<List<CheckVoteDTO>> call, Response<List<CheckVoteDTO>> response) {
                            if (response.body().size() == 0 || response.body() == null) {
                                Call<Void> call1 = retrofitRequest.postVoteWheter(voteWheterDTO);
                                call1.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent intent1 = new Intent(BoardVoteViewActivity.this, BoardVoteViewResultActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("client", clientDTO);
                                        bundle.putSerializable("writing", writingDTO);
                                        intent1.putExtras(bundle);
                                        startActivity(intent1);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }if(response.body().size() >0 || response.body()!=null){ {
                                Toast.makeText(BoardVoteViewActivity.this, "이미햇음", Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(BoardVoteViewActivity.this,BoardVoteViewResultActivity.class);
                               Bundle bundle=new Bundle();
                                bundle.putSerializable("client", clientDTO);
                                bundle.putSerializable("writing", writingDTO);
                                intent1.putExtras(bundle);
                                startActivity(intent1);
                                finish();
                                }
                            }
                        }
                    });
                } else
                    Toast.makeText(BoardVoteViewActivity.this, "먼저 입력해주세요", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BoardVoteViewActivity.this, "항목을 체크해주세요123123", Toast.LENGTH_SHORT).show();
                    ischekced = false;
                }
            }
        });
    }
}

