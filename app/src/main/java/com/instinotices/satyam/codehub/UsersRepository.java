package com.instinotices.satyam.codehub;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

public class UsersRepository {
    private LiveData<PagedList<User>> pagedListLiveData;
    public UsersRepository() {
        // Get an instance of users data source factory
        UsersDataSourceFactory usersDataSourceFactory = new UsersDataSourceFactory();
        // Set up configurations for our paged list live data object to be created in next line.
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(UsersDataSource.PAGE_SIZE)
                .setPrefetchDistance(20).build();
        // Generate LiveData<PagedList<User>> from users data source factory.
        pagedListLiveData = new LivePagedListBuilder(usersDataSourceFactory, pagedListConfig).build();
    }

    public LiveData<PagedList<User>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
