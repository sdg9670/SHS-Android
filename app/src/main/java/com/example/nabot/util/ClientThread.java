package com.example.nabot.util;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabot.domain.ClientDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    EditText etext;
    TextView text;
    ClientDTO client;
    public ClientThread(EditText etext, TextView text, ClientDTO client) {
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
