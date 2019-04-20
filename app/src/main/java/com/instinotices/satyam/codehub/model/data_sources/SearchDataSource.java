package com.instinotices.satyam.codehub.model.data_sources;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.instinotices.satyam.codehub.model.data_types.SearchResults;
import com.instinotices.satyam.codehub.model.data_types.User;

import java.io.IOException;

import retrofit2.Response;

import static com.instinotices.satyam.codehub.model.data_sources.AllUsersDataSource.gitHubService;

/**
 * This class acts as a DataSource for providing search results based on keywords entered
 */
public class SearchDataSource extends PageKeyedDataSource<Integer, User> {
    String query;

    public SearchDataSource(String query) {
        this.query = query;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, User> callback) {
        try {
            // Making a synchronous retrofit request to fetch initial followers
            Response<SearchResults> userResponse = gitHubService().getUsersBySearch(query, 1).execute();
            // Send the result when fetch finished.
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body().getUsersList(), null, 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {
        try {
            // Making a synchronous retrofit request to fetch initial followers
            Response<SearchResults> userResponse = gitHubService().getUsersBySearch(query, params.key).execute();
            // Send the result when fetch finished.
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body().getUsersList(), params.key + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
