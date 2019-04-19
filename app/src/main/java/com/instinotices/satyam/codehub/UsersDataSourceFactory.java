package com.instinotices.satyam.codehub;
import android.arch.paging.DataSource;

public class UsersDataSourceFactory extends DataSource.Factory {
    @Override
    public DataSource create() {
        // Return an instance of UsersDataSource
        return new UsersDataSource();
    }

}
