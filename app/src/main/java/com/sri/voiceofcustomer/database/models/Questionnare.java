package com.sri.voiceofcustomer.database.models;

import java.util.List;

/**
 * Created by SRiGorthi on 17-01-2017.
 */

public class Questionnare {

public String surveyId ;
public List<Question> questionList;

    public Questionnare()
    {}
    public Questionnare(String surveyId, List<Question>questionList) {
        this.surveyId = surveyId;
        this.questionList=questionList;
    }

    public void addQuestions(Question q)
    {
        questionList.add(q);
    }
}
