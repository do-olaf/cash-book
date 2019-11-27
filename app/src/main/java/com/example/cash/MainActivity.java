package com.example.cash;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    /**
     * DB 셋팅
     */
    private final String dbName = "userDB";
    private final String tableName = "userTable";

    SQLiteDatabase sampleDB = null;
    ListView listView = (ListView)findViewById(R.id.listView);


    /**
     * onCreate(): 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용됨.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        /**
         * db list
         */
        try{
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            //테이블이 존재하지 않으면 새로 생성
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                    +"(idx INTEGER not null primary key autoincrement," +
                    "          checkInOut TEXT not null," +
                    "          money INTEGER not null," +
                    "          payment TEXT not null," +
                    "          category TEXT not null," +
                    "          memo TEXT);");

//            //테이블이 존재하는 경우 기존 데이터 지우기
//            sampleDB.execSQL("DELETE FROM" + tableName);
//
//            //새로운 데이터 테이블에 집어넣기

            sampleDB.close();
        }catch (SQLiteException se){
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        showList();
    }
    protected void create_popup(View v){
        //create 버튼을 클릭하면 실행되는 동작
    }
    protected void delete_popup(View v) {
        //delete 버튼을 클릭하면 실행되는 동작
    }

    /**
     * 테이블에 db list 보여주는 메서드
     */
    protected  void showList(){
        List<UserVO> list = new ArrayList<>();

        /**
         * 샘플 데이터 입력해본 것 (나중에 지우기)
         */
        UserVO voSample = new UserVO();
        voSample.setIdx(1);
        voSample.setCategory("카테고리");
        voSample.setCheck("수/지");
        voSample.setDate("날짜");
        voSample.setMemo("메모");
        voSample.setMoney(5000);
        voSample.setPayment("체크카드");
        list.add(voSample);

        try{
            SQLiteDatabase readDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            //select문 사용해서 테이블에 있는 데이터 가져오기
            Cursor cursor = readDB.rawQuery("SELECT * FROM " + tableName, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        UserVO vo = new UserVO();
                        vo.setIdx( cursor.getInt( cursor.getColumnIndex("idx") ) );
                        vo.setCategory( cursor.getString( cursor.getColumnIndex("category") ) );
                        vo.setPayment( cursor.getString( cursor.getColumnIndex("payment") ) );
                        vo.setMoney( cursor.getLong( cursor.getColumnIndex("money") ) );
                        vo.setMemo( cursor.getString( cursor.getColumnIndex("memo") ) );
                        vo.setDate( cursor.getString( cursor.getColumnIndex("date") ) );
                        vo.setCheck( cursor.getString( cursor.getColumnIndex("check") ) );
                        list.add(vo);
                    }while (cursor.moveToNext());
                }
            }
            readDB.close();

            //list와 listView를 연결하기 위해 arrayAdapter 사용
            ArrayAdapter<UserVO> adapter = new ArrayAdapter<>(
                    this, //context(액티비티 인스턴스)
                    R.layout.list_item, //리스트 레이아웃
                    list //데이터가 저장된 ArrayList 객체
                    );
            listView.setAdapter(adapter);
        }catch (SQLiteException se){
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
    }







}
