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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sri.voiceofcustomer.R;
import com.sri.voiceofcustomer.database.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 405116 on 1/18/2017.
 */

public class CreateSurveyFragment extends Fragment {

    private Button addCategoryButton;
    private Button addQuesionButton;
    RelativeLayout surveyRelativeLayout;
    Spinner categorySpinner;
    List<String> categoryList = new ArrayList<String>();
    View surveyView;
    ArrayAdapter<String> categoryAdapter;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mDatabase = FirebaseDatabase.getInstance().getReference("categories");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> genericList = new GenericTypeIndicator<List<String>>() {
                };
                categoryList = dataSnapshot.getValue(genericList);

                if (categoryList.size() > 0) {
                    categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            categoryList);
                    categorySpinner.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("category onload", databaseError.getDetails());

            }

        });


        surveyView = inflater.inflate(R.layout.create_survey, container, false);

        surveyRelativeLayout = (RelativeLayout) surveyView.findViewById(R.id.surveyRelativeLayout);
        categorySpinner = (Spinner) surveyView.findViewById(R.id.category_spinner);
        categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                categoryList);
        categorySpinner.setAdapter(categoryAdapter);

        addCategoryButton = (Button) surveyView.findViewById(R.id.add_category);
        addCategoryButton.setOnClickListener(categoryOnClickListener);

        addQuesionButton = (Button) surveyView.findViewById(R.id.survey_submit);
        addQuesionButton.setOnClickListener(submitQuestionListener);
        return surveyView;
    }

    private View.OnClickListener categoryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText editTextCategory = (EditText) surveyView.findViewById(R.id.survey_category);
            String category = editTextCategory.getText().toString().trim();

            if (!(category.length() > 0)) {
                editTextCategory.setError("Category cannot be empty");

            } else {
                categoryList.add(category);
                mDatabase = FirebaseDatabase.getInstance().getReference("categories");
                mDatabase.setValue(categoryList);

            }
        }
    };


    private View.OnClickListener submitQuestionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String categorySelected = null;
            EditText editTextCategory = (EditText) surveyView.findViewById(R.id.survey_category);
            String category = editTextCategory.getText().toString().trim();
            EditText editTextQuestion = (EditText) surveyView.findViewById(R.id.add_questions);
            String surveyQuestion = editTextQuestion.getText().toString().trim();
            EditText editTextsurveyName = (EditText) surveyView.findViewById(R.id.survey_name);
            String surveyName = editTextsurveyName.getText().toString().trim();
            Spinner categorySpinner = (Spinner) surveyView.findViewById(R.id.category_spinner);



            if (!(surveyName.length() > 0)) {
                editTextsurveyName.setError("SurveyName cannot be empty.");
            }

            else if (!(surveyQuestion.length() > 0)) {
                editTextQuestion.setError("Question cannot be empty");
            }

            else {
                if (categorySpinner.getSelectedItem() == null) {
                    Toast.makeText(getActivity(), "Please Wait..Loading categories", Toast.LENGTH_SHORT).show();
                } else {
                    categorySelected = categorySpinner.getSelectedItem().toString();
                }
                try {
                    ProgressBar progressBar = (ProgressBar) surveyView.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Quetions");
                    Question question = new Question(categorySelected, surveyQuestion);
                    mDatabase.child(surveyName).push().setValue(question);
                    Toast.makeText(getActivity(), "Question created successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please Try after some time!", Toast.LENGTH_SHORT).show();
                    Log.e("Creating question error", e.getMessage());
                }

            }

        }
    };



    private void addQuestionToSurvey()
    {

    }
}
