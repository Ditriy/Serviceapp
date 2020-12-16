package com.example.serviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    final int TASK1_CODE = 1;

    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;

    public final static String PARAM_TIME = "time";
    public final static String PARAM_PINTENT = "pendingIntent";
    public final static String PARAM_RESULT = "result";

    Button start;
    TextView tvTask1;
    TextView tvTask2;
    TextView tvTask3;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTask1 = (TextView) findViewById(R.id.tvTask1);
        tvTask1.setText("Task1");
        start = (Button) findViewById(R.id.btnStart);
    }

    public void onClickStart(View v) {
        PendingIntent pi;
        Intent intent;

        // Создаем PendingIntent для Task1
        pi = createPendingResult(TASK1_CODE, null, 0);
        // Создаем Intent для вызова сервиса, кладем туда параметр времени
        // и созданный PendingIntent
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 7)
                .putExtra(PARAM_PINTENT, pi);
        // стартуем сервис
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == STATUS_START) {
            if (requestCode==TASK1_CODE) {
                    tvTask1.setText("Task1 start");
            }
        }

        // Ловим сообщения об окончании задач
        if (resultCode == STATUS_FINISH) {
            int result = data.getIntExtra(PARAM_RESULT, 0);
            if (requestCode==TASK1_CODE) {
                    tvTask1.setText("Task1 finish, result = " + result);
            }
        }
    }
}