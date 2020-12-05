package abheri.co.protracklite.utils.adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class GoalAdaptor extends RecyclerView.Adapter<GoalAdaptor.ViewHolder> {

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


    public GoalAdaptor(Context context, List<Goal> list, RecyclerView recyclerViewOnClick) {
        goal = list;
        thisContext = context;
        recyclerView = recyclerViewOnClick;
        pdh = new ProgressDataHelper(context);
        ps = pdh.getAllProgresses();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvChoiceTitle);
            tvProgress = itemView.findViewById(R.id.tvProgressGoal);
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
    public GoalAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalAdaptor.ViewHolder holder, int i) {
        getProgress(goal.get(i).getId());
        holder.itemView.setTag(goal.get(i));
        holder.tvTitle.setText(goal.get(i).getName());
        holder.tvProgress.setText(totalProgress+"%");

    }

    @Override
    public int getItemCount() {
        return goal.size();
    }

    private void getProgress(long goalID) {
        pgd = pdh.getProgressesByGoal(goalID);
        int prog = 0;
        for (int i = 0; i < pgd.size(); ++i) {
            prog = prog + pgd.get(i).getProgress();
        }
        totalProgress = prog/pgd.size();
    }
}