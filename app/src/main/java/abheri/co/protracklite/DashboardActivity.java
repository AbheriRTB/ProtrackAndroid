package abheri.co.protracklite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import abheri.co.protracklite.utils.adaptors.DashAdaptor;
import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.builders.Progress;
import abheri.co.protracklite.utils.builders.Subject;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.builders.TopicDataMap;
import abheri.co.protracklite.utils.data.GoalDataHelper;
import abheri.co.protracklite.utils.data.ProgressDataHelper;
import abheri.co.protracklite.utils.data.SubjectDataHelper;
import abheri.co.protracklite.utils.data.TopicDataHelper;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;

public class DashboardActivity extends AppCompatActivity {

    BottomAppBar appBar;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdaptor;
    RecyclerView.LayoutManager layoutManager;
    List<Goal> goals;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onResume() {
        super.onResume();
        myAdaptor.notifyDataSetChanged();
    }

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
                startActivity(new Intent(DashboardActivity.this, AddActivity.class));

                break;
            // action with ID action_settings was selected
            case R.id.manageMenu:
                Intent intent = new Intent(DashboardActivity.this, SubjectActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBFunctions();
        setContentView(R.layout.activity_dashboard);

        appBar = findViewById(R.id.mainBottomAppBar);
        setSupportActionBar(appBar);
        floatingActionButton = findViewById(R.id.fabAdd);
        swipeRefreshLayout = findViewById(R.id.srlDash);

        recyclerView = findViewById(R.id.dashList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        GoalDataHelper gdh = new GoalDataHelper(this);

        goals = gdh.getAllGoals();
        TopicDataHelper tdh = new TopicDataHelper(this);

        for (int i = 0; i < goals.size(); i++) {
            List<Topic> lt;
            lt = tdh.getTopicsBySubject(goals.get(i).getId());
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, GoalActivity.class));
            }
        });

        //LayoutInflater inflater = DashboardActivity.this.getLayoutInflater();
        myAdaptor = new DashAdaptor(this, goals, recyclerView);
        recyclerView.setAdapter(myAdaptor);

        floatingActionButton.setColorFilter(Color.WHITE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdaptor.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void DBFunctions() {
        SubjectDataHelper sdh = new SubjectDataHelper(this);
        TopicDataHelper tdh = new TopicDataHelper(this);
        GoalDataHelper gdh = new GoalDataHelper(this);
        TopicMapDataHelper tmdh = new TopicMapDataHelper(this);
        ProgressDataHelper pdh = new ProgressDataHelper(this);


        List<Subject> ls = sdh.getAllSubjects();
        List<Topic> ts = tdh.getAllTopics();
        List<Goal> gs = gdh.getAllGoals();
        List<TopicDataMap> tds = tmdh.getAllTopicDataMaps();
        List<Progress> ps = pdh.getAllProgresses();

        if (ls.size() <= 0) {
            sdh.createSubject("English", "English Subject", 1);
            sdh.createSubject("II lang (Hindi)", "Hindi", 2);
            sdh.createSubject("II lang (Kannada)", "Kannada", 3);
            sdh.createSubject("II lang (Sanskrit)", "Sanskrit", 4);
            sdh.createSubject("Maths", "Mathematics", 5);
            sdh.createSubject("Science", "General Science", 6);
            sdh.createSubject("Social", "Social Science", 7);
        }


        if (ts.size() <= 0) {

            // English Portions
            tdh.createTopic("The Fun They Had", "This is chapter I", 1);
            tdh.createTopic("The Road Not Taken", "This is poem I", 1);
            tdh.createTopic("The Sound of Music", "This is chapter II", 1);
            tdh.createTopic("Wind", "This is poem II", 1);
            tdh.createTopic("The Little Girl", "This is chapter III", 1);
            tdh.createTopic("Rain on the Roof", "This is poem III", 1);
            tdh.createTopic("A Truly Beautiful Mind", "This is chapter IV", 1);
            tdh.createTopic("The Lake Isle of Innisfree", "This is poem IV", 1);
            tdh.createTopic("The Snake and the Mirror", "This is chapter V", 1);
            tdh.createTopic("A Legend of the Northland", "This is poem V", 1);
            tdh.createTopic("My Childhood", "This is chapter VI", 1);
            tdh.createTopic("No Men Are Foreign", "This is poem VI", 1);
            tdh.createTopic("Packing", "This is chapter VII", 1);
            tdh.createTopic("The Duck and the Kangaroo", "This is poem VII", 1);
            tdh.createTopic("Reach for the Top", "This is chapter VIII", 1);
            tdh.createTopic("On Killing a Tree", "This is poem VIII", 1);
            tdh.createTopic("The Bond of Love", "This is chapter IX", 1);
            tdh.createTopic("The Snake Trying", "This is poem IX", 1);
            tdh.createTopic("Kathmandu", "This is chapter X", 1);
            tdh.createTopic("A Slumber Did My Spirit Seal", "This is poem X", 1);
            tdh.createTopic("If I Were You", "This is chapter XI", 1);

            // Hindi Chapters
            tdh.createTopic("दो बैलों की कथा", "This is chapter I", 2);
            tdh.createTopic("ल्हासा की ओर", "This is chapter II", 2);
            tdh.createTopic("उपभोक्तावाद की संस्कृति", "This is chapter III", 2);
            tdh.createTopic("साँवले सपनों की याद", "This is chapter IV", 2);
            tdh.createTopic("नाना साहब की पुत्री देवी मैना को भस्म कर दिया गया", "This is chapter V", 2);
            tdh.createTopic("प्रेमचंद के फटे जूते", "This is chapter VI", 2);
            tdh.createTopic("मेरे बचपन के दिन", "This is chapter VII", 2);
            tdh.createTopic("एक कुत्ता और एक मैना", "This is chapter VIII", 2);
            tdh.createTopic("साखियाँ एवं सबद", "This is chapter IX", 2);
            tdh.createTopic("वाख", "This is chapter X", 2);
            tdh.createTopic("सवैये", "This is chapter XI", 2);
            tdh.createTopic("कैदी और कोकिला", "This is chapter XII", 2);
            tdh.createTopic("ग्राम श्री", "This is chapter XIII", 2);
            tdh.createTopic("चंद्र गहना से लौटती बेर", "This is chapter XIV", 2);
            tdh.createTopic("मेघ आए", "This is chapter XV", 2);
            tdh.createTopic("यमराज की दिशा", "This is chapter XVI", 2);
            tdh.createTopic("बच्चे काम पर जा रहे हैं", "This is chapter XVII", 2);

            // Kannada Chapters
            tdh.createTopic("Kannada Moulvi", "This is a chapter", 3);
            tdh.createTopic("Bedagina Tana Jayapura", "This is a chapter", 3);
            tdh.createTopic("Dharma Samadrusti", "This is a chapter", 3);
            tdh.createTopic("Adarsha Shikshaka Sarvepalli Radhakrishnan", "This is a chapter", 3);
            tdh.createTopic("Prajanishte", "This is a chapter", 3);
            tdh.createTopic("Janapada Kalegala Vaibhava", "This is a chapter", 3);
            tdh.createTopic("Urubhanga", "This is a chapter", 3);
            tdh.createTopic("Haralile", "This is a chapter", 3);
            tdh.createTopic("Hosa Haadu", "This is a poem", 3);
            tdh.createTopic("Parivala", "This is a poem", 3);
            tdh.createTopic("Siriya Ninnena Bannipenu", "This is a poem", 3);
            tdh.createTopic("Niyatiyanar Miridapar", "This is a poem", 3);
            tdh.createTopic("Marali Manege", "This is a poem", 3);
            tdh.createTopic("Tatva Padagalu", "This is a poem", 3);
            tdh.createTopic("Ninna Muttina Sattigeyannittu Salah", "This is a poem", 3);
            tdh.createTopic("Kannada Naadu Nudi", "This is a poem", 3);

            // Sanskrit Chapters
            tdh.createTopic("भारतीवसन्तगीतिः", "This is a chapter", 4);
            tdh.createTopic("स्वर्णकाकः", "This is a chapter", 4);
            tdh.createTopic("सोमप्रभम्", "This is a chapter", 4);
            tdh.createTopic("कल्पतरूः", "This is a chapter", 4);
            tdh.createTopic("सूक्तिमौक्तिकम्", "This is a chapter", 4);
            tdh.createTopic("भ्रान्तो बालः", "This is a chapter", 4);
            tdh.createTopic("प्रत्यभिग्यानम्", "This is a chapter", 4);
            tdh.createTopic("लौहतुला", "This is a chapter", 4);
            tdh.createTopic("सिकतासेतुः", "This is a chapter", 4);
            tdh.createTopic("जटायोः शौर्यम्", "This is a chapter", 4);
            tdh.createTopic("पर्यावरणम्", "This is a chapter", 4);
            tdh.createTopic("वाडमनःप्राणस्वरूपम्", "This is a chapter", 4);

            // Maths Chapters
            tdh.createTopic("Number System", "This is a chapter", 5);
            tdh.createTopic("Polynomials", "This is a chapter", 5);
            tdh.createTopic("Coordinate Geometry", "This is a chapter", 5);
            tdh.createTopic("Linear Equations in Two Variables", "This is a chapter", 5);
            tdh.createTopic("Introduction to Euclids Geometry", "This is a chapter", 5);
            tdh.createTopic("Lines and Angles", "This is a chapter", 5);
            tdh.createTopic("Triangles", "This is a chapter", 5);
            tdh.createTopic("Quadrilaterals", "This is a chapter", 5);
            tdh.createTopic("Areas of Parallelograms and Triangles", "This is a chapter", 5);
            tdh.createTopic("Circles", "This is a chapter", 5);
            tdh.createTopic("Constructions", "This is a chapter", 5);
            tdh.createTopic("Heron’s Formula", "This is a chapter", 5);
            tdh.createTopic("Surface Areas and Volumes", "This is a chapter", 5);
            tdh.createTopic("Statistics", "This is a chapter", 5);
            tdh.createTopic("Probability", "This is a chapter", 5);

            // Science Chapters
            tdh.createTopic("Matter in Our Surroundings", "This is Chemistry chapter I", 6);
            tdh.createTopic("Is Matter Around Us Pure", "This is Chemistry chapter II", 6);
            tdh.createTopic("Atoms and Molecules", "This is chapter XI", 6);
            tdh.createTopic("Structure of the Atom", "This is Chemistry chapter III", 6);
            tdh.createTopic("The Fundamental Unit of Life", "This is Biology chapter I", 6);
            tdh.createTopic("Tissues", "This is Biology chapter II", 6);
            tdh.createTopic("Diversity in Living Organisms", "This is Biology chapter III", 6);
            tdh.createTopic("Motion", "This is Physics chapter I", 6);
            tdh.createTopic("Force and Laws of Motion ", "This is Physics chapter II", 6);
            tdh.createTopic("Gravitation", "This is Physics chapter III", 6);
            tdh.createTopic("Work and Energy", "This is Physics chapter IV", 6);
            tdh.createTopic("Sound", "This is Physics chapter V", 6);
            tdh.createTopic("Why Do We Fall Ill", "This is Biology chapter IV", 6);
            tdh.createTopic("Natural Resources", "This is Biology chapter V", 6);

            // Social Chapters
            tdh.createTopic("The French Revolution", "This is a History Chapter", 7);
            tdh.createTopic("Socialism in Europe and the Russian Revolution", "This is a History Chapter", 7);
            tdh.createTopic("Nazism And the Rise Of Hitler", "This is a History Chapter", 7);
            tdh.createTopic("Forest Society And Colonialism", "This is a History Chapter", 7);
            tdh.createTopic("Pastoralists In The Modern World", "This is a History Chapter", 7);
            tdh.createTopic("What Is Democracy? Why Democracy?", "This is a Civics Chapter", 7);
            tdh.createTopic("Constitutional Design", "This is a Civics Chapter", 7);
            tdh.createTopic("Electoral Politics", "This is a Civics Chapter", 7);
            tdh.createTopic("Working Of Institutions", "This is a Civics Chapter", 7);
            tdh.createTopic("Democratic Rights", "This is a Civics Chapter", 7);
            tdh.createTopic("India – Size and Location", "This is a Geography Chapter", 7);
            tdh.createTopic("Physical Features of India", "This is a Geography Chapter", 7);
            tdh.createTopic("Drainage", "This is a Geography Chapter", 7);
            tdh.createTopic("Climate", "This is a Geography Chapter", 7);
            tdh.createTopic("Natural Vegetation and Wildlife", "This is a Geography Chapter", 7);
            tdh.createTopic("Population", "This is a Geography Chapter", 7);
            tdh.createTopic("The Story of Village Palampur", "This is a Economics Chapter", 7);
            tdh.createTopic("People as Resource", "This is a Economics Chapter", 7);
            tdh.createTopic("Poverty as a Challenge", "This is a Economics Chapter", 7);
            tdh.createTopic("Food Security in India", "This is a Economics Chapter", 7);
            tdh.getAllTopics();

        }
    }
}