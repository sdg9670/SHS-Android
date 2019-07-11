package com.example.nabot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.domain.ContactDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CheckFriendAdapter extends BaseAdapter {
    public ArrayList<ContactDTO> items = new ArrayList<ContactDTO>();
    public TextView idText;
    public Button agree;
    public Button disagree;
    public CheckFriendAdapter() {

    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ContactDTO getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        items.remove(position);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_check_friend, parent, false);
        idText = (TextView) view.findViewById(R.id.idText);
        agree = view.findViewById(R.id.agree);
        disagree = view.findViewById(R.id.disagree);
        idText.setText(items.get(position).getSomeid());
        return view;
    }

    public void addItem(ContactDTO contact) {
        items.add(contact);
    }
}
