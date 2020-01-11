package com.example.cash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.cash.DBContract.table01.CATEGORY;
import static com.example.cash.DBContract.table01.CHECKINOUT;
import static com.example.cash.DBContract.table01.DATE;
import static com.example.cash.DBContract.table01.MEMO;
import static com.example.cash.DBContract.table01.MONEY;
import static com.example.cash.DBContract.table01.PAYMENT;

public class MyCursorAdapter extends CursorAdapter {
    // 체크박스가 체크된 item의 position값 저장할 List

    static List<Integer> selectedItemsPositions;        //checkbox list
    static int clickedItemPosition;             //btn_viewmore 클릭된 item의 position
    CheckBox checkBox;
    Button btn_viewmore;

    @SuppressWarnings("deprecation")
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        selectedItemsPositions = new ArrayList<>();
    }

    /**
     * 화면에 출력한 view와 content-provider의 column을 연결(binding)하는 역할
     * (스크롤할때마다 bindView를 하기 때문에 checked값을 따로 배열에 저장해뒀다
     * bindView를 할때 배열에 저장된 값을 갖고 와서 setChecked를 해줘야 됨)
     * @param view newView()에서 반환된 view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //list_item에 있는 컴포넌트들 객체로 받아오기
        TextView tvDate = (TextView) view.findViewById( R.id.date );
        TextView tvCheck = (TextView) view.findViewById( R.id.checkinout );
        TextView tvMoney = (TextView) view.findViewById( R.id.money );
        TextView tvPayment = (TextView) view.findViewById( R.id.payment );
        TextView tvCategory = (TextView) view.findViewById( R.id.category );

        //DB에 있는 데이터를 cursor를 움직이며 받아오기
        String date = cursor.getString( cursor.getColumnIndex( DATE ) );
        String checkinout = cursor.getString( cursor.getColumnIndex( CHECKINOUT ) ).equals("0") ? "수입" : "지출";
        String money = new DecimalFormat("#,##0").format(Integer.parseInt(cursor.getString( cursor.getColumnIndex( MONEY ) )));
        String payment = cursor.getString( cursor.getColumnIndex( PAYMENT ) );
        String category = cursor.getString( cursor.getColumnIndex( CATEGORY ) );
//        Log.d("스트링 확인", date + ", " + checkinout); //: 아마 필요 없는 코드. 다시 확인해보고 지울게요

        //받아온 데이터 -> 컴포넌트에 텍스트 입력
        tvDate.setText( date );
        tvCheck.setText( checkinout );
        tvMoney.setText(money);
        tvPayment.setText(payment);
        tvCategory.setText(category);

        // 체크박스 바인딩
        checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
        checkBox.setTag(cursor.getInt(0)); // id 값으로 tag 셋팅
        if (selectedItemsPositions.contains(cursor.getInt(0)))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        // 더보기 버튼 바인딩
        btn_viewmore = (Button) view.findViewById(R.id.btn_viewmore1);
        btn_viewmore.setTag(cursor.getInt(0));  // id 값으로 tag셋팅
    }

    /**
     * view 생성하여 화면에 출력하는 역할
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        //list_item에 만든 레이아웃을 확장하여 activity_main에 있는 listView에 넣는 개념
        final LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list_item, parent, false );

        // 체크박스 리스너 셋팅
        checkBox = (CheckBox) v.findViewById(R.id.checkBox1); // 체크박스
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // 체크상태 변경 감지 리스너
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                int position = (int) compoundButton.getTag(); // position값 가져오기
                if (isChecked) {
                    // 리스트에 position값이 저장되지 않았다면 add
                    if (!selectedItemsPositions.contains(position))
                        Log.d("체크", position + "");
                        selectedItemsPositions.add(position);
                } else {
                    // 리스트에서 position값 remove
                    // 단, List에서 숫자값을 remove 할때는 반드시 캐스팅 필요
                    // 만약 int로 입력하면 idx에 해당하는 값이 지워짐
                    Log.d("체크해제", position + "");
                    selectedItemsPositions.remove((Integer) position);
                }
            }
        });

        /*View More 버튼 리스너 세팅*/
        btn_viewmore = (Button) v.findViewById(R.id.btn_viewmore1);
        btn_viewmore.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int position = (int) v.getTag();
                clickedItemPosition = position;

                Intent intent = new Intent(context, ViewMoreActivity.class);
                ((Activity) context).startActivityForResult(intent, 2);
            }
        });

        return v;
    }


}
