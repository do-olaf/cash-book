package com.example.cash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CreateActivity extends AppCompatActivity {
    Button btn_modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        /**
         * 스피터 셋팅
         */
        //payment 스피너에 데이터 출력
        ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerPayment = (Spinner)findViewById(R.id.spinner_payment);
        spinnerPayment.setPrompt("결제 수단을 선택하세요");
        spinnerPayment.setAdapter(adapterPayment);

        //payment 스피너에 데이터 출력
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerCategory = (Spinner)findViewById(R.id.spinner_category);
        spinnerCategory.setPrompt("카테고리를 선택하세요");
        spinnerCategory.setAdapter(adapterCategory);

        /**
         * 버튼 리스너
         */
        btn_modify = (Button)findViewById(R.id.btn_create);
        btn_modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

}
