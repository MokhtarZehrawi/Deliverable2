package com.uottawa.runnan.seg_deliberable1;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
import com.uottawa.runnan.seg_deliberable1.Model.Service;

public class ServiceListAdapter extends ArrayAdapter<Service> {

    private Context mContext;
    private int mResource;

    public ServiceListAdapter(Context context, int resource, ArrayList<Service> list ){
        super(context, resource, list);
        mContext=context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getNameOfService();
        double rate = getItem(position).getHourlyRate();

        Service service = new Service(name, rate);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.tv_service_name);
        TextView tvRate = (TextView)convertView.findViewById(R.id.tv_service_rate);
        tvName.setText(name);
        tvRate.setText(Double.toString(rate));

        return convertView;

    }


}
