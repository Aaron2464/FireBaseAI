package com.example.aaron.firebaseai.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aaron.firebaseai.R;
import com.example.aaron.firebaseai.UserManager.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.aaron.firebaseai.LoginActivity.TAG;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder>{

    private FirebaseDatabase dbAdapter;
    private Context mContext;
    private String FriendUid;
    private String FriendName;
    private String FriendEmail;
    ArrayList<User>mUsers;

    public FriendListAdapter(Context context, ArrayList<User> users) {
        mContext = context;
        mUsers = users;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txt_friend_name.setText(mUsers.get(position).getName());
        holder.txt_friends_email.setText(mUsers.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"size" + mUsers.size());
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_friend_name;
        private TextView txt_friends_email;

        public ViewHolder(final View itemview) {
            super(itemview);

            txt_friend_name = (TextView)itemview.findViewById(R.id.txt_friend_name);
            txt_friends_email = (TextView)itemview.findViewById(R.id.txt_friends_email);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
