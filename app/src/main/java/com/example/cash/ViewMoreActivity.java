package com.example.cash;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.cash.DBContract.table01.CATEGORY;
import static com.example.cash.DBContract.table01.CHECKINOUT;
import static com.example.cash.DBContract.table01.DATE;
import static com.example.cash.DBContract.table01.MEMO;
import static com.example.cash.DBContract.table01.MONEY;
import static com.example.cash.DBContract.table01.PAYMENT;
import static com.example.cash.MyCursorAdapter.clickedItemPosition;

public class ViewMoreActivity  extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase = null;
    DBOpenHelper dbOpenHelper = null;
    MyCursorAdapter myAdapter;
    ArrayAdapter<CharSequence> adapterPayment;
    ArrayAdapter<CharSequence> adapterCategory;

    Button btn_edit, btn_cancel;
    Button btn_dataSelect;
    RadioGroup radioGroup;
    RadioButton radioBtn_income;
    RadioButton radioBtn_outcome;
    EditText et_money;
    EditText et_memo;
    Spinner spinnerPayment;
    Spinner spinnerCategory;

    //DB에서 불러와서 저장할 데이터들
    String date;
    String checkinout;
    String money;
    String payment;
    String category;
    String memo;

    // intent 로 가져온 날짜 데이터
    String startDate = "";
    String endDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmore);

        Intent intent = getIntent();
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");

        btn_dataSelect = (Button)findViewById(R.id.btn_dateSelect);
        radioGroup = (RadioGroup)findViewById(R.id.radioBtn);
        radioBtn_income = (RadioButton)findViewById(R.id.radioBtn_income);
        radioBtn_outcome = (RadioButton)findViewById(R.id.radioBtn_outcome);

        et_money = (EditText)findViewById(R.id.editText_money);
        et_memo = (EditText)findViewById(R.id.editText_memo);
        CalendarPiker calendarpiker_add = new CalendarPiker(this, btn_dataSelect ,0);
        calendarpiker_add.updateDateBtnText(calendarpiker_add.setyear, calendarpiker_add.setmonth, calendarpiker_add.setday);


        /**
        해당하는 transaction의 데이터 로딩
         */
        loadDB(); //해당하는 transaction의 데이터 불러오기

        /**
         * 스피터 셋팅
         */
        //payment 스피너셋팅
        adapterPayment = ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayment = (Spinner)findViewById(R.id.spinner_payment);
        spinnerPayment.setPrompt(payment);
        spinnerPayment.setAdapter(adapterPayment);

        //category 스피너셋팅
        adapterCategory = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory = (Spinner)findViewById(R.id.spinner_category);
        spinnerCategory.setPrompt(category);
        spinnerCategory.setAdapter(adapterCategory);

        /**
         해당하는 transaction의 데이터 출력
         */
        setData();

        /**
         * 버튼 리스너
         */
        btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(et_money.getText().length()==0 ) {   //et_money에 아무것도 안쓰여있을 경우
                    Toast.makeText(getApplicationContext(),"금액을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();

                    //HashMap처럼 key, value 설정
                    //이후 data.get~Extra("name") 으로 value값 불러올 수 있음
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

    //해당 transaction의 정보를 DB에서 불러와서 저장
    private void loadDB(){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();

        String sql_select = DBContract.table01._SELECTALL + " where _id=" + clickedItemPosition;
        Log.d("select문", sql_select);
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select, null);

        while( cursor.moveToNext() ) {
            date = cursor.getString(cursor.getColumnIndex( DATE ));
            checkinout = cursor.getString(cursor.getColumnIndex( CHECKINOUT ));
            money = cursor.getString(cursor.getColumnIndex(MONEY));
            payment = cursor.getString(cursor.getColumnIndex(PAYMENT));
            category = cursor.getString(cursor.getColumnIndex(CATEGORY));
            memo = cursor.getString(cursor.getColumnIndex(MEMO));
        }

        cursor.close();
    }

    //불러온 정보를 화면에 출력
    private void setData(){
        if(checkinout.equals("0")){ //수입: 0, 지출: 1
            radioBtn_income.setChecked(true);
        }else{ //현재 else만 실행되는 상황 -> string 이라 .equals() 로 비교해야돼요~
            radioBtn_outcome.setChecked(true);
        }
        btn_dataSelect.setText(date);
        et_money.setText(money);
        et_memo.setText(memo);
        spinnerCategory.setSelection(adapterCategory.getPosition(category));
        spinnerPayment.setSelection(adapterPayment.getPosition(payment));
    }
}
