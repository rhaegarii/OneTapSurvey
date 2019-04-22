package com.example.makersurvey;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;
import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    public static final int SM_REQUEST_CODE = 1;
    public static final String SURVEY_HASH = "SJNVLMC";
    private SurveyMonkey s = new SurveyMonkey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadActivity();


    }

    public void greenClick(View view) {
        printCsv("1", false);
        onClickDialog(view, "That's Great to Hear!");

    }

    public void yellowClick(View view) {
        printCsv("2", false);
        onClickDialog(view, "Okay");
    }
    public void redClick(View view) {
        printCsv("3", false);
        onClickDialog(view, "Bad");
    }

    public void blueClick1(View view) {
        printCsv("1", true);

    }
    public void blueClick2(View view) {
        printCsv("2", true);
    }
    public void blueClick3(View view) {
        printCsv("3", true);

    }
    public void blueClick4(View view) {
        printCsv("4", true);

    }


    //Prints time and button to csv file on SD card
    public void printCsv(String but, boolean mode) {
        toastThanks();
        String fileName = "log";
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String question = sharedPref.getString(SettingsActivity.EDIT_TEXT_QUESTION, "Errorq");
        question = "Question: " + question;
        if (mode) {
                if (but.equals("1"))
                    but = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_ONE, "Error1");
                if (but.equals("2"))
                    but = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_TWO, "Error2");
                if (but.equals("3"))
                    but = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_THREE, "Error3");
                if (but.equals("4"))
                    but = "None of the Above";

            fileName = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_FILENAME, "Errorf");
        }

        FileOutputStream outputstream;
        FileOutputStream os1;
        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String writer = but + ", " + simpleDate.format(currentTime);
        Log.d("hello", writer);
        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), fileName + ".csv");
            Log.d("This is my message",file.getAbsolutePath());
            // if file doesn't exists, then create it
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
                os1 = new FileOutputStream(file, true);
                OutputStreamWriter osw= new OutputStreamWriter(os1);
                BufferedWriter b1 = new BufferedWriter((osw));
                b1.write(question);
                b1.newLine();
                b1.close();
                os1.close();
            }
            outputstream = new FileOutputStream(file, true);
            OutputStreamWriter oswriter = new OutputStreamWriter(outputstream);
            BufferedWriter bw = new BufferedWriter(oswriter);
            bw.write(writer);
            bw.newLine();
            bw.close();
            outputstream.close();

        } catch (IOException e) {
            Log.e("", "Could not create file.", e);

        }
    }

    public void onClickDialog(View v, String message) {
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        //builder.setTitle(message);
        builder.setMessage("Thanks so much for your feedback! Would you like to take a moment to " +
                "fill out a 5 question, 30 second survey about the Invention Studio! " +
                "\n\nThis message will close automatically after 10 seconds");
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                takeSurvey(s, view);
            }
        });
        builder.setNegativeButton(R.string.nothanks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        final AlertDialog dlg = builder.create();

        dlg.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 10000); // after 2 second (or 2000 miliseconds), the task will be active.
    }
    public void takeSurvey(SurveyMonkey s, View view) {
        //This is how you display a survey for the user to take
        // Remember: you must supply the parent activity (e.g. this), your own request code (to differentiate from other activities), and the collector hash of the SDK collector you've created at SurveyMonkey.com
        s.startSMFeedbackActivityForResult(this, SM_REQUEST_CODE, SURVEY_HASH);
    }

    public void optionsMenu(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void changeText(View view) {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        String question = sharedPref.getString(SettingsActivity.EDIT_TEXT_QUESTION, "Errorq");
        String answer1 = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_ONE, "Error1");
        String answer2 = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_TWO, "Error2");
        String answer3 = sharedPref.getString(SettingsActivity.EDIT_TEXT_ANSWER_THREE, "Error3");

        TextView header = findViewById(R.id.textView);
        header.setText(question);
        Button button1 = findViewById(R.id.button3);
        button1.setText(answer1);
        Button button2 = findViewById(R.id.button4);
        button2.setText(answer2);
        Button button3 = findViewById(R.id.button5);
        button3.setText(answer3);
    }
  /* public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }*/
    public void toastThanks() {
        Toast.makeText(getApplicationContext(),"Thanks!",Toast.LENGTH_SHORT).show();

    }

    public void loadActivity(){
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);

        if (switchPref == true) {
            setContentView(R.layout.activity_button_questions);
            View view = new View(this);
            changeText(view);
        }
        else
            setContentView(R.layout.activity_main);

    }
    public void loadActivity(View v){
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);

        if (switchPref == true) {
            setContentView(R.layout.activity_button_questions);
            View view = new View(this);
            changeText(view);
        }
        else
            setContentView(R.layout.activity_main);

    }


}
