package com.instinotices.satyam.codehub.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.instinotices.satyam.codehub.model.data_types.User;
import com.instinotices.satyam.codehub.model.repositories.GithubRepository;

public class UsersListViewModel extends AndroidViewModel {
    private String mUserName, mSearchQuery;
    private LiveData<PagedList<User>> liveData;

    public UsersListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
        // Create an instance of Github repository (in followers mode)
        GithubRepository githubRepository = new GithubRepository(GithubRepository.MODE_FOLLOWERS);
        // Share the user's login with repository
        githubRepository.setUserName(mUserName);
        // Get back an instance of live data in which updated list of followers will be posted.
        liveData = githubRepository.getPagedListLiveData();
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
        // Create an instance of Github repository (in search mode)
        GithubRepository githubRepository = new GithubRepository(GithubRepository.MODE_SEARCH);
        // Share the search query with repository
        githubRepository.setSearchQuery(mSearchQuery);
        // Get back an instance of live data in which updates to search results will be posted
        liveData = githubRepository.getPagedListLiveData();
    }

    public LiveData<PagedList<User>> getLiveData() {
        return liveData;
    }
}
