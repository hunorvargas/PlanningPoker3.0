package com.example.planningpoker30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intentBack;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        intentBack = new Intent(StatisticsActivity.this, MainActivity.class);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == btnBack){

            startActivity(intentBack);

        }

    }
}
