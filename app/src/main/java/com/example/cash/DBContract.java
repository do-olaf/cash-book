package com.example.cash;

import android.provider.BaseColumns;

// 계약 클래스 : 프로그램 개발 과정에서 참조되는 여러 상수들을 정의한 클래스

// 데이터베이스의 계약 클래스(Contract Class) :
// 테이블 이름, 열(Column) 이름, 기능 별 SQL 문장들에 대한 상수 정의를 포함한 클래스를 의미

// 계약 클래스(Contract Class)는 데이터베이스를 사용함에 있어
// 개발자가 사용해야 할 여러 정보를 상수로 정의해둔 것

// 계약 클래스(Contract Class)를 사용함으로써 얻을 수 있는 장점은
// 데이터베이스와 관련된 정보를 한 곳에서 관리할 수 있다는 것과
// 그로 인해 데이터베이스 구조 또는 이름이 변경될 때의 수정 작업을 최소화할 수 있다는 것
public final class DBContract {
    //table01
    public static final class table01 implements BaseColumns{
        public static final String DATE = "date";
        public static final String CHECKINOUT = "checkinout";
        public static final String MONEY = "money";
        public static final String PAYMENT = "payment";
        public static final String CATEGORY = "category";
        public static final String MEMO = "memo";
        public static final String _TABLENAME = "table01";
        public static final String _CREATE =
                "create table if not exists " + _TABLENAME + "("
                        + _ID + " integer primary key autoincrement, "
                        + CHECKINOUT + " text not null, "
                        + MONEY + " integer not null, "
                        + PAYMENT + " text not null, "
                        + CATEGORY + " text not null, "
                        + MEMO + " text);";
        public static final String _INSERT =
                "insert or replace into " + _TABLENAME + "("
                        + DATE + ", " + CHECKINOUT + ", " + MONEY + ", "
                        + PAYMENT + ", " + CATEGORY + ", " + MEMO + ") VALUES ";
        public static final String _SELECT =
                "select * from " + _TABLENAME;
    }
}
