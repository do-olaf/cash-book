package com.example.cash;

import java.util.Calendar;

public class SetDate {
    static int nowYear, nowMonth, nowDay //현재 날짜
               , startYear, startMonth, startDay //시작 선택 날짜
               , endYear, endMonth, endDay; //끝 선택 날짜

    SetDate() {
        setNowDate();
        setStartDate();
        setEndDate();
    }

    public void setNowDate() {
        Calendar calendar = Calendar.getInstance();

        //현재 날짜 가져오기
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        nowMonth = calendar.get(Calendar.MONTH);
        nowYear = calendar.get(Calendar.YEAR);
    }

    public void setStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -14);

        //시작 선택 날짜 가져오기
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        startMonth = calendar.get(Calendar.MONTH);
        startYear = calendar.get(Calendar.YEAR);
    }

    public void setEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -14);

        //끝 선택 날짜 가져오기
        endDay = nowDay;
        endMonth = nowMonth;
        endYear = nowYear;
    }
}
