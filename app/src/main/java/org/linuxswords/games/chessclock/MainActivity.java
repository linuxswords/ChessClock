package org.linuxswords.games.chessclock;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity
{

    // create variables of the two class
    private Gyroscope gyroscope;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView leftClockView = findViewById(R.id.clockLeft);
        TextView rightClockView = findViewById(R.id.clockRight);
        long startTime = 5L * 60L;

        PlayerClock leftClock = new PlayerClock(startTime, leftClockView).start().pause().showStartTime();
        PlayerClock rightClock = new PlayerClock(startTime, rightClockView).start().pause().showStartTime();

        gyroscope = new Gyroscope(this);

        gyroscope.setListener((rx, ry, rz) -> {
            if (rz > 0.5f) {
                leftClock.pause();
                rightClock.start();
            }
            else if (rz < -0.5f) {
                leftClock.start ();
                rightClock.pause();
            }
        });
    }

    // create on resume method
    @Override
    protected void onResume() {
        super.onResume();
        gyroscope.register();
    }

    // create on pause method
    @Override
    protected void onPause() {
        super.onPause();
        gyroscope.unregister();
    }

}
