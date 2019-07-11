package com.example.nabot.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private SendThread st = new SendThread();
    private ReceiveThread rt = new ReceiveThread();
    private String sendmsg;
    private String rcvmsg;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("StartService", "onBind()");
        return null;
    }

    public void onCreate() {//생명주기
        Log.e("StartService", "onCreate()");
        super.onCreate();

        st.start();
        rt.start();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("StartService", " onStartCommand()");
        // String param = intent.getStringExtra("param");
        // Toast.makeText(this, "received param : " + param, Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

    public class SendThread extends Thread implements Runnable {
        @Override
        public void run() {
            sendmsg = "보낸다";
            Log.d("test", sendmsg);
        }
    }

    public class ReceiveThread extends Thread implements Runnable {
        public void run() {
            rcvmsg = "받는다";
            Log.d("test", rcvmsg);
        }
    }
}


