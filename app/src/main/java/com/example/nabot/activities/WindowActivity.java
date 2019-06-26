package com.example.nabot.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.BoardSpinnerAdapter;
import com.example.nabot.adapter.SensorSpinnerAdapter;
import com.example.nabot.adapter.WindowSpinnerAdapter;
import com.example.nabot.domain.BoardDTO;
import com.example.nabot.domain.CurtainDTO;
import com.example.nabot.domain.SensorDTO;
import com.example.nabot.domain.WindowDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class WindowActivity extends AppCompatActivity {

    TextView windowStatusText;
    EditText openEdit1, openEdit2, closeEdit1, closeEdit2;
    Spinner windowSpinner, windowSelectSpinner, openValueSpinner1, openValueSpinner2, openValueSpinner3, closeValueSpinner1, closeValueSpinner2, closeValueSpinner3;

    Button windowButton, windowStatusButton, sendButton;
    LinearLayout windowSelectLayout, windowOnOffLayout, openLayout, closeLayout, openCheckLayout1, openCheckLayout2, openCheckLayout3, closeCheckLayout1, closeCheckLayout2, closeCheckLayout3;

    RadioButton rdOn, rdOff;
    CheckBox openCheck1, openCheck2, openCheck3, closeCheck1, closeCheck2, closeCheck3;

    String[] select = {"온도", "습도", "강수량", "미세먼지 수치", "내부 가스량"};
    String[] value = {"이상", "미만"};
    String windowStatus;
    int selectPosition;
    List<WindowDTO> windowArray;
    List<SensorDTO> sensorArray;

    WindowSpinnerAdapter windowAdapter;
    SensorSpinnerAdapter sensorAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        windowSelectLayout = (LinearLayout) findViewById(R.id.windowSelectLayout);
        windowOnOffLayout = (LinearLayout) findViewById(R.id.windowOnOffLayout);
        openLayout = (LinearLayout) findViewById(R.id.openLayout);
        closeLayout = (LinearLayout) findViewById(R.id.closeLayout);
        openCheckLayout1 = (LinearLayout) findViewById(R.id.openCheckLayout1);
        openCheckLayout2 = (LinearLayout) findViewById(R.id.openCheckLayout2);
        openCheckLayout3 = (LinearLayout) findViewById(R.id.openCheckLayout3);
        closeCheckLayout1 = (LinearLayout) findViewById(R.id.closeCheckLayout1);
        closeCheckLayout2 = (LinearLayout) findViewById(R.id.closeCheckLayout2);
        closeCheckLayout3 = (LinearLayout) findViewById(R.id.closeCheckLayout3);

        windowStatusText = (TextView) findViewById(R.id.windowStatusText);

        openEdit1 = (EditText) findViewById(R.id.openEdit1);
        openEdit2 = (EditText) findViewById(R.id.openEdit2);
        closeEdit1 = (EditText) findViewById(R.id.closeEdit1);
        closeEdit2 = (EditText) findViewById(R.id.closeEdit2);

        windowSpinner = (Spinner) findViewById(R.id.windowSpinner);
        windowSelectSpinner = (Spinner) findViewById(R.id.windowSelectSpinner);
        openValueSpinner1 = (Spinner) findViewById(R.id.openValueSpinner1);
        openValueSpinner2 = (Spinner) findViewById(R.id.openValueSpinner2);
        openValueSpinner3 = (Spinner) findViewById(R.id.openValueSpinner3);
        closeValueSpinner1 = (Spinner) findViewById(R.id.closeValueSpinner1);
        closeValueSpinner2 = (Spinner) findViewById(R.id.closeValueSpinner2);
        closeValueSpinner3 = (Spinner) findViewById(R.id.closeValueSpinner3);

        windowButton = (Button) findViewById(R.id.windowButton);
        windowStatusButton = (Button) findViewById(R.id.windowStatusButton);
        sendButton = (Button) findViewById(R.id.sendButton);

        rdOn = (RadioButton) findViewById(R.id.rdOn);
        rdOff = (RadioButton) findViewById(R.id.rdOff);

        openCheck1 = (CheckBox) findViewById(R.id.openCheck1);
        openCheck2 = (CheckBox) findViewById(R.id.openCheck2);
        openCheck3 = (CheckBox) findViewById(R.id.openCheck3);
        closeCheck1 = (CheckBox) findViewById(R.id.closeCheck1);
        closeCheck2 = (CheckBox) findViewById(R.id.closeCheck2);
        closeCheck3 = (CheckBox) findViewById(R.id.closeCheck3);

        ArrayAdapter<String> outtterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, select);
        windowSelectSpinner.setAdapter(outtterAdapter);

        ArrayAdapter<String> ValueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, value);
        openValueSpinner1.setAdapter(ValueAdapter);
        openValueSpinner2.setAdapter(ValueAdapter);
        openValueSpinner3.setAdapter(ValueAdapter);
        closeValueSpinner1.setAdapter(ValueAdapter);
        closeValueSpinner2.setAdapter(ValueAdapter);
        closeValueSpinner3.setAdapter(ValueAdapter);

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<WindowDTO>> call = retrofitRequest.getWindow();
        call.enqueue(new RetrofitRetry<List<WindowDTO>>(call) {
            @Override
            public void onResponse(Call<List<WindowDTO>> call, Response<List<WindowDTO>> response) {
                windowArray = response.body();
                windowAdapter = new WindowSpinnerAdapter(WindowActivity.this);
                for (int i = 0; i < windowArray.size(); i++) {
                    windowAdapter.addItem(windowArray.get(i));
                };
                windowSpinner.setAdapter(windowAdapter);
                windowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        windowButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectPosition = position;
                                windowSelectLayout.setVisibility(View.VISIBLE);
                                if(windowAdapter.getItem(position).getStatus()==1)
                                    windowStatus = "열려있음";
                                else
                                    windowStatus = "닫혀있음";
                                windowStatusText.setText("창문 " + Integer.toString(windowAdapter.getItem(position).getId()) + "의 현재상태 : " + windowStatus);

                                windowOnOffLayout.setVisibility(View.INVISIBLE);
                                openLayout.setVisibility(View.INVISIBLE);
                                closeLayout.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        RetrofitRequest retrofitRequest2 = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<SensorDTO>> call2 = retrofitRequest2.getSensor();
        call2.enqueue(new RetrofitRetry<List<SensorDTO>>(call2) {
            @Override
            public void onResponse(Call<List<SensorDTO>> call2, Response<List<SensorDTO>> response) {
                sensorArray = response.body();
                sensorAdapter = new SensorSpinnerAdapter(WindowActivity.this);
                for (int i = 0; i < sensorArray.size(); i++) {
                    sensorAdapter.addItem(sensorArray.get(i));
                }
            }
        });

        windowSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                windowStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            windowOnOffLayout.setVisibility(View.VISIBLE);
                            openLayout.setVisibility(View.VISIBLE);
                            closeLayout.setVisibility(View.VISIBLE);
                            openCheckLayout2.setVisibility(View.VISIBLE);
                            openCheckLayout3.setVisibility(View.VISIBLE);
                            closeCheckLayout2.setVisibility(View.VISIBLE);
                            closeCheckLayout3.setVisibility(View.VISIBLE);

                            openCheck1.setText("내부온도");
                            openEdit1.setText(Double.toString(sensorAdapter.getItem(selectPosition).getTemp()));
                            openCheck2.setText("외부온도");
                            openEdit2.setText(Double.toString(windowAdapter.getItem(selectPosition).getTemp()));
                            openCheck3.setText("내부온도가 외부온도보다");
                            closeCheck1.setText("내부온도");
                            closeEdit1.setText(Double.toString(sensorAdapter.getItem(selectPosition).getTemp()));
                            closeCheck2.setText("외부온도");
                            closeEdit2.setText(Double.toString(windowAdapter.getItem(selectPosition).getTemp()));
                            closeCheck3.setText("내부온도가 외부온도보다");

                        } else if (position == 1) {
                            windowOnOffLayout.setVisibility(View.VISIBLE);
                            openLayout.setVisibility(View.VISIBLE);
                            closeLayout.setVisibility(View.VISIBLE);
                            openCheckLayout2.setVisibility(View.VISIBLE);
                            openCheckLayout3.setVisibility(View.VISIBLE);
                            closeCheckLayout2.setVisibility(View.VISIBLE);
                            closeCheckLayout3.setVisibility(View.VISIBLE);

                            openCheck1.setText("내부습도");
                            openEdit1.setText(Double.toString(sensorAdapter.getItem(selectPosition).getHumi()));
                            openCheck2.setText("외부습도");
                            openEdit2.setText(Double.toString(windowAdapter.getItem(selectPosition).getHumi()));
                            openCheck3.setText("내부습도가 외부습도보다");
                            closeCheck1.setText("내부습도");
                            closeEdit1.setText(Double.toString(sensorAdapter.getItem(selectPosition).getHumi()));
                            closeCheck2.setText("외부습도");
                            closeEdit2.setText(Double.toString(windowAdapter.getItem(selectPosition).getHumi()));
                            closeCheck3.setText("내부습도가 외부습도보다");
                        } else if (position == 2) {
                            windowOnOffLayout.setVisibility(View.VISIBLE);
                            openLayout.setVisibility(View.VISIBLE);
                            closeLayout.setVisibility(View.VISIBLE);
                            openCheck1.setText("외부 강수량");
                            openEdit1.setText(Double.toString(windowAdapter.getItem(selectPosition).getRain()));
                            openCheckLayout2.setVisibility(View.INVISIBLE);
                            openCheckLayout3.setVisibility(View.INVISIBLE);
                            closeCheck1.setText("외부 강수량");
                            closeEdit1.setText(Double.toString(windowAdapter.getItem(selectPosition).getRain()));
                            closeCheckLayout2.setVisibility(View.INVISIBLE);
                            closeCheckLayout3.setVisibility(View.INVISIBLE);
                        } else if (position == 3) {
                            windowOnOffLayout.setVisibility(View.VISIBLE);
                            openLayout.setVisibility(View.VISIBLE);
                            closeLayout.setVisibility(View.VISIBLE);
                            openCheck1.setText("외부 미세먼지");
                            openEdit1.setText(Double.toString(windowAdapter.getItem(selectPosition).getDust()));
                            openCheckLayout2.setVisibility(View.INVISIBLE);
                            openCheckLayout3.setVisibility(View.INVISIBLE);
                            closeCheck1.setText("외부 미세먼지");
                            closeEdit1.setText(Double.toString(windowAdapter.getItem(selectPosition).getDust()));
                            closeCheckLayout2.setVisibility(View.INVISIBLE);
                            closeCheckLayout3.setVisibility(View.INVISIBLE);
                        } else {
                            windowOnOffLayout.setVisibility(View.VISIBLE);
                            openLayout.setVisibility(View.VISIBLE);
                            closeLayout.setVisibility(View.VISIBLE);
                            openCheck1.setText("내부 가스량");
                            openEdit1.setText(Double.toString(sensorAdapter.getItem(selectPosition).getGas()));
                            openCheckLayout2.setVisibility(View.INVISIBLE);
                            openCheckLayout3.setVisibility(View.INVISIBLE);
                            closeCheck1.setText("내부 가스량");
                            closeEdit1.setText(Double.toString(sensorAdapter.getItem(selectPosition).getGas()));
                            closeCheckLayout2.setVisibility(View.INVISIBLE);
                            closeCheckLayout3.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                windowOnOffLayout.setVisibility(View.INVISIBLE);
                openLayout.setVisibility(View.INVISIBLE);
                closeLayout.setVisibility(View.INVISIBLE);
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
