package com.example.nabot.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabot.R;
import com.example.nabot.classes.Camera;
import com.example.nabot.domain.ClientDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
    Button command_dialog;
    ClientDTO client;
    EditText etext;
    TextView text;
    private long time= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent=getIntent();
        client= (ClientDTO)intent.getSerializableExtra("client");
        command_dialog = (Button) findViewById(R.id.comButton);
        Button speakerButton = (Button) findViewById(R.id.speakerButton);
        Button doorlockButton = (Button) findViewById(R.id.doorlockButton);
        Button chatButton = (Button) findViewById(R.id.buttonVote);
        Button boardButton = (Button) findViewById(R.id.boardButton);
        Button logoutButton = (Button) findViewById(R.id.logout);
        Button windowButton = (Button) findViewById(R.id.windowButton);
        Button curtainButton = (Button) findViewById(R.id.curtainButton);
        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
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
                /*Camera camera = new Camera("Speaker", "192.168.1.100", 5000);
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(VideoActivity.CAMERA, camera);
                startActivity(intent);*/
            }
        });
        doorlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DoorLockActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("client",client);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor seditor = getSharedPreferences("login", MODE_PRIVATE).edit();
                seditor.putInt("id", -1);
                seditor.commit();
                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                client.setFcm("널");
                Call<Void> call = retrofitRequest.updateFCM(client);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WindowActivity.class);
                startActivity(intent);
            }
        });
        curtainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CurtainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }

}

class ClientThread extends Thread {
    EditText etext;
    TextView text;
    ClientDTO client;
    ClientThread(EditText etext, TextView text, ClientDTO client) {
        this.etext = etext;
        this.text = text;
        this.client = client;
    }
    public void run() {
        String host = "simddong.ga";
        int port = 9670;
        Socket socket = null;
        PrintWriter outputStream = null;
        BufferedReader inputStream = null;
        try {
            socket = new Socket(host, port);
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            outputStream.print(client.getId() + "\tandroid\tcommand\t" + client.getId() + "\t" + etext.getText().toString());
            outputStream.flush();
            etext.setText("");
            Log.e("케케", "ㅎㅎ" + etext.getText().toString());
            Log.d("ClientThread", "서버로 보냄.");
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = inputStream.readLine();
            String [] splitdata = input.split("\t");
            input = splitdata[splitdata.length-1];
            Log.d("ClientThread","받은 데이터 : "+input);
            text.setText(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}