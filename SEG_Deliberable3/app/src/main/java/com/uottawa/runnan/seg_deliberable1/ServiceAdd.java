package com.uottawa.runnan.seg_deliberable1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.lang.Double;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.runnan.seg_deliberable1.Model.ServiceListAdapter;
import com.uottawa.runnan.seg_deliberable1.Model.Service;
import com.uottawa.runnan.seg_deliberable1.Model.User;


import java.util.ArrayList;

public class ServiceAdd extends AppCompatActivity {

    ListView ServiceListView;
    Button AddService;
    Button EditService;
    Button DeleteService;
    EditText ServiceName;
    EditText HourlyRate;

    ArrayList<Service> list;
    ArrayAdapter<Service> adapter;


    //Firebase
    FirebaseDatabase database;
    DatabaseReference services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_add);

        Bundle bundle = getIntent().getExtras();
        String username = getIntent().getStringExtra("username");
        String roletype = bundle.getString("roletype");

        //Firebase
        database = FirebaseDatabase.getInstance();
        services = database.getReference("Services");


        ServiceListView = (ListView)findViewById(R.id.lv_services);
        AddService = (Button)findViewById(R.id.btn_add_service);
        EditService = (Button)findViewById(R.id.btn_edit_service);
        DeleteService = (Button)findViewById(R.id.btn_delete_service);
        ServiceName = (EditText)findViewById(R.id.service_name);
        HourlyRate = (EditText)findViewById(R.id.hourly_rate);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, list);
        final ServiceListAdapter serviceAdapter = new ServiceListAdapter(this, R.layout.service_adapter_layout, list);

        ServiceListView.setAdapter(adapter);
        services.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count=0;
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String serviceName = data.child("nameOfService").getValue().toString();
                    double serviceRate = Double.parseDouble(data.child("hourlyRate").getValue().toString());
                    Service temp = new Service(serviceName, serviceRate);
                    list.add(temp);
                    adapter.notifyDataSetChanged();
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //add custom code here

            }
        });


        AddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = ServiceName.getText().toString();
                String serviceRate = HourlyRate.getText().toString();
                String namePattern = "[a-zA-Z\\\\s,]+";
                String ratePattern = "^-?\\d*\\.\\d+$" + "|" + "^-?\\d+$";

                if(serviceName.matches(namePattern) && serviceRate.matches(ratePattern) ){

                    services.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String name = ServiceName.getText().toString();
                            double rate = Double.parseDouble(HourlyRate.getText().toString());

                            if(dataSnapshot.child(name).exists() ){

                                Toast.makeText(ServiceAdd.this,"Service already exists", Toast.LENGTH_SHORT).show();
                            }else{
                                final Service tempS = new Service(name, rate);
                                services.child(tempS.getNameOfService()).setValue(tempS);
                                list.add(tempS);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ServiceAdd.this,"Service added successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //add custom code here

                        }
                    });

                }else if(!serviceName.matches(namePattern)){

                    Toast.makeText(ServiceAdd.this,"Invalid service name", Toast.LENGTH_SHORT).show();

                }else if(!serviceRate.matches(ratePattern)){

                    Toast.makeText(ServiceAdd.this,"Invalid rate input", Toast.LENGTH_SHORT).show();

                }
            }
        });

        EditService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = ServiceName.getText().toString();
                String serviceRate = HourlyRate.getText().toString();
                String namePattern = "[a-zA-Z\\\\s,]+";
                String ratePattern = "^-?\\d*\\.\\d+$" + "|" + "^-?\\d+$";

                if(serviceName.matches(namePattern) && serviceRate.matches(ratePattern) ){

                    services.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = ServiceName.getText().toString();
                            double rate = Double.parseDouble(HourlyRate.getText().toString());

                            if(dataSnapshot.child(name).exists() ){
                                for(int x=0; x<list.size(); x++){
                                    if(list.get(x).getNameOfService().equals(name)){
                                        list.get(x).setHourlyRate(rate);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ServiceAdd.this,"Successful edit", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(ServiceAdd.this,"Service doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //Add custom code here

                        }
                    });


                }else if(!serviceName.matches(namePattern)){
                    Toast.makeText(ServiceAdd.this,"Invalid service name", Toast.LENGTH_SHORT).show();

                }else if(!serviceRate.matches(ratePattern)){

                    Toast.makeText(ServiceAdd.this,"Invalid rate input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = ServiceName.getText().toString();
                String serviceRate = HourlyRate.getText().toString();
                String namePattern = "[a-zA-Z\\\\s,]+";
                String ratePattern = "^-?\\d*\\.\\d+$" + "|" + "^-?\\d+$";

                if(serviceName.matches(namePattern) && serviceRate.matches(ratePattern) ){

                    services.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = ServiceName.getText().toString();
                            double rate = Double.parseDouble(HourlyRate.getText().toString());

                            if(dataSnapshot.child(name).exists() ){
                                for(int x=0; x<list.size(); x++){
                                    if(list.get(x).getNameOfService().equals(name)){
                                        list.remove(x);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ServiceAdd.this,"Successful deletion", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(ServiceAdd.this,"Service doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //Add custom code here

                        }
                    });


                }else if(!serviceName.matches(namePattern)){
                    Toast.makeText(ServiceAdd.this,"Invalid service name", Toast.LENGTH_SHORT).show();

                }else if(!serviceRate.matches(ratePattern)){

                    Toast.makeText(ServiceAdd.this,"Invalid rate input", Toast.LENGTH_SHORT).show();
                }

            }
        });








    }







}
