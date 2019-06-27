package com.example.nabot.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.example.nabot.R;

public class SpeakerListActivity extends AppCompatActivity {

    ListView speakerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakerlist);

    }
}

