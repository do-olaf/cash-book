package com.example.cash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import static com.example.cash.MyCursorAdapter.clickedItemPosition;
import static com.example.cash.MyCursorAdapter.selectedItemsPositions;


public class MainActivity extends AppCompatActivity  {
    SQLiteDatabase sqLiteDatabase = null;
    DBOpenHelper dbOpenHelper = null;
    Button btn_startDate, btn_endDate;
    ListView listView;
    MyCursorAdapter myAdapter;
    CalendarPiker calpick_st = null;
    CalendarPiker calpick_end = null;

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

        /**
         * 날짜, 버튼 셋팅
         */
        btn_startDate = (Button)(findViewById(R.id.btn_startDate));
        btn_endDate = (Button)(findViewById(R.id.btn_endDate));
        calpick_st = new CalendarPiker(this, btn_startDate ,14);
        calpick_end = new CalendarPiker(this,btn_endDate,0);

        // DB 불러와서 리스트 셋팅
        listView = (ListView) findViewById(R.id.listView);
        loadDB( (String) btn_startDate.getText(), (String) btn_endDate.getText() );

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
                deleteDB();
                calpick_st.updateDateBtnText();
                calpick_end.updateDateBtnText();
                loadDB( (String) btn_startDate.getText(), (String) btn_endDate.getText() );
            }
        });

        // search 버튼 리스너 셋팅
        Button btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Integer.parseInt((String)btn_startDate.getText()) > Integer.parseInt((String)btn_endDate.getText()) ){
                    Toast.makeText(getApplicationContext(),"잘못된 기간 설정입니다",Toast.LENGTH_SHORT).show();
                    return;
                }
                calpick_st.setPreDate();
                calpick_end.setPreDate();
                loadDB( (String) btn_startDate.getText(), (String) btn_endDate.getText() );
            }
        });
    }//onCreate()

    //DB 불러오는 메서드
    private void loadDB(String startDate, String endDate){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();

        String sql_select = DBContract.table01._SELECT + startDate + " and " + endDate;
        Log.d("select문", sql_select);
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select, null);

        //MyCursorAdapter에 지정한 DB불러오기 양식을 lstView에 구현
        myAdapter = new MyCursorAdapter(this, cursor);
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

    // DB 데이터 삭제하는 메서드
    private void deleteDB(){
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();

        for(int i =0 ;i<selectedItemsPositions.size();i++) {
            int _id = selectedItemsPositions.get(i);
            String sql_delete = DBContract.table01._DELETE +_id;
            sqLiteDatabase.execSQL(sql_delete);
        }
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
                    calpick_st.updateDateBtnText();
                    calpick_end.updateDateBtnText();
                    loadDB((String) btn_startDate.getText(), (String) btn_endDate.getText());
            }
        }
    }

}
