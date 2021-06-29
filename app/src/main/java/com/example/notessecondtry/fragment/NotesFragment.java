package com.example.notessecondtry.fragment;//package com.example.notessecondtry.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notessecondtry.R;
import com.example.notessecondtry.data.CardSource;
import com.example.notessecondtry.data.CardsSourceImpl;
import com.example.notessecondtry.data.Notes;
import com.example.notessecondtry.data.OnRegisterMenu;
import com.example.notessecondtry.fragment.NoteActivity;
import com.example.notessecondtry.fragment.ShowNoteInside;
import com.example.notessecondtry.ui.MyAdapter;

public class NotesFragment extends Fragment implements OnRegisterMenu {

    private CardSource data;
    private MyAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        data = new CardsSourceImpl(getResources()).init();
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.redact_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                data.addCardData(new Notes("Заголовок " + data.size(),
                        "Описание " + data.size(),
                        R.drawable.chili));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        data = new CardsSourceImpl(getResources()).init();
        initRecyclerView();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_redact, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();

        switch (item.getItemId()) {
            case R.id.update:
                data.updateCardData(position,
                        new Notes("Кадр " + position,
                                data.getCardData(position).getDescription(),
                                data.getCardData(position).getPicture()));
                adapter.notifyItemChanged(position);
                return true;
            case R.id.delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);

                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter(data,this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.SetOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNoteInside(position);
            }

        });
    }
    private void showNoteInside(int index) {
        Intent intent = new Intent();
        intent.setClass(getContext(), NoteActivity.class);
        intent.putExtra(ShowNoteInside.ARG_INDEX, index);
        startActivity(intent);
    }

    @Override
    public void onRegister(View view) {
        registerForContextMenu(view);
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }
}
