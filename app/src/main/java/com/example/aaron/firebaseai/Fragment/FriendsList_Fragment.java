package com.example.aaron.firebaseai.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.aaron.firebaseai.Adapter.FriendListAdapter;
import com.example.aaron.firebaseai.R;
import com.example.aaron.firebaseai.Tools.LinearItemDecoration;
import com.example.aaron.firebaseai.UserManager.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.aaron.firebaseai.LoginActivity.TAG;

public class FriendsList_Fragment extends Fragment{

    private String friend_request;
    private String FriendUid;
    private String CurrentUid;
    private String FriendName;
    private String FriendEmail;
    private String ReceivedUid;

    EditText edit_email;
    Button btn_sned_request;

    RecyclerView recyclerView;
    FirebaseDatabase db_friend_list;
    DatabaseReference users_friend_list;
    FriendListAdapter mFriendListAdapter;
    ArrayList<User> mUsers = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.friends_recyclerview,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db_friend_list = FirebaseDatabase.getInstance();
        users_friend_list = db_friend_list.getReference("FriendRequest");

        edit_email = view.findViewById(R.id.edit_email);
        btn_sned_request = view.findViewById(R.id.btn_send_request);

        CurrentUid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        mainRecyclerViewGetPage(view);
        btn_sned_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                friend_request = edit_email.getText().toString();

                Query query = reference.child("Users").orderByChild("email").equalTo(friend_request);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            // dataSnapshot is the "issue" node with all children with id 0
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                FriendUid = snapshot.child("uid").getValue().toString();
                                Log.d(TAG,"Uid:" + FriendUid);
                                // do something with the individual "issues"
                        }
                        users_friend_list.child(CurrentUid).child(FriendUid).setValue("sent");
                        users_friend_list.child(FriendUid).child(CurrentUid).setValue("received");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void mainRecyclerViewGetPage(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());      //創建默認的LayoutManager
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);   //如果可以確定每個item的高度是固定的，設置這個選項可以提高性能
        recyclerView.addItemDecoration(new LinearItemDecoration(1, Color.parseColor("#e7e7e7")));

        Log.d(TAG,"USERUID:" + CurrentUid);
        Query query = reference.child("FriendRequest").child(CurrentUid).orderByValue().equalTo("received");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // dataSnapshot is the "issue" node with all children with id 0
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    FriendUid = snapshot.child("uid").getValue().toString();
//                    Log.d(TAG,"Uid:" + FriendUid);
                    ReceivedUid =snapshot.getKey().toString();

                     reference.child("Users").child(ReceivedUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            FriendName = snapshot.child("name").getValue().toString();  //prints "Do you have data? You'll love Firebase."
                            Log.d(TAG,"Name:" + FriendName);
                            FriendEmail = snapshot.child("email").getValue().toString();  //prints "Do you have data? You'll love Firebase."
                            Log.d(TAG,"Email:" + FriendEmail);

                            User user = new User(FriendEmail,null,FriendName,null,ReceivedUid);
                            mUsers.add(user);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    Log.d(TAG,"ReceivedUid:" + ReceivedUid);
                    // do something with the individual "issues"
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mFriendListAdapter = new FriendListAdapter(getContext(),mUsers);
        mFriendListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mFriendListAdapter);
    }
}
