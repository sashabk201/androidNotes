package com.example.androidnotes.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.androidnotes.NoteFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NoteRepository {

    private static final String SEPARATOR = "sepa:rator";
    private static final String TAG = "Repository";

    Context context;

    List<Note> list = new ArrayList<>();

    public NoteRepository(Context context) {
        this.context = context;
        Load();
//        Random random = new Random();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-y H:m");
//        for (int i = 0; i < 30; i++) {
//            Note note = new Note("Заголовок"+i,"Сообщение"+i,
//                    dateFormat.format(new Date()), dateFormat.format(new Date()),
//                    random.nextBoolean(),random.nextBoolean(),random.nextBoolean(),random.nextBoolean());
//            list.add(note);
//        }
    }

    public boolean save(Note note){
        if (note.getId() != 0){
           Note oldNote = parsStringData(loadFromFile(note.getId()+".txt"));
           note.setCreateDate(oldNote.getCreateDate());
        }
        if (note.getId() == 0) note.setId((int)new Date().getTime());
        String filename = note.getId()+".txt";
        FileOutputStream outputStream = null;
        try {
            String content = note.toString(SEPARATOR);
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.add(note);
        return true;
    }

    public void Load(){
        createTestNote();
        File rootFolder = context.getFilesDir();

        File[] filesArray = rootFolder.listFiles();
        if (filesArray != null){
            Log.i(TAG, "path: "+rootFolder.getPath());
            for (File f: filesArray) {
                Log.i(TAG, "File: "+f.getPath());
            }
        }
    }

    private Note parsStringData(String data){
        Note note = new Note();
        String[] parsData = data.split(SEPARATOR);
        Log.i(TAG, "parsStringData: "+parsData.length);
        if (parsData.length != 9){
            throw new NullPointerException();
        }
        note.setId(Integer.parseInt(parsData[0]));
        note.setHeader(parsData[1]);
        note.setMessage(parsData[2]);
        note.setCreateDate(parsData[3]);
        note.setUpdateDate(parsData[4]);
        note.setJob(Boolean.parseBoolean(parsData[5]));
        note.setPurchases(Boolean.parseBoolean(parsData[6]));
        note.setHome(Boolean.parseBoolean(parsData[7]));
        note.setFavorites(Boolean.parseBoolean(parsData[8]));
        return note;
    }

    private String loadFromFile(String fileName) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                inputStream.close();
                Log.i(TAG, "openFile: "+builder.toString());
                return builder.toString();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            Toast.makeText(context,
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void createTestNote(){
        File rootFolder = context.getFilesDir();
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-y H:m");
        for (int i = 0; i < 3; i++) {
            Note note = new Note("Заголовок"+i,"Сообщение"+i,
                    dateFormat.format(new Date()), dateFormat.format(new Date()),
                    random.nextBoolean(),random.nextBoolean(),random.nextBoolean(),random.nextBoolean());
            save(note);

        }

    }

//  GET LIST

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
