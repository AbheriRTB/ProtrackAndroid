package abheri.co.protracklite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.data.GoalDataHelper;
import abheri.co.protracklite.utils.builders.Subject;
import abheri.co.protracklite.utils.data.SubjectDataHelper;
import abheri.co.protracklite.utils.data.Topic;
import abheri.co.protracklite.utils.data.TopicDataHelper;
import abheri.co.protracklite.utils.builders.TopicDataMap;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;

public class MainActivity extends AppCompatActivity {

    BottomAppBar appBar;
    FloatingActionButton floatingActionButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.aboutMenu:

                break;
            // action with ID action_settings was selected
            case R.id.addMenu:
                startActivity(new Intent(MainActivity.this, AddActivity.class));

                break;
            // action with ID action_settings was selected
            case R.id.manageMenu:
                Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBFunctions();
        setContentView(R.layout.activity_main);

        appBar = findViewById(R.id.mainBottomAppBar);
        setSupportActionBar(appBar);
        floatingActionButton = findViewById(R.id.fabAdd);

        floatingActionButton.setColorFilter(Color.WHITE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, GoalActivity.class));
            }
        });
    }

    void DBFunctions(){
        SubjectDataHelper sdh = new SubjectDataHelper(this);
        TopicDataHelper tdh = new TopicDataHelper(this);
        GoalDataHelper gdh = new GoalDataHelper(this);
        TopicMapDataHelper tmdh = new TopicMapDataHelper(this);


        List<Subject> ls = sdh.getAllSubjects();
        List<Topic> ts = tdh.getAllTopics();
        List<Goal> gs = gdh.getAllGoals();
        List<TopicDataMap> tds = tmdh.getAllTopicDataMaps();
        if (ls.size() <= 0) {
            sdh.createSubject("English", "English Subject", 1);
            sdh.createSubject("II lang (Hindi)", "Hindi", 2);
            sdh.createSubject("II lang (Kannada)", "Kannada", 3);
            sdh.createSubject("II lang (Sanskrit)", "Sanskrit", 4);
            sdh.createSubject("Maths", "Mathematics", 6);
            sdh.createSubject("Science", "General Science", 7);
            sdh.createSubject("Social", "Social Science", 8);
        }


        if (ts.size() <= 0) {

            // English Portions
            tdh.createTopic(1, "The Fun They Had", "This is chapter I", 1);
            tdh.createTopic(2, "The Road Not Taken", "This is poem I", 1);
            tdh.createTopic(3, "The Sound of Music", "This is chapter II", 1);
            tdh.createTopic(4, "Wind", "This is poem II", 1);
            tdh.createTopic(5, "The Little Girl", "This is chapter III", 1);
            tdh.createTopic(6, "Rain on the Roof", "This is poem III", 1);
            tdh.createTopic(7, "A Truly Beautiful Mind", "This is chapter IV", 1);
            tdh.createTopic(8, "The Lake Isle of Innisfree", "This is poem IV", 1);
            tdh.createTopic(9, "The Snake and the Mirror", "This is chapter V", 1);
            tdh.createTopic(10, "A Legend of the Northland", "This is poem V", 1);
            tdh.createTopic(11, "My Childhood", "This is chapter VI", 1);
            tdh.createTopic(12, "No Men Are Foreign", "This is poem VI", 1);
            tdh.createTopic(13, "Packing", "This is chapter VII", 1);
            tdh.createTopic(14, "The Duck and the Kangaroo", "This is poem VII", 1);
            tdh.createTopic(15, "Reach for the Top", "This is chapter VIII", 1);
            tdh.createTopic(16, "On Killing a Tree", "This is poem VIII", 1);
            tdh.createTopic(17, "The Bond of Love", "This is chapter IX", 1);
            tdh.createTopic(18, "The Snake Trying", "This is poem IX", 1);
            tdh.createTopic(19, "Kathmandu", "This is chapter X", 1);
            tdh.createTopic(20, "A Slumber Did My Spirit Seal", "This is poem X", 1);
            tdh.createTopic(21, "If I Were You", "This is chapter XI", 1);

            // Science Chapters
            tdh.createTopic(22, "Matter in Our Surroundings", "This is Chemistry chapter I", 7);
            tdh.createTopic(23, "Is Matter Around Us Pure", "This is Chemistry chapter II", 7);
            tdh.createTopic(24, "Atoms and Molecules", "This is chapter XI", 7);
            tdh.createTopic(25, "Structure of the Atom", "This is Chemistry chapter III", 7);
            tdh.createTopic(26, "The Fundamental Unit of Life", "This is Biology chapter I", 7);
            tdh.createTopic(27, "Tissues", "This is Biology chapter II", 7);
            tdh.createTopic(28, "Diversity in Living Organisms", "This is Biology chapter III", 7);
            tdh.createTopic(29, "Motion", "This is Physics chapter I", 7);
            tdh.createTopic(30, "Force and Laws of Motion ", "This is Physics chapter II", 7);
            tdh.createTopic(31, "Gravitation", "This is Physics chapter III", 7);
            tdh.createTopic(32, "Work and Energy", "This is Physics chapter IV", 7);
            tdh.createTopic(33, "Sound", "This is Physics chapter V", 7);
            tdh.createTopic(34, "Why Do We Fall Ill", "This is Biology chapter IV", 7);
            tdh.createTopic(35, "Natural Resources", "This is Biology chapter V", 7);
            tdh.getAllTopics();
        }

        if (gs.size() <= 0) {

            gdh.createGoal("Mid-Term Exams", "There will be mid term exams this month", "1/12/2020");
            gdh.createGoal("Sangeeta Exams", "There will be mid term exams this month", "22/11/2020");
            gdh.getAllGoals();

        }

        if(tds.size() <= 0){
            tmdh.createTopicDataMap(1,1);
        }
        tmdh.getTopicsForGoal(1);


    }
}