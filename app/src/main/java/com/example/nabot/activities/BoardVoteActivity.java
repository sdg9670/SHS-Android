package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.nabot.R;
import com.example.nabot.adapter.VoteAddListAdapter;
import com.example.nabot.domain.VoteDTO;

import java.util.ArrayList;

public class BoardVoteActivity extends AppCompatActivity {
    Button button_voteadd, button_voteinsert;
    VoteAddListAdapter voteListAdapter;
    ListView voteindexlist;
    ArrayList<VoteDTO> voteDTOS = new ArrayList<VoteDTO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writingvote);
        button_voteinsert = findViewById(R.id.button_voteinsert);
        voteListAdapter = new VoteAddListAdapter(this);
        voteindexlist = findViewById(R.id.voteindexlist);
        voteindexlist.setAdapter(voteListAdapter);
        button_voteadd = findViewById(R.id.button_voteadd);

        voteindexlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        button_voteadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voteListAdapter.addItem(new VoteDTO(""));
                voteDTOS=voteListAdapter.getitems();
            }
        });

        button_voteinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voteDTOS.clear();
                voteDTOS=voteListAdapter.getitems();
                Intent intent2 = new Intent();
                Bundle bundle=new Bundle();
                for(int i=0; i<voteDTOS.size();i++){
                    Log.e("vvvv",voteDTOS.get(i).getName());
                }
                 if(voteDTOS.size()>0){
                     Log.e("보낸다보내","ss");
                    bundle.putSerializable("voteDTOS", voteDTOS);
                    intent2.putExtra("isvoting",true);
                    intent2.putExtras(bundle);
                    setResult(RESULT_OK, intent2);
                    finish();

                }
                 else{

                     intent2.putExtra("isvoting",false);
                     setResult(RESULT_OK, intent2);
                     finish();
                 }

            }
        });
   }
}

