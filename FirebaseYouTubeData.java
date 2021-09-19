package com.mayur.projectpersonalitydevelopment;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseYouTubeData {

    private FirebaseDatabase fd;

    private DatabaseReference node;

    private Context context;


    public FirebaseYouTubeData(Context context){
        this.context=context;
        fd= FirebaseDatabase.getInstance();
        node= fd.getReference("User/SubUser");
    }

    public void getData(){
        //Get youtube key from firebase and store in GetFirebaseData mpdel class
        fd= FirebaseDatabase.getInstance();
        node= fd.getReference("VideoKey");

        //checking if data exists in firebase database
        node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //first data of firebase VideoKey
                int i=1;

                for (DataSnapshot data : snapshot.getChildren()) {
                    SharedPreferences sharedPreferences=context.getSharedPreferences("MySharedPrefernces",0);
                    // Creating an Editor object to edit(write to the file)
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    // Storing the key and its value as the data fetched from edittext
                    myEdit.putString(""+i, data.toString());

                    // Once the changes have been made,
                    // we need to commit to apply those changes made,
                    // otherwise, it will throw an error
                    myEdit.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setData(String password, int str){
        //checking if data exists in firebase database
        node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    if(data.getKey().equals(password)) {
                        node.child(password +"/score").setValue(str);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
