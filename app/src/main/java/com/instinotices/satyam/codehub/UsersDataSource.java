package com.instinotices.satyam.codehub;

import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersDataSource extends ItemKeyedDataSource<Integer, User> {
    public final static int PAGE_SIZE = 50;
    public static final String BASE_URL = "https://api.github.com/";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {
        // Making a synchronous retrofit request to fetch initial users
        try {
            Response<List<User>> userResponse= gitHubService().getAllUsers(1, PAGE_SIZE).execute();
            // Send the result when fetch finished.
            callback.onResult(userResponse.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        // Making a synchronous retrofit request to fetch initial users
        try {
            Response<List<User>> userResponse= gitHubService().getAllUsers(params.key, PAGE_SIZE).execute();
            // Send the result when fetch finished.
            callback.onResult(userResponse.body());
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

    /**
     *
     * @return An instance of GitHubAPI interface whose methods are automatically
     * implemented by Retrofit library.
     */
    private static GitHubAPI gitHubService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GitHubAPI.class);
    }
}
