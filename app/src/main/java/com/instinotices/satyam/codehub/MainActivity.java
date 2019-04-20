package com.instinotices.satyam.codehub;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements UsersAdapter.InteractionCallbacks {
    public static final String USER_NAME = "USER_NAME";
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
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(USER_NAME, user.getLogin());
        startActivity(intent);
    }
}