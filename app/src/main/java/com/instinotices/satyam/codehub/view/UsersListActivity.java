package com.instinotices.satyam.codehub.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.instinotices.satyam.codehub.R;
import com.instinotices.satyam.codehub.model.data_types.User;
import com.instinotices.satyam.codehub.viewmodel.UsersListViewModel;

public class UsersListActivity extends AppCompatActivity implements UsersAdapter.InteractionCallbacks {
    private UsersAdapter mAdapter;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProgressView = findViewById(R.id.progressView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting up recyclerView
        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);
        mAdapter = new UsersAdapter(this, Glide.with(this));
        recyclerView.setAdapter(mAdapter);

        // Setting up UsersViewModel
        UsersListViewModel viewModel = ViewModelProviders.of(this).get(UsersListViewModel.class);
        if (getIntent().hasExtra(MainActivity.INTENT_EXTRA_USER_NAME)) {
            // Inform view-model to use this as userName whose followers are to be fetched.
            viewModel.setUserName(getIntent().getStringExtra(MainActivity.INTENT_EXTRA_USER_NAME));
        } else if (getIntent().hasExtra(MainActivity.INTENT_EXTRA_SEARCH_QUERY)) {
            // Extract search query from intent
            String query = getIntent().getStringExtra(MainActivity.INTENT_EXTRA_SEARCH_QUERY);
            // Make query as Screen Title
            getSupportActionBar().setTitle(query);
            // Inform view-model to use this search query
            viewModel.setSearchQuery(query);
        }
        viewModel.getLiveData().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                // Update the users being display when liveData changes
                mAdapter.submitList(users);
                // Hides the progress bar when data is received first time.
                mProgressView.setVisibility(View.GONE);
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
        // Open the details page
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MainActivity.INTENT_EXTRA_USER_NAME, user.getLogin());
        startActivity(intent);
    }
}
