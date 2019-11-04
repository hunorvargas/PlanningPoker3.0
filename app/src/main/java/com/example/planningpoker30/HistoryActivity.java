package com.example.planningpoker30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HistoryActivity extends AppCompatActivity {

    private User newUser;
    private Question question;
    private String sessionid="";
    private Session session;
    final ArrayList<User> users = new ArrayList<>();
    final ArrayList<Question> questions = new ArrayList<>();
    final ArrayList<String> sessionIDs = new ArrayList<>();
    final ArrayList<Session> sessions= new ArrayList<>();
    final Handler handler = new Handler();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView sessionReycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();


        getAllData();

        //getSessionData();



        SessionsAdapter adapter=new SessionsAdapter(sessions);

        sessionReycler.setAdapter(adapter);
        sessionReycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init() {
        Log.d("create", "Initstart");
        newUser=new User();
        question=new Question();
        session=new Session();
        sessionReycler=findViewById(R.id.rvSessions);
        Log.d("create", "initFinish");
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

        DatabaseReference myRef = database.getReference("session").
                child(newUser.getSessionId()).child("Questions").child("QuestionDesc");


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

        Log.d("create", "getAllDataStart");
        getsessionids();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int i = 0;
                Log.d("create", "getSessionID size: "+sessionIDs.size());
                while (i < sessionIDs.size()) {
                    Log.d("create", "While SessionIDs: ");
                    sessionIDs.get(i);
                    readfirebase(sessionIDs.get(i));
                    sessions.add(session);
                    i++;
                }

            }
        }, 2000);

        Log.d("create", "getSessionIDS finish");

    }

    private void readfirebase(String sessid) {
        Log.d("create", "readfirebasestart: ");
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
        Log.d("create", "data: " + session.getSessionID()+ " " + session.getQuestion()+ " "+session.getUsers());
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
