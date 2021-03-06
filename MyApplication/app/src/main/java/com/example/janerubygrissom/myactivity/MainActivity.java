package com.example.janerubygrissom.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String userId;
    String userColor;
    EditText edtMessage;
    Button btnSend;
    ListView lstView;

    DatabaseReference dbRef, messagesRef;
    FirebaseListAdapter<Message> mAdapter;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        dbRef = FirebaseDatabase.getInstance().getReference();
        messagesRef = dbRef.child("messages");

                mAdapter = new FirebaseListAdapter<Message>(this,
                Message.class,
                android.R.layout.simple_expandable_list_item_2,
                messagesRef) {
            @Override


            protected void populateView(View v, Message model, int position) {
                TextView txtUser = (TextView) v.findViewById(android.R.id.text1);
                TextView txtMessage = (TextView) v.findViewById(android.R.id.text2);

                        txtUser.setText(model.getUsername());
                txtMessage.setText(model.getMessage());

            }


        };
        lstView.setAdapter(mAdapter);

                changeToolbarTitle();


    }



    private void changeToolbarTitle() {
        getSupportActionBar().setTitle(userId);

    }




    private void initializeViews() {
       userId = getRandomUserName();

                edtMessage = (EditText) findViewById(R.id.edtMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        lstView = (ListView) findViewById(R.id.lstView);

                btnSend.setOnClickListener(this);

    }


    @Override


    public void onClick(View view) {
         switch (view.getId()) {
             case R.id.btnSend:
                String message = edtMessage.getText().toString().trim();
                Message messageObject = new Message(userId, message);
                messagesRef.push().setValue(messageObject);
                mAdapter.notifyDataSetChanged();
                edtMessage.setText("");
                 break;

        }

    }



    private String getRandomUserName() {
        Random rand = new SecureRandom();
         int postfix = (rand.nextInt() % 10000) + 1;
         return "User" + String.format(Locale.ENGLISH, "%04d", postfix);

    }


}