// Copyright © 2016 Shawn Baker using the MIT License.
package com.example.nabot;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

public class App extends Application
{
    // 기능완성
    private static Context context;

    //******************************************************************************
    // onCreate
    //******************************************************************************
    @Override
    public void onCreate()
	{
        super.onCreate();
        context = getApplicationContext();
    }

    //******************************************************************************
    // onCreate
    //******************************************************************************
    public static Context getContext() { return context; }

    //******************************************************************************
    // getStr
    //******************************************************************************
    public static String getStr(int id) {
        return context.getResources().getString(id);
    }

	//******************************************************************************
    // getClr
    //******************************************************************************
    public static int getClr(int id)
    {
        return ContextCompat.getColor(context, id);
    }
}
