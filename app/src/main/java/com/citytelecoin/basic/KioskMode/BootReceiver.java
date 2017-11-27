package com.citytelecoin.basic.KioskMode;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.citytelecoin.basic.Navigation.Navigate;

//Boots app when device is turned on

public class BootReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
	@Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, Navigate.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}
