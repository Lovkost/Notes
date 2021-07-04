package com.example.notessecondtry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notessecondtry.fragment.MainNotesFragment;
import com.example.notessecondtry.fragment.ShowNoteInside;
import com.example.notessecondtry.observe.Publisher;


public class MainActivity extends AppCompatActivity {
    private Navigation navigation;
    private Publisher publisher = new Publisher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        getNavigation().addFragment(MainNotesFragment.newInstance(), false);
        }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}