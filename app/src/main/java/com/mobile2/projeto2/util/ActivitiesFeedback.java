package com.mobile2.projeto2.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar.andrade on 20/06/18.
 */

public class ActivitiesFeedback {

    private static List<Feedback> feedbacks = new ArrayList<>();


    public static void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }

    public static List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public static void cleanFeedbacks(){
        feedbacks.clear();
    }
}
