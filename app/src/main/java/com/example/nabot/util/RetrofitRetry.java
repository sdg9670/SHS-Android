package com.example.nabot.util;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;

abstract public class   RetrofitRetry<T> implements Callback<T> {

    private static final int TOTAL_RETRIES = 100;
    private static final String TAG = RetrofitRetry.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0;

    public RetrofitRetry(Call<T> call) {
        this.call = call;
    }

    @Override
    public void onFailure(Call<T> call,Throwable t) {
        Log.e(TAG, t.getLocalizedMessage());
        if (retryCount++ < TOTAL_RETRIES) {
            Log.e("ggg", t.getMessage());
            Log.v(TAG, "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
            retry();
        }
    }

    private void retry() {
        call.clone().enqueue(this);
    }
}