package abheri.co.protracklite.utils;

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

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.R;
import abheri.co.protracklite.TopicActivity;


public class TopicAdaptor extends RecyclerView.Adapter<TopicAdaptor.ViewHolder> {

    private List<Topic> topics;
    TopicMapDataHelper tmdh;
    MaterialAlertDialogBuilder dialogBuilder;
    Drawable backgroundDialog;
    LayoutInflater inflater;
    Context thisContext;
    RecyclerView recyclerView;
    Button btn;
    TextView tvTopic, tvProgress, tvSubject;


    public TopicAdaptor(Context context, List<Topic> list, RecyclerView recyclerViewForDialog) {
        topics = list;
        thisContext = context;
        recyclerView = recyclerViewForDialog;
        tmdh = new TopicMapDataHelper(context);

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
        holder.itemView.setTag(topics.get(i));
        holder.tvTopic.setText(topics.get(i).getName());
        holder.tvTopic.setText(topics.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }



}