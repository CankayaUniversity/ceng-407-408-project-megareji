package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPlan extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> date;
    ArrayList<String> email;
    ArrayList<String>ids;
    ListView listViewShowPlan;
    PlanPostClass customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);
        listViewShowPlan=findViewById(R.id.listViewShowPlan);
        date=new ArrayList<String>();
        email=new ArrayList<String>();
        ids=new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        customAdapter=new PlanPostClass(email,ids,this);
        listViewShowPlan.setAdapter(customAdapter);
        getDataFromFirebase();
        listViewShowPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),ShowPlanEnd.class);
                intent.putExtra("id",ids.get(position).toString());


                startActivity(intent);
            }
        });


    }

    public void getDataFromFirebase() {
        DatabaseReference newReference=firebaseDatabase.getReference("plans");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    email.add(hashMap.get("Email")+" "+hashMap.get("date"));
                    ids.add(ds.getKey());
                    customAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;


    }
}