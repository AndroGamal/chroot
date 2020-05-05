package com.example.chroot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button chroot,buttonpath;
Process process;
EditText editText;
FolderPath path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chroot = findViewById(R.id.chroot);
        editText=findViewById(R.id.editText);
        buttonpath=findViewById(R.id.path);
        path=new FolderPath(this) {
            @Override
            void OK() {
               editText.setText(path.getpath());
            }
        };
        try {
            process=Runtime.getRuntime().exec("su");
        } catch (IOException e) { e.printStackTrace(); }
        buttonpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path.create();
            }
        });
        chroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    process.getOutputStream().write("dd if=/dev/zero of=storage/extSdCard/swapkali bs=1024 count=524000\n".getBytes());
                    process.getOutputStream().write("mkswap storage/extSdCard/swapkali\n".getBytes());
                    process.getOutputStream().write("swapon -p 8 storage/extSdCard/swapkali\n".getBytes());
                    process.getOutputStream().write(("cp "+editText.getText()+" /data/local\n").getBytes());
                    process.getOutputStream().write("cd /data/local\n".getBytes());
                    process.getOutputStream().write("tar xJf kalifs-minimal.tar.xz\n".getBytes());
                    process.getOutputStream().write("cd #\n".getBytes());
                    process.getOutputStream().write("cd ..\n".getBytes());
                    process.getOutputStream().write("cp /data/local/kali-armhf/bin /data/local/kali-armhf/system/bin\n".getBytes());
                    process.getOutputStream().write("mount -o rw,remount /system\n".getBytes());
                    process.getOutputStream().write("echo \"su -c hostname kali \\\\\\n\">/system/bin/bootkali\n".getBytes());
                    process.getOutputStream().write("echo \"su -c chroot /data/local/kali-armhf su \\n\">>/system/bin/bootkali\n".getBytes());
                    process.getOutputStream().write("chmod 777 /system/bin/bootkali\n".getBytes());
                    process.getOutputStream().write("mount -o ro,remount /system\n".getBytes());
                    process.getOutputStream().write("bootkali\n".getBytes());
                    process.getOutputStream().write("mount -t tmpfs  tmpfs /dev\n".getBytes());
                    process.getOutputStream().write("mount proc /proc -t proc\n".getBytes());
                    process.getOutputStream().write("mount /sys\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
