package com.example.anrtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        captureANR();
//        createANR();
    }

    private void captureANR(){
        //Android5.0低权限应用不能监听变化?
        FileObserver fileObserver = new FileObserver("/data/anr/", FileObserver.CLOSE_WRITE) {
            @Override
            public void onEvent(int event, @Nullable String path) {
                Log.i(TAG, "onEvent: " + path);
                getANRInfo();
            }
        };
        fileObserver.startWatching();
    }

    private void getANRInfo(){
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert am != null;
        List<ActivityManager.ProcessErrorStateInfo> list=  am.getProcessesInErrorState();
        for (ActivityManager.ProcessErrorStateInfo info: list){
            Log.i(TAG, "getANRLog: " + info);
            if (info.condition == 2){
                Log.e(TAG, "getANRLog: " + info);
            }
        }
    }

    private void createANR(){
        while(true){}
    }
}
