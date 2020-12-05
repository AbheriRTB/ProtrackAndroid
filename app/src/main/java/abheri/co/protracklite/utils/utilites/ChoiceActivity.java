package abheri.co.protracklite.utils.utilites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.List;

import abheri.co.protracklite.R;
import abheri.co.protracklite.TopicActivity;
import abheri.co.protracklite.utils.adaptors.ChoiceAdaptor;
import abheri.co.protracklite.utils.adaptors.TopicAdaptor;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.data.TopicDataHelper;

public class ChoiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    List<Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        recyclerView = findViewById(R.id.listGoal);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TopicDataHelper tdh = new TopicDataHelper(this);
        topics = tdh.getAllTopics();


        LayoutInflater inflater = ChoiceActivity.this.getLayoutInflater();
        myAdaptor = new ChoiceAdaptor(this, topics, recyclerView);
        recyclerView.setAdapter(myAdaptor);
    }
}