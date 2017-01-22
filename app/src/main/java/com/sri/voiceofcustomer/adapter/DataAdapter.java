package com.sri.voiceofcustomer.adapter;

/**
 * Created by kavinraj on 1/22/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sri.voiceofcustomer.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Map<String,String> usermap=new HashMap<String,String>();

    public DataAdapter(Map<String,String> usermap) {
        this.usermap = usermap;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        for(Map.Entry<String, String> entry : usermap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            viewHolder.user_name.setText(key);
            viewHolder.user_email.setText(value);

            // do what you have to do here
            // In your case, an other loop.
        }


       // viewHolder.user_email.setText(usermap.get(i));

    }

    @Override
    public int getItemCount() {
        return usermap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView user_name,user_email;
        public ViewHolder(View view) {
            super(view);
            user_name = (TextView)view.findViewById(R.id.user_name);
            user_email = (TextView)view.findViewById(R.id.user_email);
            
        }
    }
}
