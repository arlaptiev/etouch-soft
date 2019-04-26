package com.etouchproject.etouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.IOException;
import java.io.Serializable;

public class ReadActivity extends AppCompatActivity {

    Button btn_forward, btn_back, btn_autoscroll;

    View img_autoscroll;

    // Possible messages
    String message_forward = "btn-forward";
    String message_back = "btn-back";
    String message_autoscroll = "btn-autoscroll";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        // Deleting title bar from activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        // If opened from URL, connect to Bluetooth
        if(!BluetoothConnection.getApplication().isConnected()) {
            BluetoothConnection.getApplication().setupBluetoothConnection();
        }


        // Declaring of button variables
        btn_forward = findViewById(R.id.btn_forward);
        btn_back = findViewById(R.id.btn_back);
        btn_autoscroll = findViewById(R.id.btn_autoscroll);

        // Declaring message input variable
        img_autoscroll = findViewById(R.id.img_autoscroll);


        // OnClickListener code for the forward button
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*try {
                    BluetoothConnection.getApplication().getBluetoothOutputStream().write(message_forward.getBytes()); // Transmits the value of message to the bluetooth module
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        // OnClickListener code for the back button
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*try {
                    BluetoothConnection.getApplication().getBluetoothOutputStream().write(message_back.getBytes()); // Transmits the value of message to the bluetooth module
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        // Todo OnClickListener code for the autoscroll button
    }
}
