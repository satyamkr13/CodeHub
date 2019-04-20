package com.instinotices.satyam.codehub;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubAPI {

    @GET("users")
    Call<List<User>> getAllUsers(
            @Query("since") Integer since,
            @Query("per_page") int perPage
    );

    @GET("users/{user_name}")
    Call<User> getUser(
            @Path("user_name") String userName
    );

    @GET("users/{user_name}/followers")
    Call<List<User>> getFollowers(
            @Path("user_name") String userName,
            @Query("page") int pageNumber
    );
}
