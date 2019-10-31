package com.example.planningpoker30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    User newUser;
    EditText editUsername,editSessID;
    Button btnJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
        joinSession();
    }

    private void joinSession() {
        isCompletdata();
        btnJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this,RoomActivity.class);
                intent.putExtra("Username",editUsername.getText().toString().trim());
                intent.putExtra("SessionId",editSessID.getText().toString().trim());
                startActivity(intent);

            }
        });
    }

    private void init() {
        editUsername = findViewById(R.id.editUsername);
        editSessID =  findViewById(R.id.editSessID);
        btnJoin = findViewById(R.id.btnJoin);

    }

   private void isCompletdata(){
       if (editUsername.getText().toString().isEmpty() && editSessID.getText().toString().isEmpty()){

           Toast.makeText(JoinActivity.this,"Missing UserName or SessionID!", Toast.LENGTH_SHORT).show();
       }
   }


}
