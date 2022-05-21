package org.linuxswords.games.chessclock;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity
{

    // create variables of the two class
    private Gyroscope gyroscope;
    private PlayerClock leftClock;
    private PlayerClock rightClock;
    long startTime = 5L * 60L;  // make this configurable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView leftClockView = findViewById(R.id.clockLeft);
        TextView rightClockView = findViewById(R.id.clockRight);

        leftClock = new PlayerClock(startTime, leftClockView).showStartTime();
        rightClock = new PlayerClock(startTime, rightClockView).showStartTime();

        gyroscope = new Gyroscope(this);
        gyroscope.setListener(this::clockHasTilted);

        // pause button
        findViewById(R.id.pauseButton).setOnClickListener(v -> this.pauseAllClocks());

        // reset button
        findViewById(R.id.restartButton).setOnClickListener(v -> this.restartAllClocks());
    }

    private void clockHasTilted(float rx, float ry, float rz)
    {
        if (rz > 0.5f) {
            leftClock.pause();
            rightClock.start();
        }
        else if (rz < -0.5f) {
            leftClock.start();
            rightClock.pause();
        }
//            play sound here ?
    }

    private void pauseAllClocks()
    {
        leftClock.pause();
        rightClock.pause();
    }

    private void restartAllClocks()
    {
        leftClock.restart().showStartTime();
        rightClock.restart().showStartTime();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        gyroscope.register();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gyroscope.unregister();
    }

}
