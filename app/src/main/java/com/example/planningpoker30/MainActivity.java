package com.example.planningpoker30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Intent intentCreate, intentJoin, intentHistory;
    private Button btnCreate, btnJoin, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentCreate = new Intent(MainActivity.this, CreateActivity.class);
        intentJoin = new Intent(MainActivity.this, JoinActivity.class);
        intentHistory=new Intent(MainActivity.this, HistoryActivity.class);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(this);

        btnHistory=(Button) findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == btnCreate){

            startActivity(intentCreate);

        }

        if (v == btnJoin){

            startActivity(intentJoin);

        }

        if (v == btnHistory){

            startActivity(intentHistory);

        }

    }
}