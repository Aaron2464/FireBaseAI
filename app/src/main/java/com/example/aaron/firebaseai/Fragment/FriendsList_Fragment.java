package com.example.aaron.firebaseai.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.aaron.firebaseai.R;
import com.example.aaron.firebaseai.UserManager.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.aaron.firebaseai.LoginActivity.TAG;

public class FriendsList_Fragment extends Fragment{

    private String friend_request;
    private String FriendUid;
    private String CurrentUid;

    EditText edit_email;
    Button btn_sned_request;

    FirebaseDatabase db_friend_list;
    DatabaseReference users_friend_list;

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
        users_friend_list = db_friend_list.getReference("Friend Request");

        edit_email = view.findViewById(R.id.edit_email);
        btn_sned_request = view.findViewById(R.id.btn_send_request);

        CurrentUid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        btn_sned_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                friend_request = edit_email.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

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
}
