package com.example.aaron.firebaseai.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.aaron.firebaseai.R;
import com.example.aaron.firebaseai.UserManager.Content;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Write_Fragment extends Fragment{

    Button btn_send;
    EditText edit_title;
    EditText edit_content;

    DatabaseReference SendContent;
    String CurrentUid;

    String title;
    String content;
    String time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.content_aritcle,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_send = (Button)view.findViewById(R.id.btn_send);
        edit_title = (EditText)view.findViewById(R.id.edit_title);
        edit_content = (EditText)view.findViewById(R.id.edit_content);

        CurrentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SendContent = FirebaseDatabase.getInstance().getReference();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                time = formatter.format(curDate);
                title = edit_title.getText().toString();
                content = edit_content.getText().toString();

                Content contentarticle = new Content(title,content,time,null);

                SendContent.child(CurrentUid).push().setValue(contentarticle);

            }
        });
    }

}
