package com.mayur.projectpersonalitydevelopment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPage extends AppCompatActivity {

    private EditText etFirstName,etLastName,etEmail,etPassword,etConfirmPassword;

    private Button btnRegister;

    private CheckBox chkAgree;

    private FirebaseDatabase db;

    private DatabaseReference node;

    private String[] stream={"Science","Commerce","Arts"};

    private String str="";

    int i=3;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        //initializing views
        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        chkAgree=findViewById(R.id.chkAgree);

        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //authenticate credentials
                int flag1=authUsername(etPassword.getText().toString(),etConfirmPassword.getText().toString());
                int flag2=EmptyFieldValidation();

                //data to store in firebase
                UserData ud=new UserData();
                ud.setFirst_name(etFirstName.getText().toString());
                ud.setLast_name(etLastName.getText().toString());
                ud.setPassword(etPassword.getText().toString());
                //check for internet connectivity
                NetworkUtil nu=new NetworkUtil();
                String check=nu.getConnectivityStatusString(getApplicationContext());
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if(activeNetwork != null){
                    //do nothing
                }
                else {
                    Toast.makeText(getApplicationContext(),"check your internet connection",Toast.LENGTH_LONG).show();
                }
                //sharing data to another activity
                sharedPreferences=getApplicationContext().getSharedPreferences("Credentials",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("user_password",etPassword.getText().toString());
                editor.putString("user_firstname",etFirstName.getText().toString());
                editor.apply();

                ///if authentication is successfull add data to firebase
                if(flag1 == 1 && flag2==1) {
                    //firebase data storing
                    db=FirebaseDatabase.getInstance();
                    DatabaseReference ne=db.getReference("User");
                    node=db.getReference("User/SubUser");
                    DatabaseReference newRef=node.child(etPassword.getText().toString());
                    //checking if data exists in firebase database
                    ne.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot data: snapshot.getChildren()){
                                if (data.child(etPassword.getText().toString()).exists()) {
                                    etPassword.setError("password is already taken");
                                }
                                else {
                                    if(activeNetwork != null){

                                        //Add in Database
                                        newRef.setValue(ud);

                                        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        Toast.makeText(getApplicationContext(), "data inserted", Toast.LENGTH_SHORT).show();
                                        startActivity(loginIntent);
                                        break;
                                    }
                                    else {
                                        //Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        //startActivity(loginIntent);
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // if the data is not added or it is cancelled then
                            // we are displaying a failure toast message.

                            Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    //Authenticate all users
    public int authUsername(String password,String confirm_password){
        int flag1=1;
        //regular expression for email
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        //validating firstname
        if(4>etFirstName.length() && 20<etFirstName.length()){
            etFirstName.setError("first name must be 4-20 characters long");
            flag1=-1;
        }
        //validating lastname
        else if(4>etLastName.length() && 20<etLastName.length()){
            etLastName.setError("last name must be 4-20 characters long");
            flag1=-1;
        }
        else{
            //do nothing
        }
        //validating email address
        if(etEmail.getText().toString().matches(regex)){
            //do nothing
        }
        else{
            etEmail.setError("Email Address is not valid");
            flag1=-1;
        }

        //validating password
        if(4>etPassword.length() && 20<etPassword.length()){
            etPassword.setError("password must be 4-20 characters long");
            flag1=-1;
        }
        //validating confirm password
        if(4>etConfirmPassword.length() && 20<etConfirmPassword.length()){
            etConfirmPassword.setError("password must be 4-20 characters long");
            flag1=-1;
        }
        //matching password with confirm password
        if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
            //do nothing
        }
        else{
            Toast.makeText(getApplicationContext(),"Password not matched",Toast.LENGTH_LONG).show();
            flag1=-1;
        }
        if(chkAgree.isChecked()){
            //do nothing
        }
        else{
            Toast.makeText(getApplicationContext(),"Please agree our terms and conditions",Toast.LENGTH_SHORT).show();
            flag1=-1;
        }
        return flag1;
    }
    public int EmptyFieldValidation(){
        int flag2=1;
        //empty field validation
        if(etFirstName.getText().toString().equals("")){
            etFirstName.setError("firstname cannot be empty");
            flag2=-1;
        }
        if(etLastName.getText().toString().equals("")){
            etLastName.setError("lastname cannot be empty");
            flag2=-1;
        }
        if(etEmail.getText().toString().equals("")){
            etEmail.setError("email cannot be empty");
            flag2=-1;
        }
        if(etPassword.getText().toString().equals("")){
            etPassword.setError("password cannot be empty");
            flag2=-1;
        }
        if(etConfirmPassword.getText().toString().equals("")){
            etConfirmPassword.setError("password cannot be empty");
            flag2=-1;
        }
        return flag2;
    }
}