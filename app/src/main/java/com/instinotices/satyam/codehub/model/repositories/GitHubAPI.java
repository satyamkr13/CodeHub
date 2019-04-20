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
     * Get all users registered on Github.
     * @param since is the id of last user received till now.
     *                It is the only way of supporting pagination in Github Users API
     * @param perPage is the number of users to be returned in each request.
     */
    @GET("users")
    Call<List<User>> getAllUsers(
            @Query("since") Integer since,
            @Query("per_page") int perPage
    );

    /**
     * Retrieve full information about a user.
     *
     * @param userName is the login id of user whose details are to be fetched
     */
    @GET("users/{user_name}")
    Call<User> getUser(
            @Path("user_name") String userName
    );

    /**
     * Retrieve a list of followers of a particular user.
     * @param userName is the login id of user whose followers are to be fetched
     * @param pageNumber is required for pagination if there are many followers.
     */
    @GET("users/{user_name}/followers")
    Call<List<User>> getFollowers(
            @Path("user_name") String userName,
            @Query("page") int pageNumber
    );

    /**
     * Get a list of users based on search query.
     * @param query is the word that is to be queried
     * @param pageNumber is required for pagination if there are many results.
     */
    @GET("search/users")
    Call<SearchResults> getUsersBySearch(
            @Query("q") String query,
            @Query("page") int pageNumber
    );
}
