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
import com.example.nabot.adapter.CurtainSpinnerAdapter;
import com.example.nabot.adapter.WindowSpinnerAdapter;
import com.example.nabot.domain.CurtainDTO;

import com.example.nabot.util.RetrofitRequest;
import com.example.nabot.util.RetrofitRetry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CurtainActivity extends AppCompatActivity {
    TextView curtainStatusText;
    EditText openEdit1,closeEdit1;
    Spinner curtainSpinner,curtainSelectSpinner,openValueSpinner1,closeValueSpinner1;

    Button curtainButton,curtainStatusButton;
    LinearLayout curtainSelectLayout,curtainOnOffLayout,openLayout,closeLayout,openCheckLayout1,closeCheckLayout1;

    RadioButton rdOn,rdOff;
    CheckBox openCheck1,closeCheck1;

    String[] select = {"조도량"};
    String[] value = {"이상","미만"};
    String curtainStatus;
    int selectPosition;

    List<CurtainDTO> curtainArray;
    CurtainSpinnerAdapter curtainAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curtain);

        curtainSelectLayout = (LinearLayout) findViewById(R.id.curtainSelectLayout);
        curtainOnOffLayout = (LinearLayout) findViewById(R.id.curtainOnOffLayout);
        openLayout = (LinearLayout) findViewById(R.id.openLayout);
        closeLayout = (LinearLayout) findViewById(R.id.closeLayout);
        openCheckLayout1 = (LinearLayout) findViewById(R.id.openCheckLayout1);
        closeCheckLayout1 = (LinearLayout) findViewById(R.id.closeCheckLayout1);

        curtainStatusText = (TextView) findViewById(R.id.curtainStatusText);

        openEdit1 = (EditText) findViewById(R.id.openEdit1);
        closeEdit1 = (EditText) findViewById(R.id.closeEdit1);

        curtainSpinner = (Spinner) findViewById(R.id.curtainSpinner);
        curtainSelectSpinner = (Spinner) findViewById(R.id.curtainSelectSpinner);
        openValueSpinner1 = (Spinner) findViewById(R.id.openValueSpinner1);
        closeValueSpinner1 = (Spinner) findViewById(R.id.closeValueSpinner1);

        curtainButton = (Button) findViewById(R.id.curtainButton);
        curtainStatusButton = (Button) findViewById(R.id.curtainStatusButton);

        rdOn = (RadioButton) findViewById(R.id.rdOn);
        rdOff = (RadioButton) findViewById(R.id.rdOff);

        openCheck1 = (CheckBox) findViewById(R.id.openCheck1);
        closeCheck1 = (CheckBox) findViewById(R.id.closeCheck1);


        ArrayAdapter<String> outtterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,select);
        curtainSelectSpinner.setAdapter(outtterAdapter);

        ArrayAdapter<String> ValueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,value);
        openValueSpinner1.setAdapter(ValueAdapter);
        closeValueSpinner1.setAdapter(ValueAdapter);

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
                        curtainButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectPosition = position;
                                curtainSelectLayout.setVisibility(View.VISIBLE);
                                if(curtainAdapter.getItem(position).getStatus()==1)
                                    curtainStatus = "열려있음";
                                else
                                    curtainStatus = "닫혀있음";
                                curtainStatusText.setText("창문 " + Integer.toString(curtainAdapter.getItem(position).getId()) + "의 현재상태 : " + curtainStatus);

                                curtainOnOffLayout.setVisibility(View.INVISIBLE);
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


        curtainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                // Toast.makeText(WindowActivity.this, spinnerAdapter.getItem(position).getId(), Toast.LENGTH_SHORT).show();
                curtainButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        curtainSelectLayout.setVisibility(View.VISIBLE);

                        curtainOnOffLayout.setVisibility(View.INVISIBLE);
                        openLayout.setVisibility(View.INVISIBLE);
                        closeLayout.setVisibility(View.INVISIBLE);
                     }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        curtainSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                curtainStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position==0){
                            curtainOnOffLayout.setVisibility(View.VISIBLE);
                            openLayout.setVisibility(View.VISIBLE);
                            closeLayout.setVisibility(View.VISIBLE);

                            openCheck1.setText("햇빛량");
                            openCheck1.setText("햇빛량"); openEdit1.setText(Integer.toString(curtainAdapter.getItem(position).getLux()));
                            closeCheck1.setText("햇빛량");
                            closeCheck1.setText("햇빛량"); closeEdit1.setText(Integer.toString(curtainAdapter.getItem(position).getLux()));

                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curtainOnOffLayout.setVisibility(View.INVISIBLE);
                openLayout.setVisibility(View.INVISIBLE);
                closeLayout.setVisibility(View.INVISIBLE);
            }
        });

    }

}
