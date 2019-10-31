package com.example.planningpoker30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomActivity extends AppCompatActivity {


    TextView questiontextView;
    Button voteButton1,voteButton2,voteButton3,voteButton4,voteButton5;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init();
        addUserDatabase();
    }

    private void addUserDatabase() {

        Log.d("create", "adduser");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.d("create", "nem kell Onclick");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    long currentSessionID=dataSnapshot.getChildrenCount();
                    newUser.setSessionId(Long.toString(currentSessionID));
                    Log.d("create", "kell:"+newUser.getSessionId());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("create", "kell error");
                }
            });

           //  myRef.child(String.valueOf(++(newUser.getSessionId()))).setValue(sessionId);

            myRef.child("session").child(newUser.getSessionId()).child("Users").setValue(newUser.getUserName());


            Log.d("create", "nem kell data added");
            //startActivity(new Intent(CreateActivity.this, RoomActivity.class ));
    }



    private void init() {

        questiontextView=findViewById(R.id.textViewQuestion);
        voteButton1 = findViewById(R.id.buttonVote1);
        voteButton2 = findViewById(R.id.buttonVote2);
        voteButton3 = findViewById(R.id.buttonVote3);
        voteButton4 = findViewById(R.id.buttonVote4);
        voteButton5 = findViewById(R.id.buttonVote5);

        newUser=new User();
        Intent intent= getIntent();

        newUser.setUserName(intent.getStringExtra("Username"));
        newUser.setSessionId(intent.getStringExtra("SessionId"));
    }

}
