package abheri.co.protracklite;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.builders.TopicDataMap;
import abheri.co.protracklite.utils.data.GoalDataHelper;
import abheri.co.protracklite.utils.data.ProgressDataHelper;
import abheri.co.protracklite.utils.data.TopicDataHelper;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;

public class AddActivity extends AppCompatActivity {

    boolean[] checkedItems;
    long topic_id;
    String endDate;

    BottomAppBar appBar;
    FloatingActionButton fab;
    MaterialButton btnTime;
    EditText etName, etDescription;

    CharSequence[] cs = null;
    ArrayList<Integer> dataMaps;
    ArrayList<Long> idSL = new ArrayList<Long>();

    TopicMapDataHelper tdc;
    TopicDataMap tdm;
    GoalDataHelper gdc;
    ProgressDataHelper pdh;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_settings was selected
            case R.id.back:
                startActivity(new Intent(AddActivity.this, DashboardActivity.class));

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnTime = findViewById(R.id.btnTime);
        etName = findViewById(R.id.etGoal);
        etDescription = findViewById(R.id.etAddDiscription);
        fab = findViewById(R.id.fabAdd);
        appBar = findViewById(R.id.addMenuBar);
        dataMaps = new ArrayList<>();
        tdc = new TopicMapDataHelper(AddActivity.this);
        gdc = new GoalDataHelper(AddActivity.this);
        pdh = new ProgressDataHelper(AddActivity.this);
        tdm = new TopicDataMap();

        setSupportActionBar(appBar);

        fab.setColorFilter(Color.WHITE);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoal();
            }
        });

    }

    private void getTopicTitles() {
        ArrayList<String> csl = new ArrayList<String>();
        TopicDataHelper tdh = new TopicDataHelper(AddActivity.this);
        List<Topic> lt = tdh.getAllTopics();
        if (lt != null && lt.size() > 0) {
            for (int i = 0; i < lt.size(); ++i) {
                csl.add(lt.get(i).getName());
                idSL.add(lt.get(i).getTopicID());
            }
            cs = csl.toArray(new CharSequence[lt.size()]);
        }
    }

    private void getTime() {
        /*MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
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
                });*/
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                endDate = day + "/" + month + "/" + year;
                btnTime.setText("End Date is : " + endDate);

            }
        };
        DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private void addGoal() {
        getTopicTitles();

        // Create New Goal
        Goal newGoal = gdc.createGoal(etName.getText().toString(), etDescription.getText().toString(), endDate);

        // Intent to Goals
        Intent intent = new Intent(AddActivity.this, GoalActivity.class);

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
                    topic_id = idSL.get(i);
                    //tdc.deleteTopicDataMap();
                    TopicDataMap tmap = tdc.createTopicDataMap(topic_id, newGoal.getId());
                    Calendar calendar = Calendar.getInstance();
                    String time = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/"
                            + calendar.get(Calendar.YEAR);
                    pdh.resetProgressIsLatest(tmap.getTdm_id());
                    pdh.createProgress(0, time, tmap.getTdm_id());
                }
                dataMaps.clear();
                startActivity(intent);
            }
        }).setNegativeButton("Cancel",null).setBackground(getResources().getDrawable(R.drawable.custom_dialog)).show();
        //startActivity(new Intent(AddActivity.this, ChoiceActivity.class));

    }
}