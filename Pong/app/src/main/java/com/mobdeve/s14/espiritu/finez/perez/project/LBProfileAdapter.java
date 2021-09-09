package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

import java.util.ArrayList;

public class LBProfileAdapter extends RecyclerView.Adapter<LBProfileAdapter.ViewHolder> {
    private ArrayList<ScoreModel> localScore;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemDate;
        private TextView tvItemScore;


        public ViewHolder(View itemView) {
            super(itemView);
            tvItemDate = itemView.findViewById(R.id.tvGameItemDate);
            tvItemScore = itemView.findViewById(R.id.tvGameItemScore);
        }

        public void bindData(ScoreModel score) {
            tvItemDate.setText(score.getDateScore());
            tvItemScore.setText(Integer.toString(score.getScore()));
        }
    }

    public LBProfileAdapter(Context context, ArrayList<ScoreModel> data) {
        this.context = context;
        localScore = data;
    }

    // Creates view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace contents in the view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int pos) {
        viewHolder.bindData(localScore.get(pos));
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
