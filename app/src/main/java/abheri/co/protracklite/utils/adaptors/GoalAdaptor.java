package abheri.co.protracklite.utils.adaptors;

import android.content.Context;
import android.content.Intent;
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
import abheri.co.protracklite.TopicActivity;
import abheri.co.protracklite.utils.builders.Goal;


public class GoalAdaptor extends RecyclerView.Adapter<GoalAdaptor.ViewHolder> {

    private List<Goal> goal;
    MaterialAlertDialogBuilder dialogBuilder;
    Drawable backgroundDialog;
    LayoutInflater inflater;
    Context thisContext;
    RecyclerView recyclerView;
    Button btn;
    Intent intent;


    public GoalAdaptor(Context context, List<Goal> list, RecyclerView recyclerViewOnClick) {
        goal = list;
        thisContext = context;
        recyclerView = recyclerViewOnClick;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvGoalTitle);
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
        holder.itemView.setTag(goal.get(i));
        holder.tvTitle.setText(goal.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return goal.size();
    }


}