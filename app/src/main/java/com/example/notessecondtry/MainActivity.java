package com.example.notessecondtry;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notessecondtry.data.CardSource;
import com.example.notessecondtry.data.OnRegisterMenu;
import com.example.notessecondtry.fragment.NoteActivity;
import com.example.notessecondtry.fragment.NotesFragment;
import com.example.notessecondtry.fragment.ShowNoteInside;
import com.example.notessecondtry.ui.MyAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        addFragment(NotesFragment.newInstance());
    }

    private void addFragment(Fragment fragment) {
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        RecyclerView recyclerView = findViewById(R.id.recycler_view_lines);
//        CardSource data = new CardsSourceImpl(getResources()).init();
//        MyAdapter myAdapter = new MyAdapter(data,this);
//        recyclerView.setAdapter(myAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        initView(recyclerView,data);
//    }
//
    private void initView(RecyclerView recyclerView, CardSource data) {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        final MyAdapter adapter = new MyAdapter(data, new OnRegisterMenu() {
            @Override
            public void onRegister(View view) {

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNoteInside(position);
            }
        });
    }
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    private  Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }
    private void showNoteInside(int index) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), NoteActivity.class);
        intent.putExtra(ShowNoteInside.ARG_INDEX, index);
        startActivity(intent);
    }
}