package com.example.cash;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
        editdate = (EditText)findViewById(R.id.EditText01);
        btn_startDate = (Button)findViewById(R.id.startDate_popup);

        //버튼 리스너
        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_Dialog_ID);//다이얼로그 호출
            }
        });

        //현재 날짜 가져오기
        nowDate = Calendar.getInstance();
        nowDay = nowDate.get(Calendar.DAY_OF_MONTH);
        nowMonth = nowDate.get(Calendar.MONTH);
        nowYear = nowDate.get(Calendar.YEAR);

        //현재 날짜로 시작하는 editdate(editText 컴포넌트)
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
    EditText editdate;
    Button btn_startDate;
    final int Date_Dialog_ID = 0;

    int nowDay,nowMonth, nowYear; //현재 날짜
    Calendar nowDate;
    int startDay, startMonth, startYear; //선택 날짜

    //showDialog()호출되면 적절한 Dialog인스턴스를 반환하는 메서드
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case Date_Dialog_ID:
                return new DatePickerDialog(this //
                        , onDateSet //이벤트 리스너
                        , nowYear, nowMonth, nowDay); //초기 날짜
        }
        return null;
    }

    //editText 컴포넌트 값 바꾸는 메서드
    private void updateDateDisplay(int year, int month, int day){
        editdate.setText(year+"-"+(month+1)+"-"+day);
        //month
    }

    private DatePickerDialog.OnDateSetListener onDateSet
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
