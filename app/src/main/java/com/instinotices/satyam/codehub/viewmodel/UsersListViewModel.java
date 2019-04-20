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
        GithubRepository githubRepository = new GithubRepository(GithubRepository.MODE_FOLLOWERS);
        githubRepository.setUserName(mUserName);
        liveData = githubRepository.getPagedListLiveData();
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
        GithubRepository githubRepository = new GithubRepository(GithubRepository.MODE_SEARCH);
        githubRepository.setSearchQuery(mSearchQuery);
        liveData = githubRepository.getPagedListLiveData();
    }

    public LiveData<PagedList<User>> getLiveData() {
        return liveData;
    }
}
