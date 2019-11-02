package com.example.planningpoker30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;

public class CreateActivity extends AppCompatActivity {

    EditText editTextQuestionId, editTextQuestion;
    Button creatSessionButton;
    long maxID=0;

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
                String sessionId = editTextQuestionId.getText().toString().trim();
                Log.d("create1", question + " " + sessionId);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                Log.d("create1", "nem kell Onclick");

                if(!question.isEmpty()){

                    Log.d("create1", "nem kell empty");

                    myRef.child("session").child(sessionId).child("Qestion").setValue(question);


                    Log.d("create1", "nem kell data added");
                    //startActivity(new Intent(CreateActivity.this, RoomActivity.class ));
                }
                else Log.d("create1", "nem kell else");

            }


        });
    }


    private void init() {
        editTextQuestionId = findViewById(R.id.editQuestionId);
        creatSessionButton =  findViewById(R.id.btnC);
        editTextQuestion = findViewById(R.id.editTextQuestion);
    }

    public long getMaxID() {
        return maxID;
    }

    public void setMaxID(long maxID) {
        this.maxID = maxID;
    }
}


