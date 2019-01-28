package com.etouchproject.etouch;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection extends Application {

    private static BluetoothConnection sInstance;

    public static BluetoothConnection getApplication() {
        return sInstance;
    }

    // Bluetooth variables
    private final String DEVICE_ADDRESS = "98:D3:81:FD:43:D0"; // ToDo change MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // ToDo Change this
    private boolean connected = false;

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Log.d("Bluetooth", "IM ON");
    }

    // Returns true if successfully connected
    public boolean setupBluetoothConnection() {
        if(BTinit()) {
            connected = BTconnect();
            return connected;
        }
        return false;
    }

    public OutputStream getBluetoothOutputStream() {
        return outputStream;
    }

    public boolean isConnected() {
        return connected;
    }

    // Finds the device
    public boolean BTinit()
    {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) // Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if(!bluetoothAdapter.isEnabled()) // Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {

            /* ToDo check if bluetooth is enabled
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            */
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) // Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }


    // Connects to the device
    public boolean BTconnect()
    {
        boolean connected = true;

        try
        {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); // Creates a socket to handle the outgoing connection
            socket.connect();

            Toast.makeText(getApplicationContext(),
                    "Connection to bluetooth device successful", Toast.LENGTH_LONG).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            connected = false;
        }

        if(connected)
        {
            try
            {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return connected;
    }

}




/*
package com.etouchproject.etouch;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import eu.basicairdata.bluetoothhelper.BluetoothHelper;

public class BluetoothConnection extends Application {

    private static BluetoothConnection sInstance;

    public static BluetoothConnection getApplication() {
        return sInstance;
    }

    // Bluetooth variables
    BluetoothHelper mBluetoothHelper = new BluetoothHelper();
    private String DEVICE_NAME = "HC-05";
    private boolean connected = false;

    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Log.d("Bluetooth", "I HAPPENED");

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
                connected = true;
            }
        });

    }

    // Returns true if successfully connected
    public void setupBluetoothConnection() {
        Log.d("Bluetooth", "SETTING UP CONNECTION");
        mBluetoothHelper.Connect(DEVICE_NAME);
        mBluetoothHelper.SendMessage("1");
    }

    public boolean isConnected() {
        return connected;
    }



}

 */