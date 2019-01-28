package com.etouchproject.etouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.IOException;
import java.io.Serializable;

public class UploadActivity extends AppCompatActivity {

    Button btn_submit, btn_upload;

    String message;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Deleting title bar from activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Declaring of button variables
        btn_submit = findViewById(R.id.btn_submit);
        btn_upload = findViewById(R.id.btn_upload);

        // Declaring message input variable
        message = findViewById(R.id.message).toString();


        // OnClickListener code for the submit
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*try {
                    BluetoothConnection.getApplication().getBluetoothOutputStream().write(message.getBytes()); // Transmits the value of message to the bluetooth module
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                nextActivity();
            }

        });


        // ToDo OnClickListener code for the upload


    }


    // Starts next Activity
    public void nextActivity() {
        startActivity(new Intent(UploadActivity.this, ReadActivity.class));
    }

}
