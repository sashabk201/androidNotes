package com.example.androidnotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidnotes.model.Note;
import com.example.androidnotes.model.NoteAdapter;
import com.example.androidnotes.model.NoteRepository;

import java.util.List;

public class NoteFragment extends Fragment {


    public final static String TYPE_LOAD = "LOAD";
    public final static String TYPE_ALL = "ALL";
    public final static String TYPE_JOB = "JOB";
    public final static String TYPE_FAVORITE = "FAVORITE";
    public final static String TYPE_HOME = "HOME";
    public final static String TYPE_PURCHASES = "PURCHASES";
    public final static String TYPE_KEY = "type";

    public final static String NOTE_KEY = "note";

    public final static int ADD_ITEM_REQUEST_CODE = 1;
    public final static int EDIT_ITEM_REQUEST_CODE = 2;
    private String type = TYPE_ALL;

    private NoteRepository repository;
    private NoteAdapter adapter;
    private RecyclerView recycleView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout refresh;
    private String TAG = "NOTEFRAGMENT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new NoteRepository(this.getContext());
        adapter = new NoteAdapter();
        adapter.mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recycleView.getChildLayoutPosition(view);
                Intent intent = new Intent(getContext(),NotePageActivity.class);
                intent.putExtra(NOTE_KEY,repository.get(position));
                startActivityForResult(intent,EDIT_ITEM_REQUEST_CODE);
            }
        };
        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY,NoteFragment.TYPE_ALL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView = view.findViewById(R.id.list);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycleView.setAdapter(adapter);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NotePageActivity.class);
                startActivityForResult(intent,ADD_ITEM_REQUEST_CODE);
            }
        });

        refresh = view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.GREEN,Color.RED,Color.BLUE);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });

        setData();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Note item =  data.getParcelableExtra("item");
            Log.i(TAG, "onActivityResult: "+item);
            repository.save(item);
            adapter.addItem(item);
            repository.logTest();
        }

        if (requestCode == EDIT_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Note item =  data.getParcelableExtra("item");
            Log.i(TAG, "onActivityResult: "+item);
            repository.save(item);
            repository.logTest();
            setData();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("StaticFieldLeak")
    private void setData() {
        new AsyncTask<Void,Void, List<Note>>(){
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return repository.get(type);
            }

            @Override
            protected void onCancelled() {
                refresh.setRefreshing(false);
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                adapter.setData(notes);
                refresh.setRefreshing(false);
            }
        }.execute();
    }

}
