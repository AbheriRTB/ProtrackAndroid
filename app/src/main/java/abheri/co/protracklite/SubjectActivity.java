package abheri.co.protracklite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.adaptors.SubjectAdaptor;
import abheri.co.protracklite.utils.builders.OldTopic;
import abheri.co.protracklite.utils.builders.Subject;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.data.SubjectDataHelper;
import abheri.co.protracklite.utils.data.TopicDataHelper;

public class SubjectActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<OldTopic> oldTopics;
    TextView tvTitle;
    BottomAppBar bottomAppBar;
    List<Subject> subjects;
    List<Topic> topics;


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
                startActivity(new Intent(SubjectActivity.this, DashboardActivity.class));

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        recyclerView = findViewById(R.id.listGoal);
        recyclerView.setHasFixedSize(true);
        tvTitle = findViewById(R.id.tvTitle2);
        bottomAppBar = findViewById(R.id.topicMenuBar);
        setSupportActionBar(bottomAppBar);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        oldTopics = new ArrayList<OldTopic>();

        /*fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setColorFilter(Color.WHITE);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainTopicsActivity.this,
                        R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);

                View view = inflater.inflate(R.layout.dialog_text_input, null);
                builder.setView(view)
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setBackground(getResources().getDrawable(R.drawable.custom_dialog));
                builder.show();
            }
        });
*/

        SubjectDataHelper sdh = new SubjectDataHelper(this);

        subjects = sdh.getAllSubjects();
        TopicDataHelper tdh = new TopicDataHelper(this);
        topics = tdh.getAllTopics();

        for (int i = 0; i < subjects.size(); i++) {
            List<Topic> lt;
            lt = tdh.getTopicsBySubject(subjects.get(i).getId());
            subjects.get(i).setTopics(lt);
        }

        LayoutInflater inflater = SubjectActivity.this.getLayoutInflater();
        myAdaptor = new SubjectAdaptor(this, subjects, getResources().getDrawable(R.drawable.custom_dialog), inflater, recyclerView);
        recyclerView.setAdapter(myAdaptor);

    }
}