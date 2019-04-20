package com.instinotices.satyam.codehub.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.instinotices.satyam.codehub.R;
import com.instinotices.satyam.codehub.model.data_types.User;
import com.instinotices.satyam.codehub.viewmodel.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {
    User mUser;
    View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressView = findViewById(R.id.progressView);
        if (getIntent().hasExtra(MainActivity.INTENT_EXTRA_USER_NAME)) {
            DetailsViewModel detailsViewModel = ViewModelProviders
                    .of(this)
                    .get(DetailsViewModel.class);
            detailsViewModel.setUserName(
                    getIntent().getStringExtra(MainActivity.INTENT_EXTRA_USER_NAME)
            );
            detailsViewModel.getUserLiveData().observe(this, new Observer<User>() {
                @Override
                public void onChanged(@Nullable User user) {
                    if (user.getLogin() != null) {
                        mUser = user;
                        displayData();
                        progressView.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            finish();
        }
    }

    private void displayData() {
        Glide.with(this)
                .load(mUser.getAvatar_url())
                .into((ImageView) findViewById(R.id.imageViewUserDetails));
        ((TextView) findViewById(R.id.textViewDisplayName)).setText(mUser.getName());
        ((TextView) findViewById(R.id.textViewUserName)).setText(mUser.getLogin());
        ((TextView) findViewById(R.id.textViewFollowerCount)).setText(mUser.getFollowers() + "");
        ((TextView) findViewById(R.id.textViewFollowingCount)).setText(mUser.getFollowing() + "");

        if (mUser.getCompany() != null) {
            ((TextView) findViewById(R.id.textViewCompany)).setText(mUser.getCompany());
        } else {
            findViewById(R.id.imageViewCompany).setVisibility(View.GONE);
            findViewById(R.id.textViewCompany).setVisibility(View.GONE);
        }

        if (mUser.getEmail() != null) {
            ((TextView) findViewById(R.id.textViewEmail)).setText(mUser.getEmail());
        } else {
            findViewById(R.id.imageViewEmail).setVisibility(View.GONE);
            findViewById(R.id.textViewEmail).setVisibility(View.GONE);
        }

        if (mUser.getLocation() != null) {
            ((TextView) findViewById(R.id.textViewLocation)).setText(mUser.getLocation());
        } else {
            findViewById(R.id.imageViewLocation).setVisibility(View.GONE);
            findViewById(R.id.textViewLocation).setVisibility(View.GONE);
        }

        if (mUser.getBio() != null) {
            ((TextView) findViewById(R.id.textViewBio)).setText(mUser.getBio());
        } else {
            findViewById(R.id.imageViewBio).setVisibility(View.GONE);
            findViewById(R.id.textViewBio).setVisibility(View.GONE);
        }
    }


    public void openInGithub(View view) {
        Uri url = Uri.parse(mUser.getHtml_url());
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, url);
    }

    public void viewFollowers(View view) {
        Intent intent = new Intent(this, UsersListActivity.class);
        intent.putExtra(MainActivity.INTENT_EXTRA_USER_NAME, mUser.getLogin());
        startActivity(intent);
    }
}
