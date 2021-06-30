package com.example.notessecondtry.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notessecondtry.MainActivity;
import com.example.notessecondtry.R;
import com.example.notessecondtry.data.CardSource;
import com.example.notessecondtry.data.CardsSourceImpl;
import com.example.notessecondtry.data.Notes;
import com.example.notessecondtry.ui.MyAdapter;

public class MainNotesFragment extends Fragment {
    private CardSource data;
    private MyAdapter adapter;
    private RecyclerView recyclerView;

    public static MainNotesFragment newInstance() {
        return new MainNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_notes, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        data = new CardsSourceImpl(getResources()).init();
        initView(view);
        setHasOptionsMenu(true);
        initToolbar(view);
        initDrawer(toolbar,view);
        return view;
    }
    private void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.redact_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                data.addCardData(new Notes("Заголовок " + data.size(),
                        "Описание " + data.size(),
                        R.drawable.chili));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.action_clear:
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

    private void initRecyclerView(){

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(data,this);
        recyclerView.setAdapter(adapter);

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
    private void initDrawer(Toolbar toolbar,View v) {
        final DrawerLayout drawer = v.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_update:

                return true;
            case R.id.action_delete:
                //Пока что удаляется последний элемент
                data.deleteCardData(data.size()-1);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
