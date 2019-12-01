package com.example.cash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ButtonListener {
    Context context;
    Button btn_startDate
            , btn_endDate
            , btn_search
            , btn_create
            , btn_delete;

    ButtonListener(Context context) {
        this.context = context;
        setButtonListener();
    }

    public void setButtonListener() {
        //레이아웃 리소스 가져오기
        btn_startDate = (Button)((Activity)context).findViewById(R.id.btn_startDate);
        btn_endDate = (Button)((Activity)context).findViewById(R.id.btn_endDate);
        btn_search = (Button)((Activity)context).findViewById(R.id.btn_search);


        //버튼 리스너 셋팅
        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(1);//다이얼로그 호출
            }
        });
        btn_endDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                showDatePickerDialog(2);//다이얼로그 호출
            }
        });
    }

    //datePickerDialog 보여주는 메서드
    public void showDatePickerDialog(int version){
        DatePickerDialog datePickerDialog;
        if(version==1) {
            datePickerDialog = new DatePickerDialog(context
                    , onDateSetListener_start //이벤트 리스너
                    , SetDate.startYear, SetDate.startMonth, SetDate.startDay); //초기 날짜
        }else{
            datePickerDialog = new DatePickerDialog(context
                    , onDateSetListener_end //이벤트 리스너
                    , SetDate.endYear, SetDate.endMonth, SetDate.endDay); //초기 날짜
        }
        datePickerDialog.setMessage("날짜를 설정하세요");
        datePickerDialog.show();
    }

    //datePickerDialog로 날짜 선택하면 시작 날짜 변경
    public DatePickerDialog.OnDateSetListener onDateSetListener_start
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view //onDateSet(): 콜백메서드
                , int year, int monthOfYear, int dayOfMonth) {
            System.out.println("날짜 변경 완료");
            SetDate.startYear = year;
            SetDate.startMonth = monthOfYear;
            SetDate.startDay = dayOfMonth;
            updateDateBtnText(SetDate.startYear, SetDate.startMonth, SetDate.startDay,1);
        }
    };

    //datePickerDialog로 날짜 선택하면 끝 날짜 변경
    public DatePickerDialog.OnDateSetListener onDateSetListener_end
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view //onDateSet(): 콜백메서드
                , int year, int monthOfYear, int dayOfMonth) {
            System.out.println("날짜 변경 완료");
            SetDate.endYear = year;
            SetDate.endMonth = monthOfYear;
            SetDate.endDay = dayOfMonth;
            updateDateBtnText(SetDate.endYear, SetDate.endMonth, SetDate.endDay,2);
        }
    };

    //날짜 선택 버튼 text 바꾸는 메서드
    public void updateDateBtnText(int year, int month, int day, int version){
        switch (version) {
            case 1: btn_startDate.setText(year+"-"+(month+1)+"-"+String.format("%02d", day)); break;   //시작 날짜 선택 버튼
            case 2: btn_endDate.setText(year+"-"+(month+1)+"-"+String.format("%02d", day)); break;    //끝 날짜 선택 버튼
        }
    }
}
