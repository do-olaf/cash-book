package com.example.cash;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.example.cash.DBContract.table01.CATEGORY;
import static com.example.cash.DBContract.table01.CHECKINOUT;
import static com.example.cash.DBContract.table01.DATE;
import static com.example.cash.DBContract.table01.MEMO;
import static com.example.cash.DBContract.table01.MONEY;
import static com.example.cash.DBContract.table01.PAYMENT;

public class MyCursorAdapter extends CursorAdapter {
    @SuppressWarnings("deprecation")
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //list_item에 있는 컴포넌트들 객체로 받아오기
        TextView tvDate = (TextView) view.findViewById( R.id.date );
        TextView tvCheck = (TextView) view.findViewById( R.id.checkinout );
        TextView tvMoney = (TextView) view.findViewById( R.id.money );
        TextView tvPayment = (TextView) view.findViewById( R.id.payment );
        TextView tvCategory = (TextView) view.findViewById( R.id.category );
        TextView tvMemo = (TextView) view.findViewById( R.id.memo );
        //DB에 있는 데이터를 cursor를 움직이며 받아오기
        String date = cursor.getString( cursor.getColumnIndex( DATE ) );
        String checkinout = cursor.getString( cursor.getColumnIndex( CHECKINOUT ) );
        String money = cursor.getString( cursor.getColumnIndex( MONEY ) );
        String payment = cursor.getString( cursor.getColumnIndex( PAYMENT ) );
        String category = cursor.getString( cursor.getColumnIndex( CATEGORY ) );
        String memo = cursor.getString( cursor.getColumnIndex( MEMO ) );
        Log.d("스트링 확인", date + ", " + checkinout); //: 아마 필요 없는 코드. 다시 확인해보고 지울게요

        //컴포넌트에 텍스트 입력
        tvDate.setText( date );
        tvCheck.setText( checkinout );
        tvMoney.setText(money);
        tvPayment.setText(payment);
        tvCategory.setText(category);
        tvMemo.setText(memo);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //list_item에 만든 레이아웃을 확장하여 activity_main에 있는 listView에 넣는 개념
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list_item, parent, false );
        return v;
    }

}
