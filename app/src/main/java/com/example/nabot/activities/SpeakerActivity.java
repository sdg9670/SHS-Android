package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.DoorlockViewAdapter;
import com.example.nabot.adapter.SpeakerViewAdapter;
import com.example.nabot.classes.Camera;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.domain.DoorlockDTO;
import com.example.nabot.util.ClientThread;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class SpeakerActivity extends AppCompatActivity {
    EditText etext;
    TextView text;
    private ClientDTO client = null;
    private BoardDTO board;
    Button remoteCommandButton;
    List<ClientDTO> speakers = null;
    ListView speakerlist;
    SpeakerViewAdapter speakerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        Intent intent = getIntent();
        speakerViewAdapter = new SpeakerViewAdapter(this);
        client = (ClientDTO) intent.getSerializableExtra("client");
        remoteCommandButton = findViewById(R.id.remoteCommandButton);
        speakerlist = findViewById(R.id.speakerlist);
        speakerlist.setAdapter(speakerViewAdapter);


        remoteCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SpeakerActivity.this);
                dialog.setContentView(R.layout.dialog_command);
                dialog.show();
                ViewGroup.LayoutParams params =dialog.getWindow().getAttributes();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button button = (Button) dialog.findViewById(R.id.comButton);
                Button button2 = (Button) dialog.findViewById(R.id.comClose);
                etext = (EditText) dialog.findViewById(R.id.com_edittext);
                text = (TextView) dialog.findViewById(R.id.com_text);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etext.getText().length() == 0)
                            return ;
                        ClientThread thread = new ClientThread(etext, text, client);
                        thread.start();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        RetrofitRequest retrofitRequest=RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<ClientDTO>> call=retrofitRequest.getspeaker(client.getDong(),client.getHo());
        call.enqueue(new RetrofitRetry<List<ClientDTO>>(call) {
            @Override
            public void onResponse(Call<List<ClientDTO>> call, Response<List<ClientDTO>> response) {
                if (response.body() != null) {
                    speakers = new ArrayList<ClientDTO>();
                    speakers = response.body();
                    for (int i = 0; i < speakers.size(); i++) {
                        speakerViewAdapter.addItem(speakers.get(i));
                    }
                    speakerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        speakerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Camera camera = new Camera("Speaker", "192.168.1.100", 5000);
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(VideoActivity.CAMERA, camera);
                startActivity(intent);
            }
        });

    }
}