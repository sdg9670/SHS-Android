package com.example.nabot.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.nabot.R;
import com.example.nabot.adapter.CurtainSpinnerAdapter;
import com.example.nabot.adapter.WindowSpinnerAdapter;
import com.example.nabot.domain.CurtainDTO;

import com.example.nabot.domain.WindowDTO;
import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurtainActivity extends AppCompatActivity {

    TextView curtainStatusText;
    EditText editText;
    Spinner curtainSpinner, curtainSelectSpinner, openValueSpinner1, openValueSpinner2;

    LinearLayout curtainSelectLayout, openLayout, setting1;

    RadioButton rdButton1,rdButton;
    RadioGroup rdGroup;
    Button button;

    String[] select = {"조도량"};
    String[] value = {"열림", "닫힘"};
    String curtainStatus, lux_set = "0.0";
    int selectPosition, selectPosition2, luxOver = 0;

    List<CurtainDTO> curtainArray;
    CurtainSpinnerAdapter curtainAdapter;

    CurtainDTO curtainDTO;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curtain);

        curtainSelectLayout = (LinearLayout) findViewById(R.id.curtainSelectLayout);
        openLayout = (LinearLayout) findViewById(R.id.openLayout);
        setting1 = (LinearLayout) findViewById(R.id.setting1);

        curtainStatusText = (TextView) findViewById(R.id.curtainStatusText);

        editText = (EditText) findViewById(R.id.editText);

        curtainSpinner = (Spinner) findViewById(R.id.curtainSpinner);
        curtainSelectSpinner = (Spinner) findViewById(R.id.curtainSelectSpinner2);
        openValueSpinner1 = (Spinner) findViewById(R.id.openValueSpinner1);
        openValueSpinner2 = (Spinner) findViewById(R.id.openValueSpinner2);

        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        rdButton = (RadioButton) findViewById(R.id.rdButton);
        rdButton1 = (RadioButton) findViewById(R.id.rdButton1);

        button = (Button) findViewById(R.id.button1);


        final ArrayAdapter<String> outterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, select);

        final ArrayAdapter<String> ValueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,value);
        openValueSpinner1.setAdapter(ValueAdapter);

        final Intent intent = getIntent();
        curtainDTO = (CurtainDTO) intent.getSerializableExtra("curtain");

        RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
        Call<List<CurtainDTO>> call = retrofitRequest.getCurtain();
        call.enqueue(new RetrofitRetry<List<CurtainDTO>>(call) {
            @Override
            public void onResponse(Call<List<CurtainDTO>> call, Response<List<CurtainDTO>> response) {
                curtainArray = response.body();
                curtainAdapter = new CurtainSpinnerAdapter(CurtainActivity.this);
                for (int i = 0; i < curtainArray.size(); i++) {
                    curtainAdapter.addItem(curtainArray.get(i));
                };
                curtainSpinner.setAdapter(curtainAdapter);
                curtainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                        selectPosition = position;
                        curtainSelectLayout.setVisibility(View.VISIBLE);
                        if(curtainAdapter.getItem(position).getStatus()==1)
                            curtainStatus = "열려있음";
                        else
                            curtainStatus = "닫혀있음";
                        curtainStatusText.setText("커튼 " + Integer.toString(curtainAdapter.getItem(position).getId()) + "의 현재상태 : " + curtainStatus);
                        openLayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                curtainSelectSpinner.setAdapter(outterAdapter);
            }
        });
        curtainSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                selectPosition2=position;

                if(position==0){
                    openLayout.setVisibility(View.VISIBLE);

                    rdButton1.setText("햇빛량");
                    editText.setText(Double.toString(curtainAdapter.getItem(selectPosition).getLux()));
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
                        break;
                    case R.id.rdButton:
                        setting1.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition2 == 0) {
                    if(rdButton1.isChecked()){
                        lux_set = editText.getText().toString();
                        if(openValueSpinner1.getSelectedItem().equals("열림")){
                            luxOver=1;
                        }
                        else if(openValueSpinner1.getSelectedItem().equals("닫힘")){
                            luxOver=2;
                        }
                    }
                    else if(rdButton.isChecked()){
                        luxOver=0;
                    }
                }

                RetrofitRequest retrofitRequest = RetrofitRequest.retrofit.create(RetrofitRequest.class);
                curtainDTO = new CurtainDTO(
                        curtainAdapter.getItem(selectPosition).getId(),
                        Double.parseDouble(lux_set),luxOver
                );
                Call<Void> call = retrofitRequest.putCurtain(curtainDTO);
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

