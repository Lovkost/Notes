package com.example.notessecondtry.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notessecondtry.R;

public class ShowNoteInside extends Fragment {

    public static final String ARG_INDEX = "index";
    private int index;

    public static ShowNoteInside newInstance(int index) {
        ShowNoteInside f = new ShowNoteInside();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_note_inside, container, false);
        TextView noteF = view.findViewById(R.id.notes);
        TextView descF = view.findViewById(R.id.description);
        TextView dataF = view.findViewById(R.id.date);
        String[] noteArr = getResources().getStringArray(R.array.noteTitles);
        String[] descArr = getResources().getStringArray(R.array.description);
        String[] dataArr = getResources().getStringArray(R.array.data);
        noteF.setText(noteArr[index]);
        descF.setText(descArr[index]);
        dataF.setText(dataArr[index]);
        return view;
    }
}