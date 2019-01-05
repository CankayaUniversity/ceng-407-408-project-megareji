package com.example.arzuk.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ShowPlaceEnd extends AppCompatActivity {
    TextView textviewSceneShowPlaceEnd;

    TextView textviewLatitudeShowPlaceEnd;
    TextView textviewLongitudeShowPlaceEnd;
    ImageView imageViewShowPlaceEnd;
    String ids;
    HashMap<String,String> hashMap;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place_end);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();



        textviewSceneShowPlaceEnd=findViewById(R.id.textviewSceneShowPlaceEnd);
        textviewLatitudeShowPlaceEnd=findViewById(R.id.textviewLatitudeShowPlaceEnd);
        textviewLongitudeShowPlaceEnd=findViewById(R.id.textviewLongitudeShowPlaceEnd);
        imageViewShowPlaceEnd=findViewById(R.id.imageViewShowPlaceEnd);

        Intent intent =getIntent();
        ids=intent.getStringExtra("id");
        getDataFromFirebase();

    }

    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("places");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                         hashMap=  (HashMap<String,String>) ds.getValue();

                        textviewSceneShowPlaceEnd.setText("Scene: "+hashMap.get("Scene").toString());
                        textviewLatitudeShowPlaceEnd.setText("Latitude: "+hashMap.get("Latitude").toString());
                        textviewLongitudeShowPlaceEnd.setText("Longitude: "+hashMap.get("Longitude"));
                        Picasso.get().load(hashMap.get("Url")).into(imageViewShowPlaceEnd);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
