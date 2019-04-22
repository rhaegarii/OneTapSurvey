package com.example.makersurvey;
import com.surveymonkey.surveymonkeyandroidsdk.SMFeedbackFragmentListener;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;
import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SM extends MainActivity{
    public static final int SM_REQUEST_CODE = 1;
    public static final String SM_RESPONDENT = "smRespondent";
    public static final String SM_ERROR = "smError";
    public static final String RESPONSES = "responses";
    public static final String QUESTION_ID = "question_id";
    public static final String FEEDBACK_QUESTION_ID = "813797519";
    public static final String ANSWERS = "answers";
    public static final String ROW_ID = "row_id";
    public static final String FEEDBACK_FIVE_STARS_ROW_ID = "9082377273";
    public static final String FEEDBACK_POSITIVE_ROW_ID_2 = "9082377274";
    public static final String SAMPLE_APP = "Sample App";
    public static final String SURVEY_HASH = "SJNVLMC";

    // Initialize the SurveyMonkey SDK like so
    private SurveyMonkey s = new SurveyMonkey();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Here we're setting up the SurveyMonkey Intercept Dialog -- the user will be prompted to take the survey 3 days after app install.
        // Once prompted, the user will be reminded to take the survey again in 3 weeks if they decline or 3 months if they consent to take the survey.
        // The onStart method can be overloaded so that you can supply your own prompts and intervals -- for more information, see our documentation on Github.
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void takeSurvey(View view) {
        //This is how you display a survey for the user to take
        // Remember: you must supply the parent activity (e.g. this), your own request code (to differentiate from other activities), and the collector hash of the SDK collector you've created at SurveyMonkey.com
        s.startSMFeedbackActivityForResult(this, SM_REQUEST_CODE, SURVEY_HASH);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
