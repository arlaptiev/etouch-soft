/*package com.etouchproject.etouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import java.util.concurrent.TimeUnit;

import eu.basicairdata.bluetoothhelper.BluetoothHelper;

public class HomeActivity extends Activity {

    Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Deleting title bar from activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);


        // Declaring button variables
        btn_connect = findViewById(R.id.btn_connect);

        // Button that connects the device to the bluetooth module
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_connect.setText("Connecting...");

                try {
                    nextActivity(BluetoothConnection.getApplication().setupBluetoothConnection());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    // Starts next Activity if connected to Bluetooth module
    public void nextActivity(boolean connected) throws InterruptedException {

        // Start Next Activity if connected, close app if not
        if(connected) {

            btn_connect.setText("Success!");
            TimeUnit.SECONDS.sleep(1);
            startActivity(new Intent(HomeActivity.this, UploadActivity.class));

        } else {

            btn_connect.setText("Failure :c");
            TimeUnit.SECONDS.sleep(1);
            HomeActivity.this.finish();
            System.exit(0);

        }
    }


}*/






/*package com.etouchproject.etouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import java.util.concurrent.TimeUnit;

import eu.basicairdata.bluetoothhelper.BluetoothHelper;

public class HomeActivity extends Activity {

    Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Bluetooth", "Trying to start Bluetooth");

        // Deleting title bar from activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);


        // Declaring button variables
        btn_connect = findViewById(R.id.btn_connect);

        // Button that connects the device to the bluetooth module
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BluetoothConnection.getApplication().setupBluetoothConnection();
                nextActivity();

            }
        });

    }


    // Starts next Activity if connected to Bluetooth module
    public void nextActivity() {

        // Start Next Activity if connected, close app if not
        while(!BluetoothConnection.getApplication().isConnected()) {
            btn_connect.setText("Connecting...");
        }

        btn_connect.setText("Connected!");
        //TimeUnit.SECONDS.sleep(1);
        //startActivity(new Intent(HomeActivity.this, UploadActivity.class));
    }


}*/


package com.etouchproject.etouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import java.util.concurrent.TimeUnit;

import eu.basicairdata.bluetoothhelper.BluetoothHelper;

public class HomeActivity extends Activity {

    Button btn_connect;
    BluetoothHelper mBluetoothHelper = new BluetoothHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Bluetooth", "Trying to start Bluetooth");

        // Deleting title bar from activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);

        mBluetoothHelper.setBluetoothHelperListener(new BluetoothHelper.BluetoothHelperListener() {
            @Override
            public void onBluetoothHelperMessageReceived(BluetoothHelper bluetoothhelper, final String message) {
                // Do something with the message received
                // runOnUiThread(new Runnable() {
                //     @Override
                //     public void run() {
                //         // Update your UI
                //     }
                // });
            }

            @Override
            public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {
                mBluetoothHelper.SendMessage("1");
                // Do something, depending on the new connection status
            }
        });


        // Declaring button variables
        btn_connect = findViewById(R.id.btn_connect);

        // Button that connects the device to the bluetooth module
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_connect.setText("Success!");
                mBluetoothHelper.Connect("HC-05");

            }
        });

    }



}

