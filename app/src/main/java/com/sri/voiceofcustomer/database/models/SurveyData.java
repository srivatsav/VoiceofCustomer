package com.sri.voiceofcustomer.database.models;

/**
 * Created by SRiGorthi on 22-01-2017.
 */

public class SurveyData {

    String email;
    String surveyName;
    String status;
    int noOfQuestAnswered;

    public SurveyData(){}
    public SurveyData(String email, String surveyName, String status, int noOfQuestAnswered) {
        this.email = email;
        this.surveyName = surveyName;
        this.status = status;
        this.noOfQuestAnswered = noOfQuestAnswered;
    }
}
