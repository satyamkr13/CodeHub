package com.instinotices.satyam.codehub;
import android.arch.paging.DataSource;

public class GithubDataSourceFactory extends DataSource.Factory {
    DataSource githubDataSource;

    public GithubDataSourceFactory(String userName) {
        githubDataSource = new FollowersDataSource(userName);
    }

    public GithubDataSourceFactory() {
        githubDataSource = new GithubUsersDataSource();
    }

    @Override
    public DataSource create() {
        // Return an instance of GithubUsersDataSource
        return githubDataSource;
    }
}
