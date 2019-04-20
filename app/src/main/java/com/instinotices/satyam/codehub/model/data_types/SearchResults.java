package com.instinotices.satyam.codehub.model.data_types;

import java.util.List;

/**
 * This class is used by Retrofit for parsing JSON response received
 * after making search request.
 */
public class SearchResults {
    private List<User> items;

    public List<User> getUsersList() {
        return items;
    }
}
