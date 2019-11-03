package com.example.planningpoker30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private User newUser;
    private Question question;
    private String sessionid="";
    private Session session;
    final ArrayList<User> users = new ArrayList<>();
    final ArrayList<Question> questions = new ArrayList<>();
    final ArrayList<String> sessionIDs = new ArrayList<>();
    final ArrayList<Session> sessions= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
        getAllData();
        getSessionData();
        
    }

    private void init() {
        newUser=new User();
        question=new Question();
        session=new Session();
    }

    private void getSessionData() {
        getSessionQuestion();
        getSessionQuestionDesc();
        getSessionUsers();
        session.setUsers(users);
        session.setSessionID(sessionid);
        session.setQuestion(question);
    }

    private void getSessionQuestionDesc() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("session").child(newUser.getSessionId()).child("Questions").child("QuestionDesc");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                question.setQuestionDesc(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSessionUsers() {
        Log.d("create", "Users");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Log.d("create", "Users ID:"+getSessionid());
        DatabaseReference  myRef = database.getReference().child("session").child(getSessionid()).child("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("create", "UsersName Snap");
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String names=datas.getKey();
                    newUser.setUserName(names);
                    Log.d("create", "UserName: " + newUser.getUserName());
                    newUser.setUserVote(String.valueOf(datas.getValue()));
                    users.add(newUser);
                    Log.d("create", "UserVote: " + newUser.getUserVote());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSessionQuestion() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("session").child(newUser.getSessionId()).child("Questions").child("Question");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                question.setQuestion(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllData() {
        getsessionids();
        int i = 0;
        while (i < sessionIDs.size()) {
            Log.d("create", "While SessionIDs: ");
            sessionIDs.get(i);
            sessions.add(session);
            readfirebase(sessionIDs.get(i));
            }
            i++;
    }

    private void readfirebase(String sessid) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference  myRef = database.getReference(sessid);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             String key = dataSnapshot.getKey();
                Log.i("create","readfirebase "+dataSnapshot.getKey());
                if (key.equals("Question"))
                {
                    question.setQuestion(dataSnapshot.getValue().toString());
                    Log.i("create","Question: "+dataSnapshot.getValue());
                }

                if (key.equals("QuestionDesc"))
                {
                    question.setQuestionDesc(dataSnapshot.getValue().toString());
                    Log.i("create","QuestionDesc: "+dataSnapshot.getValue());
                }

                if (key.equals("Users"))
                {
                    for (DataSnapshot child: dataSnapshot.getChildren())
                {
                    newUser.setUserName(child.getKey());
                    Log.i("create","User: "+ newUser.getUserName());
                    newUser.setUserVote(String.valueOf(child.getValue()));
                    Log.i("create","UserVote: "+child.child("questionDescription").getValue().toString());
                }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                Log.i("create","readfirebase "+dataSnapshot.getKey());
                if (key.equals("Question"))
                {
                    question.setQuestion(dataSnapshot.getValue().toString());
                    Log.i("create","Question: "+dataSnapshot.getValue());
                }

                if (key.equals("QuestionDesc"))
                {
                    question.setQuestionDesc(dataSnapshot.getValue().toString());
                    Log.i("create","QuestionDesc: "+dataSnapshot.getValue());
                }

                if (key.equals("Users"))
                {
                    for (DataSnapshot child: dataSnapshot.getChildren())
                    {
                        newUser.setUserName(child.getKey());
                        Log.i("create","User: "+ newUser.getUserName());
                        newUser.setUserVote(String.valueOf(child.getValue()));
                        Log.i("create","UserVote: "+child.child("questionDescription").getValue().toString());
                        users.add(newUser);
                    }

                }
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
        });

        session.setSessionID(sessid);
        session.setQuestion(question);
        session.setUsers(users);
    }

    private void getsessionids(){
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
    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
