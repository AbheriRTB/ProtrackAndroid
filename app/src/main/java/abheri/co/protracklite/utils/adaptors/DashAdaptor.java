package abheri.co.protracklite.utils.adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import abheri.co.protracklite.R;
import abheri.co.protracklite.TopicActivity;
import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.builders.Progress;
import abheri.co.protracklite.utils.builders.ProgressGoalDetails;
import abheri.co.protracklite.utils.data.ProgressDataHelper;


public class DashAdaptor extends RecyclerView.Adapter<DashAdaptor.ViewHolder> {

    private List<Goal> goal;
    int totalProgress;
    MaterialAlertDialogBuilder dialogBuilder;
    Drawable backgroundDialog;
    ProgressDataHelper pdh;
    LayoutInflater inflater;
    Context thisContext;
    RecyclerView recyclerView;
    Button btn;
    Intent intent;
    List<Progress> ps;
    List<ProgressGoalDetails> pgd;


    public DashAdaptor(Context context, List<Goal> list, RecyclerView recyclerViewOnClick) {
        goal = list;
        thisContext = context;
        recyclerView = recyclerViewOnClick;
        pdh = new ProgressDataHelper(context);
        ps = pdh.getAllProgresses();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGoal, tvProgress, tvDescription, tvEndDate;
        ProgressBar pbProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGoal = itemView.findViewById(R.id.tvGoalDashboard);
            tvProgress = itemView.findViewById(R.id.tvProgressDash);
            tvDescription = itemView.findViewById(R.id.tvDescriptionDash);
            tvEndDate = itemView.findViewById(R.id.tvEndDateDash);
            pbProgress = itemView.findViewById(R.id.pbProgressDash);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = recyclerView.getChildLayoutPosition(v);
                    intent = new Intent(v.getContext(), TopicActivity.class);
                    intent.putExtra("goal_id", goal.get(i).getId());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    @NonNull
    @Override
    public DashAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DashAdaptor.ViewHolder holder, int i) {
        totalProgress = getProgress(goal.get(i).getId());
        holder.itemView.setTag(goal.get(i));
        holder.tvGoal.setText(goal.get(i).getName());
        holder.tvDescription.setText(goal.get(i).getDescription());
        //holder.tvEndDate.setText(goal.get(i).getDate());
        holder.tvProgress.setText(totalProgress+"%");
        holder.pbProgress.setProgress(totalProgress);

    }

    @Override
    public int getItemCount() {
        return goal.size();
    }

    private int getProgress(long goalID) {
        pgd = pdh.getProgressesByGoal(goalID);
        int prog = 0;
        for (int i = 0; i < pgd.size(); ++i) {
            prog += pgd.get(i).getProgress();
        }
        return (prog/pgd.size());
    }
}