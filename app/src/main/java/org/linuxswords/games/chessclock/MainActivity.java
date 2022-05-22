package org.linuxswords.games.chessclock;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import org.linuxswords.games.chessclock.time.PlayerClock;
import org.linuxswords.games.chessclock.time.TimeSettingsManager;

public class MainActivity extends Activity
{
    private Gyroscope gyroscope;
    private PlayerClock leftClock;
    private PlayerClock rightClock;
    private final TimeSettingsManager timeSettingsManager = TimeSettingsManager.instance();
    private MediaPlayer mediaPlayer;

    private boolean isSilent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView leftClockView = findViewById(R.id.clockLeft);
        TextView rightClockView = findViewById(R.id.clockRight);

        leftClock = new PlayerClock(timeSettingsManager.getCurrent().minutesAsMilliSeconds(), leftClockView).showStartTime();
        rightClock = new PlayerClock(timeSettingsManager.getCurrent().minutesAsMilliSeconds(), rightClockView).showStartTime();

        gyroscope = new Gyroscope(this);
        gyroscope.setListener(this::clockHasTilted);

        // pause button
        findViewById(R.id.pauseButton).setOnClickListener(v -> this.pauseAllClocks());

        // reset button
        findViewById(R.id.restartButton).setOnClickListener(v -> this.restartAllClocks());

        // sound stuff
        initializeSoundTriggers();

        // settings
        findViewById(R.id.settingsButton).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

    }

    private void initializeSoundTriggers()
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.punch);
        mediaPlayer.setLooping(false);
        findViewById(R.id.soundToggleButton).setOnClickListener(button -> isSilent = !isSilent);
    }

    private void clockHasTilted(float rx, float ry, float rz)
    {
        if (rz > 0.5f) {
            leftClock.pause();
            rightClock.start();
            triggerSound();
        }
        else if (rz < -0.5f) {
            leftClock.start();
            rightClock.pause();
            triggerSound();
        }
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
        gyroscope.register();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gyroscope.unregister();
    }

}
