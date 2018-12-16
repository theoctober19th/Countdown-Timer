package com.theoctober19th.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Constants
    int maxTimerLimit = 10*60; //10 minutes
    int minTimerLimit = 1; //1 second
    int defaultTimerPosition = 1*60; //1 minute
    int countdown;
    boolean isTimerRunning = false;
    //Views declaration
    SeekBar timeSeekBar = null;
    TextView timeTextView = null;
    Button resetButton = null;

    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view definition
        timeSeekBar = (SeekBar)findViewById(R.id.timeSeekBar);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        timeTextView.setText(DateUtils.formatElapsedTime(defaultTimerPosition));
        resetButton = (Button) findViewById(R.id.resetButton);

         mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);

        //set seekbar parameters
        timeSeekBar.setMax(maxTimerLimit);
        timeSeekBar.setProgress(defaultTimerPosition);
        countdown = 1;
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                countdown = i;
                String text = DateUtils.formatElapsedTime(i);
                timeTextView.setText(text);
                //startCountDown(countdown);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startCountDown(int countdown){
        timeSeekBar.setEnabled(false);
        countDownTimer = new CountDownTimer(countdown*1000, 1000){
            @Override
            public void onTick(long l) {
                timeTextView.setText(DateUtils.formatElapsedTime(l/1000 + 1));
            }

            @Override
            public void onFinish() {
                timeTextView.setText(DateUtils.formatElapsedTime(0));
                if(isTimerRunning) mediaPlayer.start();
                resetTimer();
            }
        }.start();
    }

    public void startOrStop(View view){
        if(!isTimerRunning){
            Log.i("Bikalpa", "Start button bpressed");
            resetButton.setText("Reset");
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
            startCountDown(timeSeekBar.getProgress());
            isTimerRunning = true;
        }else{
            Log.i("Bikalpa", "Stop Button pressed");
            resetTimer();
        }
    }

    public void resetTimer(){
        resetButton.setText("Start");
        countDownTimer.cancel();
        timeSeekBar.setEnabled(true);
        timeSeekBar.setProgress(defaultTimerPosition);
        timeTextView.setText(DateUtils.formatElapsedTime(timeSeekBar.getProgress()));
        isTimerRunning = false;
    }
}
