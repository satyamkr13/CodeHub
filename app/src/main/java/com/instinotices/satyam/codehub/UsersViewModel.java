package com.instinotices.satyam.codehub;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

public class UsersViewModel extends AndroidViewModel {
    private LiveData<PagedList<User>> pagedListLiveData;
    public UsersViewModel(@NonNull Application application) {
        super(application);
        pagedListLiveData = new GithubRepository(GithubRepository.MODE_HOME)
                .getPagedListLiveData();
    }

    public LiveData<PagedList<User>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
