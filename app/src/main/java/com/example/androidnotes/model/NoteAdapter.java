package com.example.androidnotes.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidnotes.NoteFragment;
import com.example.androidnotes.NotePageActivity;
import com.example.androidnotes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ItemViewHolder> {

    private List<Note> data = new ArrayList<>();

    public View.OnClickListener mOnClickListener;

    public NoteAdapter() {}

    public void setData(List<Note> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(Note item){
        data.add(0,item);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);
        view.setOnClickListener(mOnClickListener);
        return new NoteAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Note item = data.get(i);
        itemViewHolder.applyData(item);
    }



    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else return 0;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView header;
        private final TextView date;
        private final TextView isJob;
        private final TextView isFavorite;
        private final TextView isHome;
        private final TextView isPurchases;

        public ItemViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            date = itemView.findViewById(R.id.date);
            isJob = itemView.findViewById(R.id.isJob);
            isFavorite = itemView.findViewById(R.id.isFavorites);
            isHome = itemView.findViewById(R.id.isHome);
            isPurchases = itemView.findViewById(R.id.isPurchases);
        }

        public void applyData(Note item){
            header.setText(item.getHeader());
            date.setText(item.getUpdateDate());
            isJob.setText("job");
            isFavorite.setText("favorite");
            isHome.setText("home");
            isPurchases.setText("purchases");
            isJob.setVisibility(item.isJob() ? View.VISIBLE : View.GONE);
            isFavorite.setVisibility(item.isFavorites() ? View.VISIBLE : View.GONE);
            isHome.setVisibility(item.isHome() ? View.VISIBLE : View.GONE);
            isPurchases.setVisibility(item.isPurchases() ? View.VISIBLE : View.GONE);
        }
    }

}

