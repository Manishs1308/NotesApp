package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    private ActivityMainBinding binding;
    private WordListAdapter adapter;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        setRecyclerView();
        loadData();
        clickListener();
        totalNoOfNotes();
    }
    private void clickListener() {
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }
    private void setRecyclerView() {
        binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        adapter = new WordListAdapter(this);
        binding.recyclerview.setAdapter(adapter);
    }

    private void loadData() {
        mWordViewModel.getAllWords().observe(this, adapter::setWords);
    }

    public void totalNoOfNotes() {
        if (mWordViewModel.getAllWords().getValue() != null && !mWordViewModel.getAllWords().getValue().isEmpty()) {
            binding.emptyScreen.setVisibility(View.GONE);
            binding.recyclerview.setVisibility(View.VISIBLE);
            binding.tvTotalNotes.setText(mWordViewModel.getAllWords().getValue().size() == 1 ? String.valueOf(mWordViewModel.getAllWords().getValue().size()).concat(" note") : String.valueOf(mWordViewModel.getAllWords().getValue().size()).concat(" notes"));
        } else {
            binding.recyclerview.setVisibility(View.GONE);
            binding.emptyScreen.setVisibility(View.VISIBLE);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NewWordActivity.EXTRA_TITLE);
            String date = data.getStringExtra(NewWordActivity.EXTRA_DATE);
            String message = data.getStringExtra(NewWordActivity.EXTRA_MESSAGE);
            Word word = new Word(title, date, message);
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

}