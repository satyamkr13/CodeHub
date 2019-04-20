package com.instinotices.satyam.codehub.model.data_sources;

import android.arch.paging.DataSource;
import android.util.Log;

public class GithubDataSourceFactory extends DataSource.Factory {
    DataSource githubDataSource;

    public GithubDataSourceFactory(String userName) {
        githubDataSource = new FollowersDataSource(userName);
    }

    public GithubDataSourceFactory() {
        githubDataSource = new AllUsersDataSource();
    }

    public GithubDataSourceFactory(String mode, String query) {
        Log.e("DATA", "Here");
        githubDataSource = new SearchDataSource(query);
    }

    @Override
    public DataSource create() {
        // Return an instance of AllUsersDataSource
        return githubDataSource;
    }
}
