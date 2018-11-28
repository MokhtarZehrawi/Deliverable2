package com.uottawa.runnan.seg_deliberable1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.runnan.seg_deliberable1.Model.User;

public class HomeOwnerMain extends AppCompatActivity {

    Button Search;
    Button Rate;
    Button Book;
    EditText Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_main);

        final String username = getIntent().getStringExtra("username");

        Search = (Button)findViewById(R.id.btn_search);
        Rate = (Button)findViewById(R.id.btn_rate);
        Book = (Button)findViewById(R.id.btn_book);
        Name = (EditText) findViewById(R.id.et_user_name);
        Name.setText(username);


        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passdata = new Intent(getApplicationContext(),RateServiceProvider.class);
                startActivity(passdata);
            }
        });



    }
}
