package com.sri.voiceofcustomer.survey.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sri.voiceofcustomer.R;

import java.util.zip.Inflater;

/**
 * Created by 405116 on 1/18/2017.
 */

public class SurveyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_survey,container,false);
    }
}
