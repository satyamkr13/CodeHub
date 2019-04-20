package com.instinotices.satyam.codehub.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.instinotices.satyam.codehub.model.data_sources.AllUsersDataSource;
import com.instinotices.satyam.codehub.model.data_types.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends AndroidViewModel {
    private MutableLiveData<User> userMutableLiveData;
    private Application mApplication;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        userMutableLiveData = new MutableLiveData<>();
        mApplication = application;
    }

    public void setUserName(String userName) {
        fetchUser(userName);
    }

    public LiveData<User> getUserLiveData() {
        return userMutableLiveData;
    }

    private void fetchUser(String userName) {
        AllUsersDataSource.gitHubService()
                .getUser(userName)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            userMutableLiveData.postValue(response.body());
                        } else {
                            Toast.makeText(mApplication, "Bad response from server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(mApplication, "Internal error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
