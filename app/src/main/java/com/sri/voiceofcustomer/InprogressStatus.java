package com.sri.voiceofcustomer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sri.voiceofcustomer.adapter.DataAdapter;
import com.sri.voiceofcustomer.database.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InprogressStatus extends AppCompatActivity {
    DatabaseReference mDatabase1;
    Map<String,String> usermap=new HashMap<String,String>();
    String client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress_status);
        initViews();
    }
    private void initViews(){
        mDatabase1 = FirebaseDatabase.getInstance().getReference("users");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
         String client=getIntent().getExtras().getString("client");
        Log.d("client",client);

        /**************************************************************************************/
        mDatabase1 = FirebaseDatabase.getInstance().getReference("users/"+client);

        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                Iterable<DataSnapshot> childIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = childIterator.iterator();
                while(iterator.hasNext())
                {
                    User user =iterator.next().getValue(User.class);

                    String name=user.firstName.concat(user.lastName);

                    String mailid=user.email;

                    usermap.put(name,mailid);
                }
                if(usermap.size()>0)
                {
                    RecyclerView.Adapter adapter = new DataAdapter(usermap);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error in company list",databaseError.getMessage());
            }
        });

        /******************************************************************************************/
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                   // Toast.makeText(getApplicationContext(), countries.get(position), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}