package com.example.androidnotes.model;

import com.example.androidnotes.NoteFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NoteRepository {

    List<Note> list = new ArrayList<>();

    public NoteRepository() {
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-y H:m");
        for (int i = 0; i < 30; i++) {
            Note note = new Note("Заголовок"+i,"Сообщение"+i,
                    dateFormat.format(new Date()), dateFormat.format(new Date()),
                    random.nextBoolean(),random.nextBoolean(),random.nextBoolean(),random.nextBoolean());
            list.add(note);
        }
    }

    public List<Note> get(String type){
        List<Note> result = list;
        switch (type)
        {
            case NoteFragment.TYPE_ALL: break;
            case NoteFragment.TYPE_JOB: result = getJobsNote(); break;
            case NoteFragment.TYPE_FAVORITE: result = getFavoriteNote(); break;
            case NoteFragment.TYPE_HOME: result = getHomeNote(); break;
            case NoteFragment.TYPE_PURCHASES: result = getPurchasesNote(); break;
        }
        if (result.size() > 0) return result;
        return null;
    }

    private List<Note> getJobsNote(){
        List<Note> result = new ArrayList<>();
        for (Note item: list){
            if (item.isJob()) result.add(item);
        }
        return result;
    }

    private List<Note> getFavoriteNote(){
        List<Note> result = new ArrayList<>();
        for (Note item: list){
            if (item.isFavorites()) result.add(item);
        }
        return result;
    }

    private List<Note> getHomeNote(){
        List<Note> result = new ArrayList<>();
        for (Note item: list){
            if (item.isHome()) result.add(item);
        }
        return result;
    }

    private List<Note> getPurchasesNote(){
        List<Note> result = new ArrayList<>();
        for (Note item: list){
            if (item.isPurchases()) result.add(item);
        }
        return result;
    }

}
