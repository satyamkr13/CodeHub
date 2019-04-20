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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.instinotices.satyam.codehub.R;
import com.instinotices.satyam.codehub.model.data_types.User;
import com.instinotices.satyam.codehub.viewmodel.UsersViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity implements UsersAdapter.InteractionCallbacks {
    public static final String INTENT_EXTRA_USER_NAME = "INTENT_EXTRA_USER_NAME";
    public static final String INTENT_EXTRA_SEARCH_QUERY = "INTENT_EXTRA_SEARCH_QUERY";
    private UsersAdapter mAdapter;
    private MaterialSearchView searchView;
    private View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressView = findViewById(R.id.progressView);
        setUpSearchBar();

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
                // Hide the loading animation
                progressView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onUserCardClick(User user) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(INTENT_EXTRA_USER_NAME, user.getLogin());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void setUpSearchBar() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                intent.putExtra(INTENT_EXTRA_SEARCH_QUERY, query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
