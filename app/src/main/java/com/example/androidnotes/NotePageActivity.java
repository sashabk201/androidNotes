package com.example.androidnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.androidnotes.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotePageActivity extends AppCompatActivity {

    EditText header;
    EditText message;
    CheckBox job;
    CheckBox favorite;
    CheckBox home;
    CheckBox purchases;
    TextWatcher textWatcher;
    Button addBtn;
    private String TAG = "NotePageActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);

        header = findViewById(R.id.header);
        message = findViewById(R.id.message);
        job = findViewById(R.id.job);
        favorite = findViewById(R.id.favorite);
        home = findViewById(R.id.home);
        purchases = findViewById(R.id.purchases);
        addBtn = findViewById(R.id.add_btn);

        textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.hashCode() == header.getText().hashCode()){
                    addBtn.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(message.getText()));
                }
                if (s.hashCode() == message.getText().hashCode())
                    addBtn.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(header.getText()));

            }};

        header.addTextChangedListener(textWatcher);
        message.addTextChangedListener(textWatcher);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-y H:m");
                Note item = new Note(header.getText().toString(),message.getText().toString(),
                        dateFormat.format(new Date()), dateFormat.format(new Date()),
                        job.isChecked(),purchases.isChecked(),home.isChecked(),favorite.isChecked());

                Log.i(TAG, "onClick: "+item.toString());
                Intent intent = new Intent();
                intent.putExtra("item",item);

                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
