package com.example.planningpoker30;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseDatabaseHelper {

    private DatabaseReference database;




    public FirebaseDatabaseHelper(String sessionId) {
        database = FirebaseDatabase.getInstance().getReference("SESSION");


    }


}