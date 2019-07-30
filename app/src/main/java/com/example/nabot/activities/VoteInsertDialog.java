package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.nabot.R;
import com.example.nabot.adapter.VoteAddListAdapter;
import com.example.nabot.domain.VoteDTO;

import java.util.ArrayList;
import java.util.List;

public class  VoteInsertDialog extends Dialog {
    Button button_voteadd, button_voteinsert;
    VoteAddListAdapter voteListAdapter;
    ListView voteindexlist;
    ArrayList<VoteDTO> voteDTOS = new ArrayList<VoteDTO>();
    VoteInsertDialogListener voteInsertDialogListener;

    interface VoteInsertDialogListener{
        void onPositiveClicked(List<VoteDTO> voteDTOList);
        void onNegativeClicked();
    }

    public void setDialogListener(VoteInsertDialogListener voteInsertDialogListener){
        this.voteInsertDialogListener = voteInsertDialogListener;
    }

    public  VoteInsertDialog( Context context) {
        super(context);
        setContentView(R.layout.dialog_voteinsert);
        button_voteinsert = findViewById(R.id.button_voteinsert);
        voteListAdapter = new VoteAddListAdapter(context);
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
                voteDTOS = voteListAdapter.getitems();
            }
        });

        button_voteinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voteDTOS=new ArrayList<VoteDTO>();
                Log.e("eqq", String.valueOf(voteListAdapter.getCount()));
                for (int i = 0; i < voteListAdapter.getCount(); i++) {
                    if (!voteListAdapter.getItem(i).getName().equals("")) {
                        voteDTOS.add(voteListAdapter.getItem(i));
                    }
                }

                if (voteDTOS.size() > 0) {
                } else {
                    voteDTOS=null;
                }
                voteInsertDialogListener.onPositiveClicked(voteDTOS);
                dismiss();
            }
        });
    }
    public  ArrayList<VoteDTO> getVoteDTOS() {
        return voteDTOS;
    }
}
