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

public class FollowersActivity extends AppCompatActivity implements UsersAdapter.InteractionCallbacks {
    UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting up recyclerView
        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);
        mAdapter = new UsersAdapter(this, Glide.with(this));
        recyclerView.setAdapter(mAdapter);

        // Setting up UsersViewModel
        FollowersViewModel viewModel = ViewModelProviders.of(this).get(FollowersViewModel.class);
        viewModel.setUserName(getIntent().getStringExtra(MainActivity.USER_NAME));
        viewModel.getFollowersListLiveData().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                mAdapter.submitList(users);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Go back when Back button pressed in ActionBar
        onBackPressed();
        return true;
    }

    @Override
    public void onUserCardClick(User user) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MainActivity.USER_NAME, user.getLogin());
        startActivity(intent);
    }
}
