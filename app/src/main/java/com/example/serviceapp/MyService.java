package com.example.serviceapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class MyService extends Service {

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = intent.getIntExtra(MainActivity.PARAM_TIME, 1);
        PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);

        MyRun mr = new MyRun(time, startId, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {

        int time;
        int startId;
        PendingIntent pi;

        public MyRun(int time, int startId, PendingIntent pi) {
            this.time = time;
            this.startId = startId;
            this.pi = pi;
        }

        public void run() {
            try {
                // сообщаем об старте задачи
                pi.send(MainActivity.STATUS_START);

                // начинаем выполнение задачи
                TimeUnit.SECONDS.sleep(time);

                // сообщаем об окончании задачи
                Intent intent = new Intent().putExtra(MainActivity.PARAM_RESULT, time * 100);
                pi.send(MyService.this, MainActivity.STATUS_FINISH, intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            stop();
        }

        void stop() {
            stopSelfResult(startId);
        }
    }
}