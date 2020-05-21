package com.example.chroot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;


public class run extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Process kali = Runtime.getRuntime().exec("bootkali");
            kali.getOutputStream().write("mount devpts /dev/pts -t devpts\n".getBytes());
            kali.getOutputStream().write("mount -t tmpfs  tmpfs /dev\n".getBytes());
            kali.getOutputStream().write("mount proc /proc -t proc\n".getBytes());
            kali.getOutputStream().write("mount  -t sysfs sysfs /sys\n".getBytes());
            kali.getOutputStream().write("mount -o remount -t rootfs  /root rootfs\n".getBytes());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process root = Runtime.getRuntime().exec("su");
                    root.getOutputStream().write("mount -o move /storage/extSdCard /data/local/kali-armhf/sdcard\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}