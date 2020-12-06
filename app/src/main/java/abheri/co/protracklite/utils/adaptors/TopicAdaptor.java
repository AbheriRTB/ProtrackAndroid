package abheri.co.protracklite.utils.adaptors;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;

import java.util.Calendar;
import java.util.List;

import abheri.co.protracklite.R;
import abheri.co.protracklite.utils.builders.GoalDetails;
import abheri.co.protracklite.utils.builders.Progress;
import abheri.co.protracklite.utils.builders.ProgressDetails;
import abheri.co.protracklite.utils.builders.Subject;
import abheri.co.protracklite.utils.builders.TopicDataMap;
import abheri.co.protracklite.utils.data.ProgressDataHelper;
import abheri.co.protracklite.utils.data.SubjectDataHelper;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;


public class TopicAdaptor extends RecyclerView.Adapter<TopicAdaptor.ViewHolder> {

    private List<Topic> topics;
    TopicMapDataHelper tmdh;
    SubjectDataHelper sdh;
    MaterialAlertDialogBuilder statusDialog, progressDialog;
    LayoutInflater inflater;
    Context thisContext;
    RecyclerView recyclerView;
    TextView tvTopic, tvProgressDialog, tvSubject, tvTopicDialog, tvSubjectDialog, tvDescriptionDialog;
    long goal_id;
    List<TopicDataMap> tdm;
    List<GoalDetails> gd;
    List<Subject> allSubjects;
    List<ProgressDetails> progressDetails;
    ProgressDataHelper pdh;
    List<Progress> ps;
    Slider slider;


    public TopicAdaptor(Context context, List<Topic> list, RecyclerView recyclerViewForDialog, long id_goal,
                        LayoutInflater inflater1) {
        topics = list;
        thisContext = context;
        recyclerView = recyclerViewForDialog;
        tmdh = new TopicMapDataHelper(context);
        pdh = new ProgressDataHelper(context);

        goal_id = id_goal;
        gd = tmdh.getTopicsForGoal(goal_id);
        tdm = tmdh.getAllTopicDataMaps();


        sdh = new SubjectDataHelper(context);
        allSubjects = sdh.getAllSubjects();
        updateSubjectInfo();
        ps = pdh.getAllProgresses();


        statusDialog = new MaterialAlertDialogBuilder(context);
        progressDialog = new MaterialAlertDialogBuilder(context);
        statusDialog.setBackground(context.getDrawable(R.drawable.custom_dialog));
        progressDialog.setBackground(context.getDrawable(R.drawable.custom_dialog));
        inflater = inflater1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvSubject, tvProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvTopicDialog = itemView.findViewById(R.id.tvTopicDialog);
            tvTopic = itemView.findViewById(R.id.tvGoalDashboard);
            tvSubject = itemView.findViewById(R.id.tvChoiceSubject);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            View view = inflater.inflate(R.layout.dialog_status, null);

            tvTopicDialog = view.findViewById(R.id.tvTopicDialog);
            tvSubjectDialog = view.findViewById(R.id.tvSubjectDialog);
            //tvDescriptionDialog = view.findViewById(R.id.tvDescriptionDialog);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = recyclerView.getChildLayoutPosition(v);
                    //dialogBuilder.setView(view);
                    tvTopicDialog.setText(gd.get(i).getTopic_name());
                    tvSubjectDialog.setText(gd.get(i).getSubject_name());
                    StatusDialog(i);
                }
            });


        }
    }

    @NonNull
    @Override
    public TopicAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdaptor.ViewHolder holder, int i) {
//        int topic_id = tdm.get(i).getTopic_id();

        progressDetails = pdh.getProgressesByTopicID(gd.get(i).getTopic_id());

        holder.itemView.setTag(gd.get(i));
        holder.tvTopic.setText(gd.get(i).getTopic_name());
        holder.tvSubject.setText(gd.get(i).getSubject_name());
        if(progressDetails.size() > 0)
            holder.tvProgress.setText(progressDetails.get(progressDetails.size()-1).getProgress() + "%");
        else
            holder.tvProgress.setText("0");
    }

    @Override
    public int getItemCount() {
        return gd.size();
    }

    private void updateSubjectInfo() {
        for (int i = 0; i < gd.size(); ++i) {
            for (int j = 0; j < allSubjects.size(); ++j) {
                if (gd.get(i).getSubject_id() == allSubjects.get(j).getId()) {
                    gd.get(i).setSubject_name(allSubjects.get(j).getName());
                    break;
                }
            }
        }
    }

    private void StatusDialog(int i) {
        View view = inflater.inflate(R.layout.dialog_status, null);
        statusDialog.setView(view);

        progressDetails = pdh.getProgressesByTopicID(gd.get(i).getTopic_id());

        tvTopic = view.findViewById(R.id.tvTopicDialog);
        tvSubjectDialog = view.findViewById(R.id.tvSubjectDialog);
        //tvDescriptionDialog = view.findViewById(R.id.tvDescriptionDialog);
        tvProgressDialog = view.findViewById(R.id.tvProgressDialog);
        tvTopic.setText(gd.get(i).getTopic_name());
        tvSubjectDialog.setText(gd.get(i).getSubject_name());
        //tvDescriptionDialog.setText(gd.get(i).getGoal_description());
        int progress = 0;
        if(progressDetails.size() > 0) {
            progress = progressDetails.get(progressDetails.size()-1).getProgress();
        }
        tvProgressDialog.setText(progress + "%");
        /*statusDialog.setTitle(gd.get(i).getTopic_name())
                .setMessage("This is " + gd.get(i).getTopic_name() + " of " + gd.get(i).getSubject_name() );*/
        statusDialog.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View view2 = inflater.inflate(R.layout.dialog_progress_track, null);

                slider = view2.findViewById(R.id.seekBar);

                int value = progressDetails.get(progressDetails.size() - 1).getProgress();
                slider.setValue(value);

                progressDialog.setView(view2)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int progress;
                                progress = (int) slider.getValue();
                                Calendar calendar = Calendar.getInstance();
                                String time = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/"
                                        + calendar.get(Calendar.YEAR);
                                pdh.resetProgressIsLatest(ps.get(i).getTopicdata_id());
                                pdh.createProgress(progress, time, ps.get(i).getTopicdata_id());
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        })
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null);//.show();
        statusDialog.show();
    }


}