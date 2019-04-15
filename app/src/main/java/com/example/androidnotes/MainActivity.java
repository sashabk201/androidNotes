package com.example.androidnotes;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.androidnotes.model.Note;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY: ";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        MainPagesAdapter adapter = new MainPagesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Note note = new Note("Заголовок","Сообщение","2018-04-54",
                "2019-04-12",true,true,false,false);

        Log.i(TAG, "onCreate: "+note.toString());
    }
}
