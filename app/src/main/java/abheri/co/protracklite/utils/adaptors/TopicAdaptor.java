package abheri.co.protracklite.utils.adaptors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import abheri.co.protracklite.R;
import abheri.co.protracklite.utils.builders.GoalDetails;
import abheri.co.protracklite.utils.builders.Subject;
import abheri.co.protracklite.utils.builders.TopicDataMap;
import abheri.co.protracklite.utils.data.SubjectDataHelper;
import abheri.co.protracklite.utils.data.Topic;
import abheri.co.protracklite.utils.data.TopicDataHelper;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;


public class TopicAdaptor extends RecyclerView.Adapter<TopicAdaptor.ViewHolder> {

    private List<Topic> topics;
    TopicMapDataHelper tmdh;
    SubjectDataHelper sdh;
    MaterialAlertDialogBuilder dialogBuilder;
    Drawable backgroundDialog;
    LayoutInflater inflater;
    Context thisContext;
    RecyclerView recyclerView;
    Button btn;
    TextView tvTopic, tvProgress, tvSubject;
    long goal_id;
    List<TopicDataMap> tdm;
    List<GoalDetails> gd;
    List<Subject> allSubjects;



    public TopicAdaptor(Context context, List<Topic> list, RecyclerView recyclerViewForDialog, long id_goal) {
        topics = list;
        thisContext = context;
        recyclerView = recyclerViewForDialog;
        tmdh = new TopicMapDataHelper(context);

        goal_id = id_goal;
        gd = tmdh.getTopicsForGoal(goal_id);

        sdh = new SubjectDataHelper(context);
        allSubjects = sdh.getAllSubjects();
        updateSubjectInfo();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvSubject, tvProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopic = itemView.findViewById(R.id.tvTopicTitle);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = recyclerView.getChildLayoutPosition(v);
                }
            });

        }
    }

    @NonNull
    @Override
    public TopicAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdaptor.ViewHolder holder, int i) {
//        int topic_id = tdm.get(i).getTopic_id();

        holder.itemView.setTag(gd.get(i));
        holder.tvTopic.setText(gd.get(i).getTopic_name());
        holder.tvSubject.setText(gd.get(i).getSubject_name());
    }

    @Override
    public int getItemCount() {
        return gd.size();
    }

    private void updateSubjectInfo(){
        for(int i=0; i<gd.size(); ++i){
            for(int j=0; j<allSubjects.size(); ++j){
                if(gd.get(i).getSubject_id() == allSubjects.get(j).getId()){
                    gd.get(i).setSubject_name(allSubjects.get(j).getName());
                    break;
                }
            }
        }
    }


}