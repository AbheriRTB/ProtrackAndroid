package abheri.co.protracklite;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.Goal;
import abheri.co.protracklite.utils.GoalDataHelper;
import abheri.co.protracklite.utils.Topic;
import abheri.co.protracklite.utils.TopicDataHelper;
import abheri.co.protracklite.utils.TopicDataMap;
import abheri.co.protracklite.utils.TopicMapDataHelper;

public class AddActivity extends AppCompatActivity {

    BottomAppBar appBar;
    FloatingActionButton fab;
    MaterialButton btnTime, btnTopics;
    EditText etName, etDescription;
    String[] listItems;
    List<Topic> topics;
    CharSequence[] cs = null;
    String goalName, goalDescription;
    ArrayList<Integer> dataMaps;
    boolean[] checkedItems;
    TopicMapDataHelper tdc;
    TopicDataMap tdm;
    GoalDataHelper gdc;
    int topic_id, goal_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnTime = findViewById(R.id.btnTime);
        btnTopics = findViewById(R.id.btnChapters);
        etName = findViewById(R.id.etGoal);
        etDescription = findViewById(R.id.etAddDiscription);
        fab = findViewById(R.id.fabAdd);
        appBar = findViewById(R.id.GoalBottomAppBar);
        dataMaps = new ArrayList<>();
        tdc = new TopicMapDataHelper(AddActivity.this);
        gdc = new GoalDataHelper(AddActivity.this);
        tdm = new TopicDataMap();

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

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create New Goal
                Goal newGoal = gdc.createGoal(etName.getText().toString(), etDescription.getText().toString(), "1/2/2020");

                // Topic Map Dialog
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(AddActivity.this);
                dialogBuilder.setTitle("Enter topics");
                cs = getTopicTitles();
                checkedItems = new boolean[cs.length];
                for (int i = 0; i < cs.length; ++i)
                    checkedItems[i] = false;
                dialogBuilder.setMultiChoiceItems(cs, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            dataMaps.add(which);
                        } else {
                            dataMaps.remove((Integer.valueOf(which)));
                        }
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < dataMaps.size(); i++) {
                            topic_id = dataMaps.get(i);
                            //tdc.deleteTopicDataMap();
                            tdc.createTopicDataMap(topic_id, newGoal.getId());
                        }
                        dataMaps.clear();
                        startActivity(new Intent(AddActivity.this, GoalActivity.class));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(AddActivity.this, GoalActivity.class));
                    }
                }).show();

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