package abheri.co.protracklite.utils.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import abheri.co.protracklite.R;
import abheri.co.protracklite.utils.builders.Topic;
import abheri.co.protracklite.utils.data.TopicMapDataHelper;


public class ChoiceAdaptor extends RecyclerView.Adapter<ChoiceAdaptor.ViewHolder> {

    private List<Topic> topics;
    TopicMapDataHelper tmdh;
    Context thisContext;
    RecyclerView recyclerView;


    public ChoiceAdaptor(Context context, List<Topic> list, RecyclerView recyclerViewForDialog) {
        topics = list;
        thisContext = context;
        recyclerView = recyclerViewForDialog;
        tmdh = new TopicMapDataHelper(context);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvSubject, cbChoice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopic = itemView.findViewById(R.id.tvGoalDashboard);
            tvSubject = itemView.findViewById(R.id.tvChoiceSubject);
            cbChoice = itemView.findViewById(R.id.cbChoice);

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
    public ChoiceAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceAdaptor.ViewHolder holder, int i) {
//        int topic_id = tdm.get(i).getTopic_id();

        holder.itemView.setTag(topics.get(i));
        holder.tvTopic.setText(topics.get(i).getName());
        holder.tvSubject.setText(topics.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
