package com.example.cash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //payment 스피너에 데이터 출력
        ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerPayment = (Spinner)findViewById(R.id.spinner_payment);
        spinnerPayment.setPrompt("결제 수단을 선택하세요");
        spinnerPayment.setAdapter(adapterPayment);
    }

}
