package com.example.nabot.activities;

import android.content.Intent;
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
import android.widget.RadioGroup;
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
import retrofit2.Callback;
import retrofit2.Response;

public class WindowActivity extends AppCompatActivity {

    TextView windowStatusText;
    EditText editText;
    Spinner windowSpinner, windowSelectSpinner, openValueSpinner1, openValueSpinner2;

    LinearLayout windowSelectLayout, openLayout, setting1, setting2;

    RadioButton rdButton1, rdButton2;
    RadioGroup rdGroup;
    Button button;

    String[] select = {"온도", "습도", "강수량", "미세먼지 수치"};
    String[] value = {"이상", "미만"};
    String windowStatus, temp_set="0.0", humi_set="0.0", rain_set="0.0", dust_set="0.0";
    int selectPosition, selectPosition2, tempOver=0, humiOver=0, rainOver=0, dustOver=0;

    List<WindowDTO> windowArray;
    List<SensorDTO> sensorArray;

    WindowSpinnerAdapter windowAdapter;
    SensorSpinnerAdapter sensorAdapter;

    WindowDTO windowDTO;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        windowSelectLayout = (LinearLayout) findViewById(R.id.windowSelectLayout);
        openLayout = (LinearLayout) findViewById(R.id.openLayout);
        setting1 = (LinearLayout) findViewById(R.id.setting1);
        setting2 = (LinearLayout) findViewById(R.id.setting2);

        windowStatusText = (TextView) findViewById(R.id.windowStatusText);

        editText = (EditText) findViewById(R.id.editText);

        windowSpinner = (Spinner) findViewById(R.id.windowSpinner);
        windowSelectSpinner = (Spinner) findViewById(R.id.windowSelectSpinner);
        openValueSpinner1 = (Spinner) findViewById(R.id.openValueSpinner1);
        openValueSpinner2 = (Spinner) findViewById(R.id.openValueSpinner2);

        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        rdButton1 = (RadioButton) findViewById(R.id.rdButton1);
        rdButton2 = (RadioButton) findViewById(R.id.rdButton2);

        button = (Button) findViewById(R.id.button1);

        final ArrayAdapter<String> outterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, select);;

        final ArrayAdapter<String> ValueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, value);
        openValueSpinner1.setAdapter(ValueAdapter);
        openValueSpinner2.setAdapter(ValueAdapter);

        final Intent intent = getIntent();
        windowDTO = (WindowDTO) intent.getSerializableExtra("window");



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

                        selectPosition = position;
                        windowSelectLayout.setVisibility(View.VISIBLE);
                        if(windowAdapter.getItem(position).getStatus()==1)
                            windowStatus = "열려있음";
                        else
                            windowStatus = "닫혀있음";
                        windowStatusText.setText("창문 " + Integer.toString(windowAdapter.getItem(position).getId()) + "의 현재상태 : " + windowStatus);
                        openLayout.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        windowSelectLayout.setVisibility(View.INVISIBLE);
                        openLayout.setVisibility(View.INVISIBLE);

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
                windowSelectSpinner.setAdapter(outterAdapter);
            }
        });

        windowSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPosition2=position;
                if (position == 0) {
                    openLayout.setVisibility(View.VISIBLE);

                    rdButton1.setText("내부온도");
                    editText.setText(Double.toString(sensorAdapter.getItem(selectPosition).getTemp()));
                    rdButton2.setText("내부온도가 외부온도보다");

                } else if (position == 1) {
                    openLayout.setVisibility(View.VISIBLE);

                    rdButton1.setText("내부습도");
                    editText.setText(Double.toString(sensorAdapter.getItem(selectPosition).getHumi()));
                    rdButton2.setText("내부습도가 외부습도보다");

                } else if (position == 2) {
                    openLayout.setVisibility(View.VISIBLE);

                    rdButton1.setText("강수량 ");
                    editText.setText(Double.toString(windowAdapter.getItem(selectPosition).getRain()));
                    rdButton2.setVisibility(View.INVISIBLE);

                } else if (position == 3) {
                    openLayout.setVisibility(View.VISIBLE);

                    rdButton1.setText("미세먼지 ");
                    editText.setText(Double.toString(windowAdapter.getItem(selectPosition).getDust()));
                    rdButton2.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                openLayout.setVisibility(View.INVISIBLE);
            }
        });
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rdButton1:
                        setting1.setVisibility(View.VISIBLE);
                        setting2.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rdButton2:
                        setting1.setVisibility(View.INVISIBLE);
                        setting2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition2 == 0) {
                    if(rdButton1.isChecked()){
                        temp_set = editText.getText().toString();
                        if(openValueSpinner1.getSelectedItem().equals("이상")){
                            tempOver=1;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("미만")){
                            tempOver=2;
                        }
                    }
                    else if(rdButton2.isChecked()){
                        if(openValueSpinner1.getSelectedItem().equals("이상")){
                            tempOver=3;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("미만")){
                            tempOver=4;
                        }
                    }
                } else if (selectPosition2 == 1) {
                    if(rdButton1.isChecked()){
                        humi_set = editText.getText().toString();
                        if(openValueSpinner1.getSelectedItem().equals("이상")){
                            humiOver=1;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("미만")){
                            humiOver=2;
                        }
                    }
                    else if(rdButton2.isChecked()){
                        if(openValueSpinner1.getSelectedItem().equals("이상")){
                            humiOver=3;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("미만")){
                            humiOver=4;
                        }
                    }
                } else if (selectPosition2 == 2) {if(rdButton1.isChecked()){
                        rain_set = editText.getText().toString();
                        if(openValueSpinner1.getSelectedItem().equals("이상")){
                            rainOver=1;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("미만")){
                            rainOver=2;
                        }
                    }

                } else if (selectPosition2 == 3) {if(rdButton1.isChecked()){
                        dust_set = editText.getText().toString();
                        if(openValueSpinner1.getSelectedItem().equals("이상")){
                            dustOver=1;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("미만")){
                            dustOver=2;
                        }
                    }
                }

                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                windowDTO = new WindowDTO(
                        windowAdapter.getItem(selectPosition).getId(),
                        Double.parseDouble(temp_set),tempOver,
                        Double.parseDouble(humi_set),humiOver,
                        Double.parseDouble(rain_set),rainOver,
                        Double.parseDouble(dust_set),dustOver
                );
                Call<Void> call = retrofitRequest.putWindow(windowDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }
        });
    }
}
