package com.example.web3;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;


import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Network;
import org.web3j.protocol.Web3j;

import org.web3j.tx.gas.DefaultGasProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.math.BigInteger;

public class LoginActivity extends AppCompatActivity {

    private EditText idEditText, passwordEditText;
    private Button enterButton;

    private Web3j web3j;
    private Credentials credentials;
    private IdentityManagement contract;

    private FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        idEditText = findViewById(R.id.editTextIDL);
        passwordEditText = findViewById(R.id.editTextPasswordL);
        enterButton = findViewById(R.id.buttonEnter);





        /*
        // Initialize web3j and credentials
        try {
            web3j = Web3j.build(Network.valueOf("https://goerli.infura.io/v3/04e668d908de430ab47d0f3f1887241b"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        credentials = Credentials.create("your_private_key");

        // Load the smart contract
        contract = IdentityManagement.load(
                "your_contract_address",
                web3j,
                credentials,
                new DefaultGasProvider()
        );
        */
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String id = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                auth = FirebaseAuth.getInstance();

                // Perform login process
                performLogin(id, password);
            }
        });
    }

    private void performLogin(String id, String password) {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Parola kısmı boş bırakılamaz!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

            usersRef.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String storedPassword = userSnapshot.child("password").getValue(String.class);

                            // Check if the entered password matches the stored password
                            if (password.equals(storedPassword)) {
                                Log.d(TAG, "Password matched. Signing in anonymously...");
                                signInAnonymously(); // Sign in anonymously if credentials match
                                return;
                            } else {
                                Log.d(TAG, "Password did not match.");
                                Toast.makeText(getApplicationContext(), "Parola doğru değil!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }

                    Log.d(TAG, "ID not found.");
                    Toast.makeText(getApplicationContext(), "ID bulunamadı!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Database error: " + databaseError.getMessage());
                    Toast.makeText(getApplicationContext(), "Veritabanı hatası: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void signInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Sign in anonymously successful.");
                    Toast.makeText(getApplicationContext(), "Giriş Başarılı!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, ShareActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, "Sign in anonymously failed. " + task.getException());
                    Toast.makeText(getApplicationContext(), "Giriş Başarısız. " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}