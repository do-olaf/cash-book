package com.example.cash;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button btn_startDate, btn_endDate;
    Button btn_search, btn_create;

    int nowDay,nowMonth, nowYear; //현재 날짜
    Calendar nowDate, startDate;
    int startDay, startMonth, startYear; //시작 선택 날짜
    int endDay, endMonth, endYear; //끝 선택 날짜



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate(): 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용됨.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout 리소스 가져오기
        btn_startDate = (Button)findViewById(R.id.btn_startDate);
        btn_endDate = (Button)findViewById(R.id.btn_endDate);
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_create = (Button)findViewById(R.id.btn_create);

        //버튼 리스너
        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(1);//다이얼로그 호출
            }
        });
        btn_endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog(2);//다이얼로그 호출
            }
            });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_popup(v);
            }
        });

        //현재 날짜 가져오기
        nowDate = Calendar.getInstance();
        nowDay = nowDate.get(Calendar.DAY_OF_MONTH);
        nowMonth = nowDate.get(Calendar.MONTH);
        nowYear = nowDate.get(Calendar.YEAR);

        //시작 날짜 (14일 전으로 맞춤)
        startDate = nowDate;
        startDate.add(Calendar.DATE,-14);
        startDay = startDate.get(Calendar.DAY_OF_MONTH);
        startMonth =  startDate.get(Calendar.MONTH);
        startYear =  startDate.get(Calendar.YEAR);
        updateDateDisplay(startYear, startMonth, startDay,1);

        //끝 날짜 text
        endDay = nowDay;
        endMonth = nowMonth;
        endYear = nowYear;
        updateDateDisplay(endYear, endMonth, endDay,2);
    }

    protected void delete_popup(View v) {
        //delete 버튼을 클릭하면 실행되는 동작
    }
    protected void create_popup(View v){
        Toast.makeText(getApplicationContext(),"Create",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,PopupActivity.class);
        startActivity(intent);
    }
    //-------------------------------------------------
    //달력 선택
    //-------------------------------------------------

    private void showDatePickerDialog(int version){
        DatePickerDialog datePickerDialog;
        if(version==1) {
            datePickerDialog = new DatePickerDialog(this
                    , onDateSetListener_start //이벤트 리스너
                    , startYear, startMonth, startDay); //초기 날짜
        }else{
                datePickerDialog = new DatePickerDialog(this
                        , onDateSetListener_end //이벤트 리스너
                        , endYear, endMonth, endDay); //초기 날짜
        }
        datePickerDialog.setMessage("날짜를 설정하세요");
        datePickerDialog.show();
    }


    //날짜 선택 버튼 text 바꾸는 메서드
    private void updateDateDisplay(int year, int month, int day, int version){
        switch (version) {
            case 1: btn_startDate.setText(year+"-"+(month+1)+"-"+day); break;   //시작 날짜 선택 버튼
            case 2:  btn_endDate.setText(year+"-"+(month+1)+"-"+day); break;    //끝 날짜 선택 버튼
        }
    }

    //시작 날짜 변경
    private DatePickerDialog.OnDateSetListener onDateSetListener_start
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view //onDateSet(): 콜백메서드
                , int year, int monthOfYear, int dayOfMonth) {
            System.out.println("날짜 변경 완료");
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;
            updateDateDisplay(startYear, startMonth, startDay,1);
        }
    };

    //끝 날짜 변경
    private DatePickerDialog.OnDateSetListener onDateSetListener_end
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view //onDateSet(): 콜백메서드
                , int year, int monthOfYear, int dayOfMonth) {
            System.out.println("날짜 변경 완료");
            endYear = year;
            endMonth = monthOfYear;
            endDay = dayOfMonth;
            updateDateDisplay(endYear, endMonth, endDay,2);
        }
    };


}
