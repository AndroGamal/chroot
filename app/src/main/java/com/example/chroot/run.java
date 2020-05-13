package com.example.chroot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;

public class run extends BroadcastReceiver {
   ;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Process kali=Runtime.getRuntime().exec("su");
            kali.getOutputStream().write("bootkali\n".getBytes());
            kali.getOutputStream().write("mount -t ramfs  ramfs /dev\n".getBytes());
            kali.getOutputStream().write("mount proc /proc -t proc\n".getBytes());
            kali.getOutputStream().write("mount  -t sysfs sysfs /sys\n".getBytes());
            kali.getOutputStream().write("exit\n".getBytes());
            kali.getOutputStream().write("mount -o bind /dev  data/local/kali-armhf/dev\n".getBytes());
            kali.getOutputStream().write ("bootkali\n".getBytes());
            kali.getOutputStream().write("dd if=/dev/zero of=sdcard/swapkali bs=1024 count=75000\n".getBytes());
            kali.getOutputStream().write("mkswap sdcard/swapkali\n".getBytes());
            kali.getOutputStream().write("swapon -p 8 sdcard/swapkali\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
