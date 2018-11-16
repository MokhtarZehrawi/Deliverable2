package com.uottawa.runnan.seg_deliberable1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    EditText view;
    EditText view2;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extras = getIntent().getExtras();
        String username = getIntent().getStringExtra("username");
        String role = getIntent().getStringExtra("roletype");

        view = (EditText) findViewById(R.id.welcome);
        view2 = (EditText)findViewById(R.id.etrole);
        next = (Button) findViewById(R.id.btn_Next);

        view.setText("Welcome " + username + "!");
        view2.setText("You are logged as " + role);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().getStringExtra("roletype").equals("Administrator")){

                    Intent passdata = new Intent(getApplicationContext(),ServiceAdd.class);
                    passdata.putExtra("username", getIntent().getStringExtra("username"));
                    startActivity(passdata);

                }else if(getIntent().getStringExtra("roletype").equals("ServiceProvider")){

                    Intent passdata = new Intent(getApplicationContext(),ProfileInformation.class);
                    passdata.putExtra("username", getIntent().getStringExtra("username"));
                    startActivity(passdata);

                }else{
                    Toast.makeText(Home.this,"Will be implemented later", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
