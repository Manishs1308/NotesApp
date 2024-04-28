package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.databinding.RecyclerviewItemBinding;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Word> mWords;
    private RecyclerviewItemBinding binding;
    private Context mContext;

    WordListAdapter(Context context) {
     mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(mInflater,R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(binding);
    }
@Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            if (!current.getTitle().isEmpty()) {
                holder.binding.tvTitle.setVisibility(View.VISIBLE);
                holder.binding.tvTitle.setText(current.getTitle());
            }else {
                holder.binding.tvTitle.setVisibility(View.GONE);
            }
            if (!current.getDate().isEmpty()) {
                holder.binding.tvDate.setVisibility(View.VISIBLE);
                holder.binding.tvDate.setText(current.getDate());
            }else {
                holder.binding.tvMsg.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(v -> {
                Word word = mWords.get(position);
                Intent intent = new Intent(mContext, UpdateNotesActivity.class);
                intent.putExtra("word",word);
                mContext.startActivity(intent);
            });
        }
    }
   void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
       ((MainActivity)mContext).totalNoOfNotes();
   }
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
    class WordViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewItemBinding binding;

        private WordViewHolder(RecyclerviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
