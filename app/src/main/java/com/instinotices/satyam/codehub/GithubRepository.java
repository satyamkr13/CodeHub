package com.instinotices.satyam.codehub;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

public class GithubRepository {
    public final static String MODE_HOME = "mode-home";
    public final static String MODE_FOLLOWERS = "mode-followers";
    public final static String MODE_SEARCH = "mode-search";
    private LiveData<PagedList<User>> pagedListLiveData;
    private String mode, userName, searchQuery;
    private GithubDataSourceFactory githubDataSourceFactory;
    private PagedList.Config pagedListConfig;

    public GithubRepository(String mode) {
        this.mode = mode;
        // Set up configurations for our paged list live data object to be created in next line.
        pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(GithubUsersDataSource.PAGE_SIZE)
                .setPrefetchDistance(20).build();

        if (mode.equals(MODE_HOME)) {
            // Load data instantly
            // Get an instance of github data source factory
            githubDataSourceFactory = new GithubDataSourceFactory();
            // Generate LiveData<PagedList<User>> from data source factory.
            pagedListLiveData = new LivePagedListBuilder(githubDataSourceFactory, pagedListConfig).build();
        }
        // Otherwise wait for userName or searchQuery.
    }

    private void getFollowers() {
        // Get an instance of github data source factory
        githubDataSourceFactory = new GithubDataSourceFactory(userName);
        // Generate LiveData<PagedList<User>> from data source factory.
        pagedListLiveData = new LivePagedListBuilder(githubDataSourceFactory, pagedListConfig).build();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        getFollowers();
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public LiveData<PagedList<User>> getPagedListLiveData() {
        return pagedListLiveData;
    }

}
