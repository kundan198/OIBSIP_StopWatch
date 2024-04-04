package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvElapsedTime, tvLapTimes;
    private Button btnStart, btnStop, btnLap, btnClear;

    private boolean isRunning;
    private long startTime, elapsedTime, pausedTime;
    private ArrayList<Long> lapTimes;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvElapsedTime = findViewById(R.id.tvElapsedTime);
        tvLapTimes = findViewById(R.id.tvLapTimes);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnLap = findViewById(R.id.btnLap);
        btnClear = findViewById(R.id.btnClear);

        handler = new Handler();
        lapTimes = new ArrayList<>();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startTime = System.currentTimeMillis() - elapsedTime;
                    handler.postDelayed(updateTimer, 10);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    handler.removeCallbacks(updateTimer);
                    pausedTime = elapsedTime;
                }
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    lapTimes.add(elapsedTime);
                    updateLapTimes();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapsedTime = 0;
                pausedTime = 0;
                lapTimes.clear();
                updateTimerDisplay();
                updateLapTimes();
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimerDisplay();
            handler.postDelayed(this, 10);
        }
    };

    private void updateTimerDisplay() {
        long time = isRunning ? elapsedTime : pausedTime;
        int hours = (int) (time / 3600000);
        int minutes = (int) (time / 60000) % 60;
        int seconds = (int) (time / 1000) % 60;
        int milliseconds = (int) (time % 1000);
        String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
        tvElapsedTime.setText(timeString);
    }

    private void updateLapTimes() {
        StringBuilder lapText = new StringBuilder("Lap Times:\n");
        for (int i = 0; i < lapTimes.size(); i++) {
            long lapTime = lapTimes.get(i);
            int lapNumber = i + 1;
            int lapHours = (int) (lapTime / 3600000);
            int lapMinutes = (int) (lapTime / 60000) % 60;
            int lapSeconds = (int) (lapTime / 1000) % 60;
            int lapMilliseconds = (int) (lapTime % 1000);
            String lapTimeString = String.format(Locale.getDefault(), "%02d:%02d:%02d.%03d", lapHours, lapMinutes, lapSeconds, lapMilliseconds);
            lapText.append("Lap ").append(lapNumber).append(": ").append(lapTimeString).append("\n");
        }
        tvLapTimes.setText(lapText.toString());
    }
}
