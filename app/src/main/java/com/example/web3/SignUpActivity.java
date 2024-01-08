package com.example.web3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, motherNameEditText, fatherNameEditText,
            idEditText, dofEditText, passwordEditText;
    private Switch switchMale, switchFemale;
    private Button signUpButton, shareButton;

    private Web3j web3j;
    private Credentials credentials;
    private IdentityManagement contract;
    private FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();


        // Initialize UI components
        nameEditText = findViewById(R.id.editName);
        surnameEditText = findViewById(R.id.editTextSurname);
        motherNameEditText = findViewById(R.id.editTextMotherName);
        fatherNameEditText = findViewById(R.id.editTextFatherName);
        idEditText = findViewById(R.id.editTextID);
        dofEditText = findViewById(R.id.editTextDOF);
        switchMale = findViewById(R.id.switchMale);
        switchFemale = findViewById(R.id.switchFemale);
        signUpButton = findViewById(R.id.SignUpButton);
        passwordEditText = findViewById(R.id.editTextPassword);
        shareButton = findViewById(R.id.shareButton);

        /*
        // Initialize web3j and credentials
        try {
            web3j = Web3j.build(Network.valueOf("https://goerli.infura.io/v3/04e668d908de430ab47d0f3f1887241b"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        credentials = Credentials.create("8a08c4738eaaf3a9023cf9e4e162e9bc897f1016c97501d52adf44957bb68049");

        // Load the smart contract
        contract = IdentityManagement.load(
                "0xc2B11E5CBf5D733E34D790CF5149e469f5a5eF54",
                web3j,
                credentials,
                new DefaultGasProvider()
        );

        */

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String motherName = motherNameEditText.getText().toString();
                String fatherName = fatherNameEditText.getText().toString();
                String id = idEditText.getText().toString();
                String dof = dofEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                // Determine gender based on switch state
                String gender = switchMale.isChecked() ? "Male" : (switchFemale.isChecked() ? "Female" : "Other");


                Kullanici_kayit kullanici = new Kullanici_kayit("",name,surname,motherName,fatherName,id,dof,gender,password);
                // Perform sign-up process
                performSignUp(name, surname, motherName, fatherName, id, dof, gender, password);


            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Share the identity if the button is clicked
                Intent loginIntent = new Intent(SignUpActivity.this, ShareActivity.class);
                startActivity(loginIntent);
            }
        });
    }





    private void performSignUp(String name, String surname, String motherName, String fatherName,
                               String id, String dof, String gender, String password) {

        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        // Corrected database reference
                        DatabaseReference myRef = database.getReference("users").child(userId);

                        Kullanici_kayit user = new Kullanici_kayit("", name, surname, motherName, fatherName, id, dof, gender, password);

                        myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Database", "Data successfully written to the database");
                                } else {
                                    Log.e("Database", "Error writing to the database: " + task.getException());
                                }
                            }
                        });

                        Toast.makeText(SignUpActivity.this, "Hesabınız başarılı bir şekilde oluşturuldu ve Giriş Yapıldı!", Toast.LENGTH_LONG).show();

                        Intent mi = new Intent(SignUpActivity.this, ShareActivity.class);
                        startActivity(mi);
                    } else {
                        // Handle the case where currentUser is null
                        Log.e("Authentication", "User is null after successful authentication");
                        Toast.makeText(SignUpActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where sign up failed
                    Log.e("Authentication", "Sign up failed. " + task.getException());
                    Toast.makeText(SignUpActivity.this, "Sign up failed. " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



