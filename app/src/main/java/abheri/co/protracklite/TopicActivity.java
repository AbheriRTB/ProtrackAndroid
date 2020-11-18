package abheri.co.protracklite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import abheri.co.protracklite.utils.Goal;
import abheri.co.protracklite.utils.GoalAdaptor;
import abheri.co.protracklite.utils.Topic;
import abheri.co.protracklite.utils.TopicAdaptor;
import abheri.co.protracklite.utils.TopicDataHelper;
import abheri.co.protracklite.utils.TopicMapDataHelper;

public class TopicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    List<Topic> topics;
    Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        recyclerView = findViewById(R.id.listGoal);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TopicDataHelper tdh = new TopicDataHelper(this);
        topics = tdh.getAllTopics();

        LayoutInflater inflater = TopicActivity.this.getLayoutInflater();
        myAdaptor = new TopicAdaptor(this, topics, recyclerView);
        recyclerView.setAdapter(myAdaptor);


    }
}