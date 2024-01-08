package com.example.web3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView, surnameTextView, idTextView, genderTextView,
            motherNameTextView, fatherNameTextView, dofTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.nameTextView);
        surnameTextView = findViewById(R.id.surnameTextView);
        idTextView = findViewById(R.id.idTextView);
        genderTextView = findViewById(R.id.genderTextView);
        motherNameTextView = findViewById(R.id.motherNameTextView);
        fatherNameTextView = findViewById(R.id.fatherNameTextView);
        dofTextView = findViewById(R.id.dofTextView);

        // Retrieve user information from Firebase Realtime Database
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String surname = dataSnapshot.child("surname").getValue(String.class);
                        String id = dataSnapshot.child("id").getValue(String.class);
                        String gender = dataSnapshot.child("gender").getValue(String.class);
                        String motherName = dataSnapshot.child("motherName").getValue(String.class);
                        String fatherName = dataSnapshot.child("fatherName").getValue(String.class);
                        String dof = dataSnapshot.child("dof").getValue(String.class);

                        // Display user information
                        nameTextView.setText("Name: " + name);
                        surnameTextView.setText("Surname: " + surname);
                        idTextView.setText("ID: " + id);
                        genderTextView.setText("Gender: " + gender);
                        motherNameTextView.setText("Mother's Name: " + motherName);
                        fatherNameTextView.setText("Father's Name: " + fatherName);
                        dofTextView.setText("Date of Birth: " + dof);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error
                }
            });
        }
    }
}
