package com.sri.voiceofcustomer.database.models;

/**
 * Created by SRiGorthi on 22-01-2017.
 */

public class AssignSurvey {

    public String company;
    public String surveyId;

    public AssignSurvey(){}

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
}
