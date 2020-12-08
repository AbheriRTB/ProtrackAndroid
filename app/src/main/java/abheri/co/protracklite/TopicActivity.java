package abheri.co.protracklite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.List;

import abheri.co.protracklite.utils.adaptors.TopicAdaptor;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.data.TopicDataHelper;

public class TopicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    List<Topic> topics;
    SwipeRefreshLayout swipeRefreshLayout;
    Topic topic;
    BottomAppBar bottomAppBar;
    long id_goal;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_settings was selected
            case R.id.homeMenu:
                startActivity(new Intent(TopicActivity.this, DashboardActivity.class));

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        recyclerView = findViewById(R.id.listGoal);
        swipeRefreshLayout = findViewById(R.id.srlTopic);
        recyclerView.setHasFixedSize(true);
        bottomAppBar = findViewById(R.id.topicMenuBar);
        setSupportActionBar(bottomAppBar);

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