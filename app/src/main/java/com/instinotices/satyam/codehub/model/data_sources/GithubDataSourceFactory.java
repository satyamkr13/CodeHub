package com.instinotices.satyam.codehub.model.data_sources;

import android.arch.paging.DataSource;

/**
 * This class is used for creating data source.
 * It automatically decides which Data source to use based
 * on choice of constructor.
 */
public class GithubDataSourceFactory extends DataSource.Factory {
    DataSource githubDataSource;

    public GithubDataSourceFactory(String userName) {
        githubDataSource = new FollowersDataSource(userName);
    }

    public GithubDataSourceFactory() {
        githubDataSource = new AllUsersDataSource();
    }

    public GithubDataSourceFactory(String mode, String query) {
        githubDataSource = new SearchDataSource(query);
    }

    @Override
    public DataSource create() {
        // Return an instance of AllUsersDataSource
        return githubDataSource;
    }
}
