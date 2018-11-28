package com.uottawa.runnan.seg_deliberable1;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.uottawa.runnan.seg_deliberable1.Model.ServiceProvider;

public class Home extends AppCompatActivity {
    EditText view;
    EditText view2;
    Button next;
    public static final String EXTRA_TEXT = "spname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Bundle extras = getIntent().getExtras();
        final String username = getIntent().getStringExtra("username");
        String role = getIntent().getStringExtra("roletype");

        view = (EditText) findViewById(R.id.welcome);
        view2 = (EditText)findViewById(R.id.etrole);
        view.setText(username);
        view2.setText("You are logged as " + role);
        next =(Button)findViewById(R.id.btnnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),username, Toast.LENGTH_SHORT).show();
                String role2 = getIntent().getStringExtra("roletype");
                if(role2.equals("Administrator")){
                    Intent intent = new Intent(getApplicationContext(),ServiceList.class);
                    startActivity(intent);
                }
                else if(role2.equals("ServiceProvider")){
                    openSPProfile();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),HomeOwnerMain.class);
                    intent.putExtra("username", username);
                    startActivity(intent);


                }
            }
        });
    }

    public void openSPProfile(){
        String spname = view.getText().toString();
        Intent intent = new Intent(getApplicationContext(),SP_profile.class);
        intent.putExtra(EXTRA_TEXT,spname);
        startActivity(intent);
    }
}
