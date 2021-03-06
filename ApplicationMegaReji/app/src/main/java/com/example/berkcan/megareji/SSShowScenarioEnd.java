package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SSShowScenarioEnd extends AppCompatActivity {

  
    EditText editTextWriteSSScenarioEndStage;
    EditText editTextWriteSSScenarioEndMain;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    String ids;
    DatabaseReference myRef;
    HashMap<String,String> hashMap;
    String wayofSet;
    String wayofScene;

    //Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.sssave_scenario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When a menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sssave_scenario){
            upload();
            Intent intent2= new Intent(getApplicationContext(),SSShowActor.class);
            intent2.putExtra("id_1",wayofScene);
            intent2.putExtra("id_2",wayofSet);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssshow_scenario_end);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        editTextWriteSSScenarioEndStage=findViewById(R.id.editTextWriteSSScenarioEndStage);
        editTextWriteSSScenarioEndMain=findViewById(R.id.editTextWriteSSScenarioEndMain);

        //Get information from previous activity
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            ids =(String) b.get("id");
            wayofSet =(String) b.get("id_2");
            wayofScene=(String) b.get("id_1");
        }
        getDataFromFirebase();
    }

    public void upload(){

        myRef.child("scenes").child(wayofSet).child(wayofScene).child("scenario").setValue(ids);

    }
    //Get scenario information from Firebase
    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("scenarios");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                        hashMap=  (HashMap<String,String>) ds.getValue();
                        editTextWriteSSScenarioEndStage.setText(hashMap.get("stage").toString());
                        editTextWriteSSScenarioEndMain.setText(hashMap.get("mainscenario").toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}