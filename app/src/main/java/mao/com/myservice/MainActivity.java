package mao.com.myservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent mIntent;
    private Intent mIntentService;
    private static final String TAG = "maoTest";
    private MySimpleService.MyBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + "main Thread name =" + Thread.currentThread().getName());

        mIntent = new Intent();
        mIntent.setAction("com.mao.MySimpleService");
        mIntent.setPackage(getPackageName());

        mIntentService = new Intent(this, MyAdvanceService.class);
    }

    public void StartService(View v) {
        startService(mIntent);

    }

    public void StopService(View v) {
        stopService(mIntent);
    }

    public void BindService(View v) {
        bindService(mIntent, con, Service.BIND_AUTO_CREATE);
    }

    public void UnBindService(View v) {
        unbindService(con);
    }

    public void getBinderData(View v) {
        Log.d(TAG, "getBinderData: " + mBinder.getDec());
    }

    public void intentService_startService(View v) {
        Log.d(TAG, "intentService_binderService: ");
        startService(mIntentService);
    }

    public void intentService_stopService(View v) {
        Log.d(TAG, "intentService_unBinderService: ");
        stopService(mIntentService);
    }

    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mBinder = (MySimpleService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };
}
