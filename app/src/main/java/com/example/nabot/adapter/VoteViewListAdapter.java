package com.example.nabot.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.VoteDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class VoteViewListAdapter extends BaseAdapter {
    private final Context context;
    TextView VoteText;
    List<VoteDTO> items = new ArrayList<VoteDTO>();
    List<VoteDTO> filteredItem=items; //임시 저장소
    List<VoteDTO> realitem=new ArrayList<VoteDTO>();

    public VoteViewListAdapter(Context context) {
        this.context = context;
    }

    public void addItem(VoteDTO title) {
        items.add(title);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
    return  items.size();
    }

    public ArrayList<VoteDTO> getitems(){
        ArrayList<VoteDTO> realitem=new ArrayList<VoteDTO>();
        for(int i=0; i<filteredItem.size();i++){
           Log.e("asdasdasdzz"+i,"size:"+i+"    "+filteredItem.get(i).getName());
            realitem.add(new VoteDTO(filteredItem.get(i).getName()));
        }
        return realitem;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flate_writingvote, parent, false);

        EditText vote_text = (EditText) view.findViewById(R.id.vote_text);
        Button vote_del = (Button) view.findViewById(R.id.button_vote_delete);
        final VoteDTO voteDTO = filteredItem.get(position);
        vote_text.setText(voteDTO.getName().toString());

        vote_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

        vote_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredItem.get(position).setName(s.toString());
            }
        });

        Log.e("asdasdasdasdadasd", String.valueOf(filteredItem.size()));
        return view;
    }

}
