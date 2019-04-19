package com.instinotices.satyam.codehub;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements UsersAdapter.InteractionCallbacks {
    UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting up recyclerView
        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);
        mAdapter = new UsersAdapter(this, Glide.with(this));
        recyclerView.setAdapter(mAdapter);

        // Setting up UsersViewModel
        UsersViewModel usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        usersViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                // Submit a new list to paged list adapter.
                mAdapter.submitList(users);
            }
        });
    }

    @Override
    public void onUserCardClick(User user) {

    }
}
