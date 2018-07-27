package com.example.zhoujianyu.gesturerecognition;

import android.graphics.Point;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public MyView mview;
    public RequestQueue myQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.sample_my_view);
        mview = findViewById(R.id.my_view);

        // get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        mview.screenWidth = screenWidth;
        mview.screenHeight = screenHeight;
        mview.init();

        // start a reading thread by calling readDiffStart method in native-lib.cpp
        readDiffStart();
    }

    public void sendCloud(final String data_str,String server_ip,String port){
        /**
         * send the str to a remote server
         */
        String url = "http://"+server_ip+":"+"5000"+"/";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //nothing here
//                Log.e("bug",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("bug","reponse error!!!!!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap data = new HashMap<String,String>();
                data.put("data",data_str);
                return data;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(1000, 0, 1.0f));
        myQueue.add(req);
    }

    /**
     * callback method after everytime native_lib.cpp read an image of capacity data
     * The function first convert
     * @param data: 32*16 short array
     */
    public void processDiff(short[] data) throws InterruptedException{
        // convert short array to a single string in convenience of data storage
        String capaString = "";
        for(int i = 0;i<data.length;i++){
            capaString+=(" "+Short.toString(data[i]));
        }

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS").format(Calendar.getInstance().getTime());

        sendCloud(timeStamp+capaString+"\n","10.0.0.67","5000");
        mview.updateData(data);
        mview.invalidate();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void readDiffStart ();
    public native void readDiffStop ();
}
