package com.example.cash;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase = null;
    DBOpenHelper dbOpenHelper = null;

    TextView textView;

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

        //DB 불러오기
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

                //호출한 activity로부터 결과를 돌려받으려면 startActivityForResult()
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }//onCreate()

    //DB 불러오는 메서드
    private void loadDB(){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(DBContract.table01._SELECT, null);

        String dbStr = "";
        textView = (TextView)findViewById(R.id.textView_sampleDB);

        //textView에 dbStr 셋팅
        while( cursor.moveToNext() ){
            dbStr += cursor.getInt(0) + "/";
            dbStr += cursor.getString(1) + "/";
            dbStr += cursor.getInt(2) + "/";
            dbStr += cursor.getString(3) + "/";
            dbStr += cursor.getString(4) + "/";
            dbStr += cursor.getString(5) + "\n";
            textView.setText(dbStr);
        }
        cursor.close();
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
//        String dbStr = "";
//        textView = (TextView)findViewById(R.id.textView_sampleDB);
//        dbStr += date;
//        dbStr += checkinout;
//        dbStr += Integer.parseInt(money) + "";
//        dbStr += payment;
//        dbStr += category;
//        dbStr += memo;
//        textView.setText(dbStr);
    }

    protected void create_popup(View v){
        //create 버튼을 클릭하면 실행되는 동작
    }
    protected void delete_popup(View v) {
        //delete 버튼을 클릭하면 실행되는 동작
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
            }
        }
    }
}
