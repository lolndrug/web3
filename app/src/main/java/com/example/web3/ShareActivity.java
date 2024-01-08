package com.example.web3;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private Button copyAddressButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        copyAddressButton = findViewById(R.id.copyAddressButton);
        homeButton = findViewById(R.id.buttonHome);

        // Kullanıcının Ethereum adresini al
        String userAddress = getUserAddress();

        // "Copy Address" butonuna tıklanınca Ethereum adresini panoya kopyalama
        copyAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(userAddress);
                showToast("Address copied to clipboard");
            }
        });


        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(ShareActivity.this, ProfileActivity.class);
                startActivity(loginIntent);
            }


        });

    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Address", text);
        clipboard.setPrimaryClip(clip);
    }

    // Toast mesajı gösterme
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private String getUserAddress() {
        //In smart contract
        return "0x1234567890abcdef1234567890abcdef12345678";
    }
}
