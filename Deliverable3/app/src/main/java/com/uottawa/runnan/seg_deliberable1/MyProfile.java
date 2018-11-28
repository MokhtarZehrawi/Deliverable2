package com.uottawa.runnan.seg_deliberable1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ListView servicelist;
    ListView availabilitylist;
    List<Product> services;
    List<Availability> av;

    DatabaseReference dataavailability;
    DatabaseReference datamyservices;

    @Override
    protected void onStart(){
        super.onStart();
        datamyservices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Product p = postSnapshot.getValue(Product.class);
                    services.add(p);
                }
                ProductList adapter = new ProductList(MyProfile.this, services);
                servicelist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dataavailability.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                av.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Availability a = postSnapshot.getValue(Availability.class);
                    av.add(a);
                }
                AvailabilityList adapter = new AvailabilityList(MyProfile.this,av);
                availabilitylist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        service = (TextView)findViewById(R.id.myservices);
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
                final Product myproduct = services.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyProfile.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_delete, null);
                dialogBuilder.setView(dialogView);
                final Button d = (Button)findViewById(R.id.btndelete);

                final AlertDialog x = dialogBuilder.create();
                x.show();
                final Button delete = (Button) dialogView.findViewById(R.id.btndelete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datamyservices.child(myproduct.getProductName()).removeValue();
                        Toast.makeText(getApplicationContext(), "Service Deleted!", Toast.LENGTH_LONG).show();
                        x.dismiss();
                    }
                });
            }
        });
    }



}
