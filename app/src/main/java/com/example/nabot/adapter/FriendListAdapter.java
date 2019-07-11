package com.example.nabot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nabot.R;
import com.example.nabot.domain.ContactDTO;
import com.example.nabot.domain.WritingDTO;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter {
    public ArrayList<ContactDTO> items = new ArrayList<ContactDTO>();

    public FriendListAdapter() {

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
        View view = inflater.inflate(R.layout.layout_friend_list, parent, false);
        String item = view.toString();
        TextView nameText = (TextView) view.findViewById(R.id.nameText);
        ImageView profileImage = (ImageView) view.findViewById(R.id.profileimage);
        nameText.setText(item);
        profileImage.setImageResource(R.mipmap.ic_launcher);
        return view;
    }

    public void addItem(ContactDTO contact) {
        items.add(contact);
    }
}
