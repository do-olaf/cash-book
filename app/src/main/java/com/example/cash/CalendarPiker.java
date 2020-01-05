package com.example.cash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.time.Month;
import java.util.Calendar;
import java.util.logging.Logger;

public class CalendarPiker {
    Context context;
    Button button;
    Calendar calendar = Calendar.getInstance();
    int daybefore;      //오늘로부터 며칠 전으로 세팅할 것인지
    int setday, setmonth, setyear;

    // delete 버튼이나 create 버튼 눌렀을때 롤백시킬 날짜 값
    String preDate;

    CalendarPiker(Activity context, Button button, int daybefore) {
        this.context = context;
        this.button = button;
        this.daybefore = daybefore;

        setDate();
        setButtonListener();
        updateDateBtnText(setyear, setmonth, setday);
        setPreDate();
    }

    public void setDate(){
        calendar.add(Calendar.DATE,-daybefore);     //오늘로부터 daybefore일 만큼 전으로 셋팅
        setday = calendar.get(Calendar.DAY_OF_MONTH);
        setmonth =  calendar.get(Calendar.MONTH);
        setyear =  calendar.get(Calendar.YEAR);
    }

    public void setButtonListener() {
        //버튼 리스너 셋팅
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();//다이얼로그 호출
            }
        });
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(context
                    , onDateSetListener //이벤트 리스너
                    , setyear, setmonth,setday); //초기 날짜
        datePickerDialog.setMessage("날짜를 설정하세요");
        datePickerDialog.show();
    }

    public DatePickerDialog.OnDateSetListener onDateSetListener
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view //onDateSet(): 콜백메서드
                , int year, int monthOfYear, int dayOfMonth) {
            System.out.println("날짜 변경 완료");
            setyear = year;
            setmonth = monthOfYear;
            setday = dayOfMonth;
            updateDateBtnText(setyear, setmonth, setday);
        }
    };

    public void updateDateBtnText(int year, int month, int day){
        Log.d("year", year + "");
        String text = String.format("%04d", year);
        text += String.format("%02d", month+1);
        text += String.format("%02d", day);
        button.setText( text ); //날짜 선택 버튼
    }

    // 직전 date 값으로 롤백
    public void updateDateBtnText(){
        button.setText( preDate );
    }

    public void setPreDate() {
        this.preDate = (String) button.getText();
    }
}
