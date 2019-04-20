package com.instinotices.satyam.codehub;

import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubUsersDataSource extends ItemKeyedDataSource<Integer, User> {
    public final static int PAGE_SIZE = 50;
    public static final String BASE_URL = "https://api.github.com/";

    /**
     * @return An instance of GitHubAPI interface whose methods are automatically
     * implemented by Retrofit library.
     */
    public static GitHubAPI gitHubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GitHubAPI.class);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {

        try {
            // Making a synchronous retrofit request to fetch initial users
            Response<List<User>> userResponse = gitHubService().getAllUsers(1, PAGE_SIZE).execute();
            // Send the result when fetch finished.
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body());
            } else {
                Log.e("sfdsdfsd", userResponse.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        // Return unique key i.e. ID in GitHub API
        return item.getId();
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        try {
            // Making a synchronous retrofit request to fetch initial users
            Response<List<User>> userResponse = gitHubService().getAllUsers(params.key, PAGE_SIZE).execute();
            // Send the result when fetch finished.
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
