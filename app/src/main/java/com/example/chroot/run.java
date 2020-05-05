package com.example.chroot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;

public class run extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Process p=Runtime.getRuntime().exec("bootkali");
            p.getOutputStream().write("mount -t tmpfs  tmpfs /dev\n".getBytes());
            p.getOutputStream().write("mount proc /proc -t proc\n".getBytes());
            p.getOutputStream().write("mount /sys\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
