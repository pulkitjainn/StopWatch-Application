package com.example.pulkit.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button start, pause, lap;
    TextView timerValue;
    Handler customHandler = new Handler();
    LinearLayout container;

    long startTime = 0L,time = 0L, timeSwap=0L, updateTime = 0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            time = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwap+time;

            int sec = (int) (updateTime/1000);
            int min = sec/60;
            sec%=60;
            int millisecond = (int) (updateTime%1000);
            timerValue.setText(""+min+":"+String.format("%2d",sec)+":"+String.format("%3d",millisecond));
            customHandler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        lap = (Button) findViewById(R.id.lap);
        timerValue = (TextView) findViewById(R.id.timerValue);
        container = (LinearLayout) findViewById(R.id.container);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime  = SystemClock.uptimeMillis();

                customHandler.postDelayed(updateTimerThread ,0);
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap+=time;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });


        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView textView = (TextView) addView.findViewById(R.id.textContent);
                textView.setText(timerValue.getText());
                container.addView(addView);
            }
        });

    }



}
