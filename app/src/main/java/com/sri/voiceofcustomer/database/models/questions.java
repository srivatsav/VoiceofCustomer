package com.sri.voiceofcustomer.database.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 405116 on 1/18/2017.
 */

public class Questions {
    public String category;
    public List<String> questions = new ArrayList<String>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

}
