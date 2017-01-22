package com.sri.voiceofcustomer.survey.fragment.admin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sri.voiceofcustomer.R;
import com.sri.voiceofcustomer.database.models.AssignSurvey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by SRiGorthi on 21-01-2017.
 */

public class NewSurveyFragment  extends Fragment {

    Spinner companySpinner;
    Spinner surveyIdSpinner;
    DatabaseReference mDatabase1,mDatabase2;

    List<String> companyList = new ArrayList<String>();
    List<String> surveyList = new ArrayList<String>();
    View newSurveyView;
    Button assignButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference("users");

        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = childIterator.iterator();
                while(iterator.hasNext())
                {
                    String key = iterator.next().getKey();
                    companyList.add(key);
                }
                if(companyList.size()>0)
                {
                    if(getActivity()!=null) {
                        ArrayAdapter<String> companyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                                companyList);
                        companySpinner.setAdapter(companyAdapter);
                        companyAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error in company list",databaseError.getMessage());
            }
        });


        mDatabase2 = FirebaseDatabase.getInstance().getReference("Quetions");

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = childIterator.iterator();
                while(iterator.hasNext())
                {
                    String key = iterator.next().getKey();
                    surveyList.add(key);
                }
                if(surveyList.size()>0)
                {
                    if(getActivity()!=null)
                    {
                        ArrayAdapter<String> surveyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                                surveyList);
                        surveyIdSpinner.setAdapter(surveyAdapter);
                        surveyAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error in surveylist ",databaseError.getMessage());
            }
        });

        newSurveyView = inflater.inflate(R.layout.fragment_new_surveys,container,false);
        companySpinner =(Spinner) newSurveyView.findViewById(R.id.client_list);
        surveyIdSpinner =(Spinner) newSurveyView.findViewById(R.id.survey_list);
        assignButton = (Button) newSurveyView.findViewById(R.id.assign_to_client);
        assignButton.setOnClickListener(assignOnClickListener);

        return newSurveyView;
    }

    private View.OnClickListener assignOnClickListener = new View.OnClickListener() {
        String companySelected = null;
        String surveySelected = null;
        @Override
        public void onClick(View view) {
            Spinner companySpinner = (Spinner) newSurveyView.findViewById(R.id.client_list);
            Spinner surveySpinner = (Spinner) newSurveyView.findViewById(R.id.survey_list);

            if (companySpinner.getSelectedItem() == null) {
                Toast.makeText(getActivity(), "Client list couldn't be loaded Check  your internet connection and retry.", Toast.LENGTH_LONG).show();
            }
            else if(surveySpinner.getSelectedItem() == null){
                Toast.makeText(getActivity(), "Surveys couldn't be loaded Check  your internet connection and retry.", Toast.LENGTH_LONG).show();
            }else {
                ProgressBar progressBar=new ProgressBar(getActivity());
                progressBar=(ProgressBar)newSurveyView.findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                companySelected = companySpinner.getSelectedItem().toString();
                surveySelected = surveySpinner.getSelectedItem().toString();
                AssignSurvey assignSurvey = new AssignSurvey();
                assignSurvey.setSurveyId(surveySelected);

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("AssignedSurveys");
                mDatabase.child(companySelected).push().setValue(surveySelected);

                Toast.makeText(getActivity(),"Survey "+surveySelected+" assigned to client "+companySelected,Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }
    };
}
