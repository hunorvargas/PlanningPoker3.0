package com.example.planningpoker30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;



public class CreateActivity extends AppCompatActivity {

    EditText editTexteditSessionID, editTextQuestion,editTextQuestionDesc;
    Button creatSessionButton;
    long maxID=0;
    final ArrayList<String> sessionIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        init();

        creatSession();

        Log.d("create", "nem kell main");

    }

    private void creatSession() {
        creatSessionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString().trim();
                String sessionId = editTexteditSessionID.getText().toString().trim();
                String questionDescrpt=editTextQuestionDesc.getText().toString().trim();

                Log.d("create1", question + " " + sessionId);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                Log.d("create1", "nem kell Onclick");

                if(!question.isEmpty()){

                    Log.d("create1", "nem kell empty");

                    myRef.child("session").child(sessionId).child("Questions").child("Question").setValue(question);
                    myRef.child("session").child(sessionId).child("Questions").child("QuestionDesc").setValue(questionDescrpt);

                    Log.d("create1", "nem kell data added");
                    Toast.makeText(CreateActivity.this, "SessionCreated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateActivity.this, MainActivity.class ));
                }
                else {
                    Log.d("create1", "nem kell else");
                    Toast.makeText(CreateActivity.this, "Complete the Question field!", Toast.LENGTH_SHORT).show();

                }
            }


        });
    }


    private void init() {
        editTexteditSessionID = findViewById(R.id.editSessionID);
        creatSessionButton =  findViewById(R.id.btnC);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextQuestionDesc=findViewById(R.id.questionDescripEditText);
        getsessionids();
    }

    public long getMaxID() {
        return maxID;
    }

    public void setMaxID(long maxID) {
        this.maxID = maxID;
    }

    public void getsessionids(){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference  myRef = database.getReference();

        myRef.addChildEventListener((new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            //Get the node from the datasnapshot
            String myParentNode = dataSnapshot.getKey();
            for (DataSnapshot child: dataSnapshot.getChildren())
            {
                String key = child.getKey().toString();
                sessionIDs.add(key);
                Log.d("create", "creatID:"+ key);
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }));
}
}


