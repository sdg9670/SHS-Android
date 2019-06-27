package com.example.nabot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nabot.R;

public class SpeakerListActivity extends AppCompatActivity {
    ListView speakerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakerlist);

        final SpeakerListAdapter ladpater = new SpeakerListAdapter();
        speakerList = (ListView) findViewById(R.id.speakerlistview);
        speakerList.setAdapter(ladpater);


    }
}

