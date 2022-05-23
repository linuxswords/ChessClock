package org.linuxswords.games.chessclock;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import org.linuxswords.games.chessclock.TiltSensor.TiltListener;
import org.linuxswords.games.chessclock.time.PlayerClock;

public class MainActivity extends Activity implements TiltListener
{
    private static final String TAG = "MainActivity";
    private int currentTiltDegree = 0;

    private PlayerClock leftClock;
    private PlayerClock rightClock;

    private MediaPlayer mediaPlayer;

    private boolean isSilent = true;
    private TiltSensor tiltSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView leftClockView = findViewById(R.id.clockLeft);
        TextView rightClockView = findViewById(R.id.clockRight);

        leftClock = new PlayerClock(leftClockView).showStartTime();
        rightClock = new PlayerClock(rightClockView).showStartTime();

        tiltSensor = new TiltSensor(this);
        tiltSensor.setListener(this);

        // pause on a single click, show settings on a long click
        findViewById(R.id.parent).setOnClickListener(v -> this.pauseAllClocks());
        findViewById(R.id.parent).setOnLongClickListener(v -> this.showSettingsScreen());
//        findViewById(R.id.parent).setdouOnLongClickListener(v -> this.showSettingsScreen());

        // reset button
//        findViewById(R.id.restartButton).setOnClickListener(v -> this.restartAllClocks());

        // sound stuff
        initializeSoundTriggers();
    }

    private boolean showSettingsScreen(){
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
    }

    private void initializeSoundTriggers()
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.punch);
        mediaPlayer.setLooping(false);
    }

    private void toggleSwitch(PlayerClock toActivate, PlayerClock toPause)
    {
        toActivate.start();
        toPause.pause();
        triggerSound();
    }

    private void triggerSound(){
        if (!isSilent) {
            mediaPlayer.start();
        }
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
        tiltSensor.register();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        tiltSensor.unregister();
    }

    @Override
    public void onTilt(int degree)
    {
        if (degree != 0) {
            if (currentTiltDegree == 0) {
                currentTiltDegree = degree;
            }
            else if (Math.signum(currentTiltDegree) != Math.signum(degree)) {
                currentTiltDegree = degree;
                Log.d(TAG, "tilted from " + currentTiltDegree + " to " + degree);
                if (Math.signum(currentTiltDegree) == -1) {
                    toggleSwitch(leftClock, rightClock);
                }
                else {
                    toggleSwitch(rightClock, leftClock);
                }
            }
        }

    }
}
