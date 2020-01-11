package com.example.cash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

public class CreateActivity extends AppCompatActivity {
    Button btn_add, btn_cancel;
    Button btn_dataSelect;
    RadioGroup radioGroup;
    EditText et_money;
    EditText et_memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        btn_dataSelect = (Button)findViewById(R.id.btn_dateSelect);
        radioGroup = (RadioGroup)findViewById(R.id.radioBtn);
        et_money = (EditText)findViewById(R.id.editText_money);
        et_memo = (EditText)findViewById(R.id.editText_memo);
        CalendarPiker calendarpiker_add = new CalendarPiker(this, btn_dataSelect ,0);
        calendarpiker_add.updateDateBtnText(calendarpiker_add.setyear, calendarpiker_add.setmonth, calendarpiker_add.setday);

        /**
         * 스피터 셋팅
         */
        //payment 스피너에 데이터 출력
        ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinnerPayment = (Spinner)findViewById(R.id.spinner_payment);
        spinnerPayment.setPrompt("결제 수단을 선택하세요");
        spinnerPayment.setAdapter(adapterPayment);

        //category 스피너에 데이터 출력
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinnerCategory = (Spinner)findViewById(R.id.spinner_category);
        spinnerCategory.setPrompt("카테고리를 선택하세요");
        spinnerCategory.setAdapter(adapterCategory);

        /**
         * 버튼 리스너
         */
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(et_money.getText().length()==0 ) {   //et_money에 아무것도 안쓰여있을 경우
                    Toast.makeText(getApplicationContext(),"금액을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();

                    //HashMap처럼 key, value 설정
                    //이후 data.get~Extra("name") 으로 value값 불러올 수 있음
//                    String[] date_arr = btn_dataSelect.getText().toString().split("-");
//                    String DBdate = Arrays.toString(date_arr);
                    String DBdate = (String) btn_dataSelect.getText();
                    intent.putExtra("date", DBdate);

                    int radioID = radioGroup.getCheckedRadioButtonId(); // 선택된 버튼의 id값 리턴
                    String is_outcome = "0"; // 수입:0, 지출:1
                    if( radioID == R.id.radioBtn_outcome ){
                        is_outcome = "1";
                    }
                    intent.putExtra("checkinout", is_outcome);
                    intent.putExtra("money", et_money.getText().toString());
                    intent.putExtra("payment", spinnerPayment.getSelectedItem().toString());
                    intent.putExtra("category", spinnerCategory.getSelectedItem().toString());
                    intent.putExtra("memo", et_memo.getText().toString());

                    //이전 액티비티로 값을 갖고 돌아가는 메서드
                    //성공(RESULT_OK)와 실패(RESULT_CANCEL)을 설정해서
                    //결과가 어떠한지 알 수 있도록
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        //cancel버튼 리스너
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                //이 코드가 없어도 되는데, 있을때와 없을때 차이점을 모르겠네요.
                // mj: mainActivity에서 RESULT_CANCELED를 받았을 때 실행할 동작을
                // 따로 설정하지 않았기 때문에 사실 필요 없는 코드인데
                // 일단 혹시 창을 닫을 때 mainActivity에서 따로 실행시킬 동작이
                // 필요하게 될 수도 있으므로 넣어두는게 좋을 것 같습니다.

                finish();
            }
        });


    }

}
