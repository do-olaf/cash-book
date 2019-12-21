package com.example.cash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase = null;
    DBOpenHelper dbOpenHelper = null;
    Button btn_startDate, btn_endDate;
    TextView textView;
    ListView listView;
    MyCursorAdapter myAdapter;

    //하나의 onActivityResult()에서 여러 개의 startActivityForResult()를 구분하기 위한 상수
    private int REQUEST_CODE = 1;

    /**
     * onCreate(): 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용됨.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        //DB 불러오기
        loadDB();

        /**
         * 날짜, 버튼 셋팅
         */
        btn_startDate = (Button)(findViewById(R.id.btn_startDate));
        btn_endDate = (Button)(findViewById(R.id.btn_endDate));
        CalendarPiker calpik_st = new CalendarPiker(this, btn_startDate ,14);
        CalendarPiker calpik_end = new CalendarPiker(this,btn_endDate,0);
        //시작 & 끝 선택 버튼 텍스트 셋팅
        calpik_st.updateDateBtnText(calpik_st.setyear, calpik_st.setmonth, calpik_st.setday);
        calpik_end.updateDateBtnText(calpik_end.setyear,calpik_end.setmonth,calpik_end.setday);


        //create 버튼 리스너 셋팅
        Button btn_create = (Button)findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //createActivity 실행
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);

                //호출한 activity로부터 결과를 돌려받으려면 startActivityForResult()
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // delete 버튼 리스너 셋팅
        Button btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 이거 어떤 방식으로 삭제할까요?
            }
        });
    }//onCreate()

    //DB 불러오는 메서드
    private void loadDB(){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(DBContract.table01._SELECT, null);
        //MyCursorAdapter에 지정한 DB불러오기 양식을 lstView에 구현
        myAdapter = new MyCursorAdapter(this,cursor);
        listView.setAdapter(myAdapter);
        //cursor.close();
    }

    //DB 데이터 추가하는 메서드
    private void insertDB(String date, String checkinout, String money
                        , String payment, String category, String memo){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();

        String sql_Insert = DBContract.table01._INSERT +
                " (" +
                "'" + date + "', " +
                "'" + checkinout + "', " +
                Integer.parseInt(money) + ", " +
                "'" + payment + "', " +
                "'" + category + "', " +
                "'" + memo + "')";

        sqLiteDatabase.execSQL(sql_Insert);
    }

    //createActivity에서 저장된 결과 돌려주는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE ){
            if(resultCode == RESULT_OK){
                    insertDB(
                            data.getStringExtra("date")
                            , data.getStringExtra("checkinout")
                            , data.getStringExtra("money")
                            , data.getStringExtra("payment")
                            , data.getStringExtra("category")
                            , data.getStringExtra("memo")
                    );
                    loadDB();
 //               }
            }
        }
    }
}
