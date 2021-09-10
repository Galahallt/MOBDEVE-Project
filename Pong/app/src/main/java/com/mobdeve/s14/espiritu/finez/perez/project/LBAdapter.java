package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

import java.util.ArrayList;

public class LBAdapter extends RecyclerView.Adapter<LBAdapter.ViewHolder> {
    private ArrayList<ScoreModel> localScore;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemRank;
        private TextView tvItemUser;
        private TextView tvItemScore;

        private ConstraintLayout clItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemRank = itemView.findViewById(R.id.tvItemRank);
            tvItemUser = itemView.findViewById(R.id.tvItemUsername);
            tvItemScore = itemView.findViewById(R.id.tvItemScore);

            clItem = itemView.findViewById(R.id.clItem);
            clItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView vUser = v.findViewById(R.id.tvItemUsername);
                    String username = String.valueOf(vUser.getText());
                    Context context = v.getContext();

                    Intent i = new Intent(context, LBProfile.class);
                    i.putExtra("VISITING_USER", username);

                    context.startActivity(i);
                }
            });
        }

        public void bindData(ScoreModel score, int rank) {
            tvItemRank.setText(Integer.toString(rank+1));
            tvItemUser.setText(score.getUsername());
            tvItemScore.setText(Integer.toString(score.getScore()));
        }
    }

    public LBAdapter(Context context, ArrayList<ScoreModel> data) {
        this.context = context;
        localScore = data;
    }

    // Creates view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leaderboards_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace contents in the view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int pos) {
        viewHolder.bindData(localScore.get(pos), pos);
    }

    // Return size of dataset
    @Override
    public int getItemCount() {
        return localScore.size();
    }

    public void setData(ArrayList<ScoreModel> localScore) {
        this.localScore.clear();
        this.localScore.addAll(localScore);
        notifyDataSetChanged();
    }
}
