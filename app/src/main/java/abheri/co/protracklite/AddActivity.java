package abheri.co.protracklite;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.data.GoalDataHelper;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.data.ProgressDataHelper;
import abheri.co.protracklite.utils.data.TopicDataHelper;
import abheri.co.protracklite.utils.builders.TopicDataMap;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;
import abheri.co.protracklite.utils.utilites.ChoiceActivity;

public class AddActivity extends AppCompatActivity {

    BottomAppBar appBar;
    FloatingActionButton fab;
    MaterialButton btnTime, btnTopics;
    EditText etName, etDescription;
    String[] listItems;
    List<Topic> topics;
    CharSequence[] cs = null, idCS = null;
    String goalName, goalDescription, endDate;
    ArrayList<Integer> dataMaps;
    boolean[] checkedItems;
    TopicMapDataHelper tdc;
    TopicDataMap tdm;
    GoalDataHelper gdc;
    ProgressDataHelper pdh;
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
        pdh = new ProgressDataHelper(AddActivity.this);
        tdm = new TopicDataMap();

        setSupportActionBar(appBar);

        fab.setColorFilter(Color.WHITE);


        //MaterialDatePicker.Builder datePicker = new MaterialDatePicker.Builder.datePicker();

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.build();
                builder.setTitleText("SELECT A DATE");
                final MaterialDatePicker<Long> materialDatePicker = builder.build();
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                btnTime.setText("End Date is : " + materialDatePicker.getHeaderText());
                                endDate = materialDatePicker.getHeaderText();
                            }
                        });

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
                getTopicTitles();

                // Create New Goal
                Goal newGoal = gdc.createGoal(etName.getText().toString(), etDescription.getText().toString(), endDate);

                // Intent to Goals
                Intent intent = new Intent(AddActivity.this, GoalActivity.class);
                //intent.putExtra("goal_id", newGoal.getId());

                // Topic Map Dialog
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(AddActivity.this);
                dialogBuilder.setTitle("Enter topics");
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
                            topic_id = dataMaps.get(i)+1;
                            //tdc.deleteTopicDataMap();
                            tdc.createTopicDataMap(topic_id, newGoal.getId());
                            Calendar calendar = Calendar.getInstance();
                            String time = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/"
                                    + calendar.get(Calendar.YEAR);
                            pdh.createProgress(0, time, tdc.getAllTopicDataMaps().get(i).getTdm_id());
                        }
                        dataMaps.clear();
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(intent);
                    }
                }).setBackground(getResources().getDrawable(R.drawable.custom_dialog)).show();
                //startActivity(new Intent(AddActivity.this, ChoiceActivity.class));

            }
        });

    }

    private void getTopicTitles() {
        ArrayList<String> csl = new ArrayList<String>();
        ArrayList<Long> idSL = new ArrayList<Long>();
        TopicDataHelper tdh = new TopicDataHelper(AddActivity.this);
        List<Topic> lt = tdh.getAllTopics();
        if (lt != null && lt.size() > 0) {
            for (int i = 0; i < lt.size(); ++i) {
                csl.add(lt.get(i).getName());
                idSL.add(lt.get(i).getTopicID());
            }
            cs = csl.toArray(new CharSequence[lt.size()]);
            idCS = csl.toArray(new CharSequence[lt.size()]);
        }
    }
}