package com.instinotices.satyam.codehub.view;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.instinotices.satyam.codehub.R;
import com.instinotices.satyam.codehub.model.data_types.User;

public class UsersAdapter extends PagedListAdapter<User, UsersAdapter.ViewHolder> {
    private InteractionCallbacks mInteractionCallbacks;
    private final RequestManager requestManagerGlide;

    public UsersAdapter(InteractionCallbacks interactionCallbacks, RequestManager requestManager) {
        super(diffUtilItemCallback);
        mInteractionCallbacks = interactionCallbacks;
        requestManagerGlide = requestManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate user card and return  corresponding the view holder.
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_user_card, viewGroup, false);
        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        // Populate cards with data
        viewHolder.userName.setText(getItem(position).getLogin());
        requestManagerGlide.load(getItem(position).getAvatar_url()).into(viewHolder.avatar);
        // Add an onClickListener when a user card is clicked
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Using viewHolder to get current item position because RecyclerView does not calls
                // @onBindViewHolder method again when it's position in list gets changed.
                mInteractionCallbacks.onUserCardClick(getItem(viewHolder.getAdapterPosition()));
                // The MainActivity will implement this method.
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final View mView;
        ImageView avatar;
        TextView userName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Get references to views
            mView = itemView;
            avatar = mView.findViewById(R.id.imageViewAvatar);
            userName = mView.findViewById(R.id.textViewUserName);
        }
    }

    /*
     * Utility class that calculates the difference between two lists and
     * output a list of update operations that converts the first list into the second one.
     * An object of this class is required by PageListAdapter to automatically
     * update user cards (with animations) being currently displayed if data changes.
     */
    private static DiffUtil.ItemCallback<User> diffUtilItemCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User user, @NonNull User t1) {
            // Checks if items have same ID by comparing userName.
            return user.getLogin().equals(t1.getLogin());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User user, @NonNull User t1) {
            // Checks if contents displayed on screen by these objects the same, we'll use userName for this as well.
            return user.getLogin().equals(t1.getLogin());
        }
    };

    /**
     * This interface provides interaction callbacks
     * like clicks to classes which implement this interface.
     */
    public interface InteractionCallbacks{
        void onUserCardClick(User user);
    }
}
