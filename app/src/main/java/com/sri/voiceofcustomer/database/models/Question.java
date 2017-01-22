package com.sri.voiceofcustomer.database.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 405116 on 1/18/2017.
 */

public class Question {

    public String surveyName;
    public String category;
    public String question;

    public Question()
    {

    }
    public Question(String category, String question) {

        this.category = category;
        this.question = question;
    }
}
