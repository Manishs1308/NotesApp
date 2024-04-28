package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.notesapp.databinding.ActivityNewWordBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.android.notes sql.TITLE";
    public static final String  EXTRA_DATE = "com.example.android.notes sql.DATE";
    public static final String EXTRA_MESSAGE = "com.example.android.notes sql.MESSAGE";
    private ActivityNewWordBinding binding;
    private Word word;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_word);
        binding.tvHeading.setText(getString(R.string.add_notes));
        word = (Word) getIntent().getSerializableExtra("word");
        setData();
        clicklistener();
    }
    private void setData() {
        if (word != null) {
            binding.etTitle.setText(word.getTitle());
            binding.etDate.setText(word.getDate());
            binding.etMessage.setText(word.getMessage());
        }
    }
    private void clicklistener() {
        binding.etDate.setOnClickListener(v -> datePicker());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(binding.etTitle.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String txt_title = binding.etTitle.getText().toString();
                String txt_date = binding.etDate.getText().toString();
                String txt_message = binding.etMessage.getText().toString();
                replyIntent.putExtra(EXTRA_TITLE, txt_title);
                replyIntent.putExtra(EXTRA_DATE, txt_date);
                replyIntent.putExtra(EXTRA_MESSAGE, txt_message);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
    private void datePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date1 = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etDate.setText(date1);
                    }
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        dpd.setStyle(DialogFragment.STYLE_NORMAL, R.style.datePicker);
    }

}
