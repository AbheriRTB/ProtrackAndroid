package abheri.co.protracklite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.Goal;
import abheri.co.protracklite.utils.GoalAdaptor;
import abheri.co.protracklite.utils.GoalDataHelper;
import abheri.co.protracklite.utils.OldTopic;
import abheri.co.protracklite.utils.Subject;
import abheri.co.protracklite.utils.SubjectAdaptor;
import abheri.co.protracklite.utils.SubjectDataHelper;
import abheri.co.protracklite.utils.Topic;
import abheri.co.protracklite.utils.TopicDataHelper;

public class GoalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fabAddGoal;
    List<Goal> goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        recyclerView = findViewById(R.id.listGoal);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fabAddGoal = findViewById(R.id.fabAddGoal);

        fabAddGoal.setColorFilter(Color.WHITE);

        GoalDataHelper gdh = new GoalDataHelper(this);

        goals = gdh.getAllGoals();
        TopicDataHelper tdh = new TopicDataHelper(this);

        for (int i = 0; i < goals.size(); i++){
            List<Topic> lt;
            lt = tdh.getTopicsBySubject(goals.get(i).getId());
        }

        LayoutInflater inflater = GoalActivity.this.getLayoutInflater();
        myAdaptor = new GoalAdaptor(this, goals, recyclerView);
        recyclerView.setAdapter(myAdaptor);

    }
}