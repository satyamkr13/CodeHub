package com.instinotices.satyam.codehub.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.instinotices.satyam.codehub.model.data_types.User;
import com.instinotices.satyam.codehub.model.repositories.GithubRepository;

public class UsersViewModel extends AndroidViewModel {
    private LiveData<PagedList<User>> pagedListLiveData;
    public UsersViewModel(@NonNull Application application) {
        super(application);
        // Create an instance of Github repository, and get back instance of live data
        // where list of users will be posted when network request if complete.
        pagedListLiveData = new GithubRepository(GithubRepository.MODE_HOME)
                .getPagedListLiveData();
    }

    public LiveData<PagedList<User>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
