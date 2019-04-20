package com.instinotices.satyam.codehub.model.data_sources;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.instinotices.satyam.codehub.model.data_types.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

import static com.instinotices.satyam.codehub.model.data_sources.AllUsersDataSource.gitHubService;


/**
 * This class acts as a DataSource for providing list of followers of a particular user
 */
public class FollowersDataSource extends PageKeyedDataSource<Integer, User> {
    String userName;

    public FollowersDataSource(String userName) {
        this.userName = userName;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, User> callback) {
        try {
            // Making a synchronous retrofit request to fetch initial followers
            Response<List<User>> userResponse = gitHubService().getFollowers(userName, 1).execute();
            // Send the result when fetch finished.
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body(), null, 2);
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
            Response<List<User>> userResponse = gitHubService().getFollowers(userName, params.key).execute();
            // Send the result when fetch finished.
            callback.onResult(userResponse.body(), params.key + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
