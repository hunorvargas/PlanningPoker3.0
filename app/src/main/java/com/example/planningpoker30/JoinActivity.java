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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {
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

        btnJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this,RoomActivity.class);
                intent.putExtra("Username",editUsername.getText().toString().trim());
                intent.putExtra("SessionId",editSessID.getText().toString().trim());
                Log.d("create", "kell join:"+editSessID.getText().toString().trim());
                if(isCompletdata())
                startActivity(intent);
                else {

                }

            }
        });
    }

    private void init() {
        editUsername = findViewById(R.id.editUsername);
        editSessID =  findViewById(R.id.editSessID);
        btnJoin = findViewById(R.id.btnJoin);

    }

   private boolean isCompletdata(){
       if (editUsername.getText().toString().isEmpty() && editSessID.getText().toString().isEmpty()){

           Toast.makeText(JoinActivity.this,"Missing UserName or SessionID!", Toast.LENGTH_SHORT).show();


       }
       else {
           if(isagoodSessionID()) {
               Log.d("create", "kell iscompletdata");
               return true;
           }
        return false;
       }
       Log.d("create", "kell nem komplett isagoodsession");
       return true;
   }

    private boolean isagoodSessionID() {
        Log.d("create", "kell isagoodsession");
        final ArrayList<String> sessionIDs = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference  myRef = database.getReference("session");

        myRef.addChildEventListener((new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Get the node from the datasnapshot
                String myParentNode = dataSnapshot.getKey();
                for (DataSnapshot child: dataSnapshot.getChildren())
                {
                    String key = child.getKey().toString();
                    sessionIDs.add(key);
                    Log.d("create", "ID:"+ key);
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



        for (String id : sessionIDs) {
             if(id.equals(editSessID.getText().toString().trim())){
                 Log.d("create", "kell session egyenlo");
                 return true;
             }
        }
        Toast.makeText(JoinActivity.this,"This Session is not available!", Toast.LENGTH_LONG).show();
        Log.d("create", "kell session nincs");
        return false;
    }
}
