package com.example.cash;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate(): 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용됨.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SetDate(); //날짜 데이터 셋팅
        ButtonListener buttonListener = new ButtonListener(this); //버튼 리스너 정의

        //시작 & 끝 선택 버튼 텍스트 셋팅
        buttonListener.updateDateBtnText(SetDate.startYear, SetDate.startMonth, SetDate.startDay,1);
        buttonListener.updateDateBtnText(SetDate.endYear, SetDate.endMonth, SetDate.endDay,2);

        //create 버튼 리스너 셋팅
        Button btn_create = (Button)findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //createActivity 실행
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void create_popup(View v){
        //create 버튼을 클릭하면 실행되는 동작
    }
    protected void delete_popup(View v) {
        //delete 버튼을 클릭하면 실행되는 동작
    }

}
