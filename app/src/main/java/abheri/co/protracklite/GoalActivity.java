package abheri.co.protracklite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.adaptors.GoalAdaptor;
import abheri.co.protracklite.utils.data.GoalDataHelper;
import abheri.co.protracklite.utils.data.Topic;
import abheri.co.protracklite.utils.data.TopicDataHelper;

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

        for (int i = 0; i < goals.size(); i++) {
            List<Topic> lt;
            lt = tdh.getTopicsBySubject(goals.get(i).getId());
        }

        LayoutInflater inflater = GoalActivity.this.getLayoutInflater();
        myAdaptor = new GoalAdaptor(this, goals, recyclerView);
        recyclerView.setAdapter(myAdaptor);

        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoalActivity.this, AddActivity.class));
            }
        });

    }
}