package com.example.planningpoker30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class JoinActivity extends AppCompatActivity {
    EditText editUsername,editSessID;
    Button btnJoin;
    private String sessionid="";
    private String usernamesesion="";
    public int counter;
    final ArrayList<String> sessionIDs = new ArrayList<>();
    final ArrayList<String> Users = new ArrayList<>();
    ProgressBar p;


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
                setSessionid(editSessID.getText().toString().trim());
                setUsernamesesion(editUsername.getText().toString().trim());

                MyTask myTask= new MyTask();
                //start asynctask
                myTask.execute(1000);

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
        getsessionids();

    }

   private boolean isCompletdata(){
       if (editUsername.getText().toString().isEmpty() && editSessID.getText().toString().isEmpty()){

           Toast.makeText(JoinActivity.this,"Missing UserName or SessionID!", Toast.LENGTH_SHORT).show();


       }
       else {
           if(isagoodSessionID()) {
               Log.d("create", "kell sessionID jo");
               if(isagoodusername()){
                   Log.d("create", "kell username jo");
                   return true;
               }
           }
        return false;
       }
       Log.d("create", "kell nem komplett isagoodsession");
       return true;
   }

    private boolean isagoodusername() {
        Log.d("create", "kell isagoodusername");

        int i = 0;
        while (i < Users.size()) {
            Log.d("create", "Whiile ID"+Users.get(i));
            if(Users.get(i).equals(getUsernamesesion())){
                Log.d("create", "kell username egyenlo");
                return true;
            }
            i++;
        }
        Toast.makeText(JoinActivity.this,"This UserName is busy!", Toast.LENGTH_LONG).show();
        Log.d("create", "kell foglalt username");
        return false;
    }

    private boolean isagoodSessionID() {

        Log.d("create", "kell isagoodsession");

        int i = 0;
        while (i < sessionIDs.size()) {
            Log.d("create", "Whiile ID"+sessionIDs.get(i));
            if(sessionIDs.get(i).equals(getSessionid())){
                Log.d("create", "kell session egyenlo");
                return true;
            }
            i++;
        }
        Toast.makeText(JoinActivity.this,"This Session is not available!", Toast.LENGTH_LONG).show();
        Log.d("create", "kell session nincs");
        return false;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUsernamesesion() {
        return usernamesesion;
    }

    public void setUsernamesesion(String usernamesesion) {
        this.usernamesesion = usernamesesion;
    }

    public void getsessionids(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference  myRef = database.getReference();
        final CountDownLatch done = new CountDownLatch(1);
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
    }
    public void getsessionUsernames(){
        Log.d("create", "Users");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Log.d("create", "Users ID:"+getSessionid());
        DatabaseReference  myRef = database.getReference().child("session").child(getSessionid()).child("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey()!=null) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String classnames = datas.getKey();
                        Users.add(classnames);
                        Log.d("create", "Users " + classnames);
                    }
                    Log.d("create", "Null");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            showDialog("Downloaded " + result + " bytes");
        }
    }

}
