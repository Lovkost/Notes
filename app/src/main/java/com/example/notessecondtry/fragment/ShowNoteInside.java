package com.example.notessecondtry.fragment;
// Написан список и внутрення часть заметок, осталось дописать обработку нажатия на элемент списка
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notessecondtry.R;
import com.example.notessecondtry.data.CardSource;
import com.example.notessecondtry.data.CardsSourceFirebaseImpl;
import com.example.notessecondtry.data.CardsSourceResponse;
import com.example.notessecondtry.data.Notes;
import com.example.notessecondtry.ui.MyAdapter;

import java.text.SimpleDateFormat;

public class ShowNoteInside extends Fragment {

    public static final String ARG_INDEX = "index";
    private int index;
    private MyAdapter adapter;
    private CardSource data;
    private CardsSourceFirebaseImpl base;

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
        Notes note = MainNotesFragment.getData().getCardData(index);
        noteF.setText(note.getTitle());
        descF.setText(note.getDescription());
        dataF.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDate()));
        return view;
    }
}