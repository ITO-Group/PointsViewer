package com.example.zhoujianyu.gesturerecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // start a reading thread by calling readDiffStart method in native-lib.cpp
        readDiffStart();
    }

    public void sendCloud(String str){
        /**
         * send the str to a remote server
         */
    }

    /**
     * callback method after everytime native_lib.cpp read an image of capacity data
     * The function first convert
     * @param data: 32*18 short array
     */
    public void processDiff(short[] data){
        // convert short array to a single string in convenience of data storage
        int max_val = 0;
        int min_val = 10000;
        for(int i = 0;i<data.length;i++){
            if(max_val<data[i]) max_val = data[i];
            if(min_val>data[i]) min_val = data[i];
        }

        String capaString = "";
        for(int i = 0;i<data.length;i++){
            capaString+=(" "+Short.toString(data[i]));
        }
        capaString = capaString.trim();
        sendCloud(capaString+"\n");
        Log.d("collect",Integer.toString(max_val)+","+Integer.toString(min_val));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void readDiffStart ();
    public native void readDiffStop ();
}
