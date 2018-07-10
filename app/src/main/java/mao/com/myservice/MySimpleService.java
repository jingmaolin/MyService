package mao.com.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static java.lang.Thread.sleep;

/**
 * Description:startService 与bindService的简单使用
 * author:jingmaolin
 * email:1271799407@qq.com.
 * phone:13342446520.
 * date: 2018/7/4.
 */
public class MySimpleService extends Service {
    private static final String TAG = "maoTest";
    private Thread mThread;
    private boolean isDestroy;
    private boolean isBindDestroy;
    private int mCount = 0;

    private MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder {
        private String dec;

        public void setDec(String dec) {
            this.dec = dec;
        }

        public String getDec() {
            return dec;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        isBindDestroy = false;
        Log.d(TAG, "onBind: ");
        Log.d(TAG, "onBind: " + "currentThread name = " + Thread.currentThread().getName());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isBindDestroy) {
                    ++mCount;
                    try {
                        sleep(1000);
                        myBinder.setDec("" + mCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return myBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        Log.d(TAG, "onStartCommand: " + "currentThread name = " + Thread.currentThread().getName());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isDestroy = false;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDestroy) {
                    Log.d(TAG, "run: " + "alive");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        mThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        isBindDestroy = true;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        isDestroy = true;
    }
}
