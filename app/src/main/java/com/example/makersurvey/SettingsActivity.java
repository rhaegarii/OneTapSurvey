package com.example.makersurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.Preference;

public class SettingsActivity extends AppCompatActivity {
    public static final String
            KEY_PREF_EXAMPLE_SWITCH = "switch_preference_1";
    public static final String
            EDIT_TEXT_QUESTION= "edit_text_preference_question";
    public static final String
            EDIT_TEXT_ANSWER_ONE= "edit_text_preference_1";
    public static final String
            EDIT_TEXT_ANSWER_TWO= "edit_text_preference_2";
    public static final String
            EDIT_TEXT_ANSWER_THREE= "edit_text_preference_3";
    public static final String
            EDIT_TEXT_ANSWER_FILENAME= "edit_text_preference_4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();



    }
    public void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
