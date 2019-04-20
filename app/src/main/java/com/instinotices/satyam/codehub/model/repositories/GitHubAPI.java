package com.instinotices.satyam.codehub.model.repositories;

import com.instinotices.satyam.codehub.model.data_types.SearchResults;
import com.instinotices.satyam.codehub.model.data_types.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface informs Retrofit about how to construct requests.
 */
public interface GitHubAPI {

    /**
     * @param since   is the id of last user received till now.
     *                It is the only way of supporting pagination in Github API
     * @param perPage is the number of users to be returned in each request.
     */
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

    @GET("search/users")
    Call<SearchResults> getUsersBySearch(
            @Query("q") String query,
            @Query("page") int pageNumber
    );
}
