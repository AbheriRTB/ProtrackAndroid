package abheri.co.protracklite;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialDatePicker.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.Subject;
import abheri.co.protracklite.utils.SubjectAdaptor;
import abheri.co.protracklite.utils.SubjectDataHelper;
import abheri.co.protracklite.utils.Topic;
import abheri.co.protracklite.utils.TopicDataHelper;

public class AddActivity extends AppCompatActivity {

    BottomAppBar appBar;
    FloatingActionButton fab;
    MaterialButton btnTime, btnTopics;
    MaterialTextView tvName, tvDescription;
    List<Subject> subjects;
    List<Topic> topics;
    CharSequence[] cs = null;
    String goalName, goalDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnTime = findViewById(R.id.btnTime);
        btnTopics = findViewById(R.id.btnChapters);
        tvName = findViewById(R.id.tvAddGoal);
        tvDescription = findViewById(R.id.tvAddDiscription);
        fab = findViewById(R.id.fabAdd);
        appBar = findViewById(R.id.GoalBottomAppBar);

        setSupportActionBar(appBar);

        fab.setColorFilter(Color.WHITE);


        //MaterialDatePicker.Builder datePicker = new MaterialDatePicker.Builder.datePicker();

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(AddActivity.this);
                dialogBuilder.setTitle("Enter topics");
                getTopicTitles();
                dialogBuilder.setMultiChoiceItems(cs, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                }).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalName = tvName.getText().toString();
                goalDescription = tvDescription.getText().toString();
            }
        });

    }
    private CharSequence[] getTopicTitles() {
        ArrayList<String> csl = new ArrayList<String>();
        TopicDataHelper tdh = new TopicDataHelper(AddActivity.this);
        List<Topic> lt = tdh.getAllTopics();
        if (lt != null && lt.size() > 0) {
            for (int i = 0; i < lt.size(); ++i) {
                csl.add(lt.get(i).getName());
            }
            cs = csl.toArray(new CharSequence[lt.size()]);
        }

        return cs;
    }
}