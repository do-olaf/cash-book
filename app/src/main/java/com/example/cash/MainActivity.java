package com.example.cash;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate(): 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용됨.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout 리소스 가져오기
        btn_startDate = (Button)findViewById(R.id.startDate_popup);

        //버튼 리스너
        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();//다이얼로그 호출
            }
        });

        //현재 날짜 가져오기
        nowDate = Calendar.getInstance();
        nowDay = nowDate.get(Calendar.DAY_OF_MONTH);
        nowMonth = nowDate.get(Calendar.MONTH);
        nowYear = nowDate.get(Calendar.YEAR);

        //시작 날짜 버튼 text
        startDay = nowDay;
        startMonth = nowMonth;
        startYear = nowYear;
        updateDateDisplay(startYear, startMonth, startDay);
    }
    protected void create_popup(View v){
        //create 버튼을 클릭하면 실행되는 동작
    }
    protected void delete_popup(View v) {
        //delete 버튼을 클릭하면 실행되는 동작
    }

    //-------------------------------------------------
    //달력 선택
    //-------------------------------------------------
    Button btn_startDate;

    int nowDay,nowMonth, nowYear; //현재 날짜
    Calendar nowDate;
    int startDay, startMonth, startYear; //선택 날짜

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this
                , onDateSetListener //이벤트 리스너
                , nowYear, nowMonth, nowDay); //초기 날짜

        datePickerDialog.setMessage("시작 날짜를 설정하세요");
        datePickerDialog.show();
    }

    //시작날짜 버튼 text 바꾸는 메서드
    private void updateDateDisplay(int year, int month, int day){
        btn_startDate.setText(year+"-"+(month+1)+"-"+day);
        //month
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view //onDateSet(): 콜백메서드
                , int year, int monthOfYear, int dayOfMonth) {
            System.out.println("날짜 변경 완료");
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;
            updateDateDisplay(startYear, startMonth, startDay);
        }
    };
}
