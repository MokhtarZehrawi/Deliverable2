package com.uottawa.runnan.seg_deliberable1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.runnan.seg_deliberable1.Model.Availability;
import com.uottawa.runnan.seg_deliberable1.Model.Product;
import com.uottawa.runnan.seg_deliberable1.Model.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {
    TextView service;
    TextView availability;
    TextView myname;
    ListView servicelist;
    ListView availabilitylist;
    List<Product> services;
    List<Availability> av;

    DatabaseReference dataavailability;
    DatabaseReference datamyservices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        service = (TextView)findViewById(R.id.myservices);
        myname = (TextView)findViewById(R.id.tvmyname);
        availability = (TextView)findViewById(R.id.myavailability);
        servicelist = (ListView)findViewById(R.id.servicelist);
        availabilitylist = (ListView)findViewById(R.id.availabilitylist);

        dataavailability = FirebaseDatabase.getInstance().getReference("availability");
        datamyservices = FirebaseDatabase.getInstance().getReference("myServices");
        services = new ArrayList<>();
        av = new ArrayList<>();

        servicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product myproduct = services.get(position);

            }
        });
    }

    @Override
    protected void  onStart() {
        super.onStart();
        datamyservices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    services.add(product);
                }
                MyServiceList adapter = new MyServiceList(MyProfile.this,services);
                servicelist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
