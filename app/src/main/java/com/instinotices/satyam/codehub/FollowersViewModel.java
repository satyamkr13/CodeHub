package com.instinotices.satyam.codehub;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

public class FollowersViewModel extends AndroidViewModel {
    String mUserName;
    private LiveData<PagedList<User>> followersListLiveData;

    public FollowersViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
        GithubRepository githubRepository = new GithubRepository(GithubRepository.MODE_FOLLOWERS);
        githubRepository.setUserName(mUserName);
        followersListLiveData = githubRepository.getPagedListLiveData();
    }

    public LiveData<PagedList<User>> getFollowersListLiveData() {
        return followersListLiveData;
    }
}
