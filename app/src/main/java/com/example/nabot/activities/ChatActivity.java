package com.example.nabot.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nabot.R;

public class ChatActivity extends AppCompatActivity {
    Button SendButton;
    EditText SendContentText;
    ScrollView ChatScroll;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SendButton = (Button) findViewById(R.id.sendbutton);
        msg = (TextView) findViewById(R.id.msg);
        SendContentText = (EditText) findViewById(R.id.sendcontenttext);
        ChatScroll = (ScrollView) findViewById(R.id.chatscroll);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg.append(SendContentText.getText() + "\n");
                SendContentText.setText("");
                ChatScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        ChatScroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }
}
