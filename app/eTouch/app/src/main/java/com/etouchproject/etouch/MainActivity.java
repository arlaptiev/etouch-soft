package com.etouchproject.etouch;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import eu.basicairdata.bluetoothhelper.BluetoothHelper;

public class MainActivity extends Activity {


    Button btn_connect;
    Button btn_submit;
    Button btn_upload;
    Button btn_forward;
    Button btn_back;
    Button btn_autoscroll;
    View btn_escape;

    View img_autoscroll;
    boolean autoscroll = false;
    String message;


    BluetoothAdapter mBluetoothAdapter = null;
    BluetoothHelper mBluetooth = new BluetoothHelper();
    private String DEVICE_NAME = "HC-05";            // The name of the remote device (HC-05)

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Bluetooth", "Trying to start Bluetooth");

        // Deleting title bar from activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        toLaunchLayout();

        bluetoothHandle();

        readFileHandle();

        // Making splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                toHomeLayout();
                deepLinkHandle();

            }

        }, SPLASH_TIME_OUT);

    }


    public void deepLinkHandle() {

        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null && data.isHierarchical()) {
            String path = intent.getDataString();
            path = "eTouch Books/" + path.substring(path.indexOf("=") + 1).replace("-", " ") + ".txt";
            String text = readText(path);

            if (mBluetooth.isConnected()) {
                mBluetooth.SendMessage(text);
                toReadLayout();
            } else {
                Toast.makeText(this, "Connect to the device first!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void bluetoothHandle() {
        /* Handles connection, reconnection to bluetooth*/

        // Check if Bluetooth is supported by the device
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            finish();
        }

        mBluetooth.Connect(DEVICE_NAME);
        // Setup listener for Bluetooth helper;
        mBluetooth.setBluetoothHelperListener(new BluetoothHelper.BluetoothHelperListener() {
            @Override
            public void onBluetoothHelperMessageReceived(BluetoothHelper bluetoothhelper, String message) {
                // Do your stuff with the message received !!!
                // runOnUiThread(new Runnable() {
                //     @Override
                //     public void run() {
                //         // Update here your User Interface
                //     }
                // });
            }

            @Override
            public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {
                if (isConnected) {
                    btn_connect.setText("Connect");

                } else {
                    btn_connect.setText("Connecting..");
                    // Auto reconnect
                    mBluetooth.Connect(DEVICE_NAME);
                }
            }
        });
    }


    public void readFileHandle() {
        /* Handles reading of the files */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

    }


    private String readText(String input) {
        /* Reads content of the input file*/

        File file = new File(Environment.getExternalStorageDirectory(), input);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }


    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                if (path.contains("emulated")) {
                    path = path.substring(path.indexOf("0") + 1);
                }

                if (mBluetooth.isConnected()) {
                    mBluetooth.SendMessage(readText(path));
                    toReadLayout();
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Storage permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    public void toLaunchLayout() {
        setContentView(R.layout.activity_main);

        btn_connect = findViewById(R.id.tempButton);
    }


    public void toHomeLayout() {
        setContentView(R.layout.activity_home);

        btn_connect = findViewById(R.id.btn_connect);

        btn_connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mBluetooth.isConnected()) {
                    toUploadLayout();
                }
            }
        });

        if (mBluetooth.isConnected()) {
            btn_connect.setText("Connect");

        }
    }


    public void toUploadLayout() {
        setContentView(R.layout.activity_upload);

        btn_submit = findViewById(R.id.btn_submit);
        btn_upload = findViewById(R.id.btn_upload);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mBluetooth.isConnected()) {
                    EditText mMessage = findViewById(R.id.message);
                    mBluetooth.SendMessage(mMessage.getText().toString());
                    toReadLayout();
                }
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mBluetooth.isConnected()) {
                    performFileSearch();
                }
            }
        });

    }


    public void toReadLayout() {
        setContentView(R.layout.activity_read);

        btn_forward = findViewById(R.id.btn_forward);
        btn_back = findViewById(R.id.btn_back);
        btn_autoscroll = findViewById(R.id.btn_autoscroll);
        btn_escape = findViewById(R.id.btn_escape);

        // Declaring autoscrolling toggle image
        img_autoscroll = findViewById(R.id.img_autoscroll);


        btn_forward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mBluetooth.isConnected()) {
                    mBluetooth.SendMessage("btn_forward");
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mBluetooth.isConnected()) {
                    mBluetooth.SendMessage("btn_back");
                }
            }
        });


        btn_autoscroll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /* Autoscrolling button sending message */
                if (mBluetooth.isConnected()) {
                    //Switch the value of the autoscroll
                    mBluetooth.SendMessage("btn_autoscroll");
                    autoscroll = !autoscroll;

                    img_autoscroll.setBackgroundResource(autoscroll ? R.drawable.play : R.drawable.play_grey);
                }
            }
        });


        btn_escape.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toUploadLayout();
            }
        });
    }
}
