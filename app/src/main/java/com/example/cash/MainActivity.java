package com.example.cash;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase = null;
    DBOpenHelper dbOpenHelper = null;

    TextView textView;

    /**
     * onCreate(): 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용됨.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * DB 셋팅
         */
        loadDB();

        /**
         * 날짜, 버튼 셋팅
         */
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
    }//onCreate()

    private void loadDB(){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(DBContract.table01._SELECT, null);

        String dbStr = "임시로 넣어봤습니다";
        textView = (TextView)findViewById(R.id.textView_sampleDB);
        while( cursor.moveToNext() ){
            textView.setText(dbStr);
            dbStr += cursor.getInt(0) + "";
            dbStr += cursor.getString(1);
            dbStr += cursor.getInt(2) + "";
            dbStr += cursor.getString(3);
            dbStr += cursor.getString(4);
            dbStr += cursor.getString(5);
        }
        cursor.close();
    }

    protected void create_popup(View v){
        //create 버튼을 클릭하면 실행되는 동작
    }
    protected void delete_popup(View v) {
        //delete 버튼을 클릭하면 실행되는 동작
    }


}
