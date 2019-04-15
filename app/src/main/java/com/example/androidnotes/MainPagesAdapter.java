package com.example.androidnotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {

    public static final String TAB_NAME_ALL = "Все";
    public static final String TAB_NAME_FAVORITE = "Важное";
    public static final String TAB_NAME_JOB = "Работа";
    public static final String TAB_NAME_HOME = "Дом";
    public static final String TAB_NAME_PURCHASES = "Покупки";

    public MainPagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        switch (i){
            case 0:  bundle.putString(NoteFragment.TYPE_KEY,NoteFragment.TYPE_ALL);break;
            case 1:  bundle.putString(NoteFragment.TYPE_KEY,NoteFragment.TYPE_FAVORITE);break;
            case 2:  bundle.putString(NoteFragment.TYPE_KEY,NoteFragment.TYPE_PURCHASES);break;
            case 3:  bundle.putString(NoteFragment.TYPE_KEY,NoteFragment.TYPE_HOME);break;
            case 4:  bundle.putString(NoteFragment.TYPE_KEY,NoteFragment.TYPE_JOB);break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return TAB_NAME_ALL;
            case 1: return TAB_NAME_FAVORITE;
            case 2: return TAB_NAME_PURCHASES;
            case 3: return TAB_NAME_HOME;
            case 4: return TAB_NAME_JOB;
        }

        return null;
    }


    @Override
    public int getCount() {
        return 5;
    }
}
