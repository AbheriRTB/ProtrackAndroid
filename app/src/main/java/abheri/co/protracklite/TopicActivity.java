package abheri.co.protracklite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.List;

import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.adaptors.TopicAdaptor;
import abheri.co.protracklite.utils.data.TopicDataHelper;

public class TopicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    List<Topic> topics;
    SwipeRefreshLayout swipeRefreshLayout;
    Topic topic;
    long id_goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        recyclerView = findViewById(R.id.listGoal);
        swipeRefreshLayout = findViewById(R.id.srlTopic);
        recyclerView.setHasFixedSize(true);

        int c1 = getResources().getColor(R.color.colorPrimaryAccent);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TopicDataHelper tdh = new TopicDataHelper(this);
        topics = tdh.getAllTopics();

        id_goal = getIntent().getLongExtra("goal_id", 0);

        Intent intent = getIntent();

        LayoutInflater inflater = TopicActivity.this.getLayoutInflater();
        myAdaptor = new TopicAdaptor(this, topics, recyclerView, id_goal, inflater);
        recyclerView.setAdapter(myAdaptor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdaptor.notifyDataSetChanged();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(c1);

    }
}