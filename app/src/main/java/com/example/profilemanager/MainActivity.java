package com.example.profilemanager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> profileActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText teamName = (EditText) findViewById(R.id.teamNameField);
        EditText teamAddress = (EditText) findViewById(R.id.teamAddressField);

        TextView teamNameView = (TextView) findViewById(R.id.teamNameTextField);
        TextView teamAddressView = (TextView) findViewById(R.id.teamAddressTextField);

        teamName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                teamNameView.setText(newText);
            }
        });

        teamAddress.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                teamAddressView.setText(newText);
            }
        });


        profileActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                        }
                    }
                }
        );
    }

    public void OnOpenInGoogleMaps(View view) {
        EditText teamAddress = (EditText) findViewById(R.id.teamAddressField);

        // Create a Uri from an intent string. Use the result to create an Intent
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q=" + teamAddress.getText());

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    public void OnSetAvatarButton(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileActivityLauncher.launch(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        // Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.avatarImage);

        //Figuring out the correct image
        String drawableName = "ic_logo_00";

        int imageID = data.getIntExtra("imageID", R.id.teamid00);

        if (imageID == R.id.teamid00) {
            drawableName = "ic_logo_00";
        } else if (imageID == R.id.teamid01) {
            drawableName = "ic_logo_01";
        } else if (imageID == R.id.teamid02) {
            drawableName = "ic_logo_02";
        } else if (imageID == R.id.teamid03) {
            drawableName = "ic_logo_03";
        } else if (imageID == R.id.teamid04) {
            drawableName = "ic_logo_04";
        } else if (imageID == R.id.teamid05) {
            drawableName = "ic_logo_05";
        } else {
            drawableName = "ic_logo_00";
        }

        int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        avatarImage.setImageResource(resID);
    }
}