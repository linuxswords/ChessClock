package org.linuxswords.games.chessclock;

import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class PlayerClock
{
    public static final String DISPLAY_TIME_FORMAT = "%d:%d";  // make this configurable
    private final PausableCountDownTimer countDownTimer;
    private final TextView view;
    private final long startTimeInMillis;

    public PlayerClock(long initialSeconds, TextView view)
    {
        this.startTimeInMillis = initialSeconds * 1_000L;
        this.view = view;
        countDownTimer = new PausableCountDownTimer(initialSeconds * 1_000L)
        {

            @Override
            public void onTimerTick(Long millisUntilFinished)
            {
                String timeText = convertMillisIntoDisplayableTime(millisUntilFinished);
                view.setText(timeText);
            }

            @Override
            public void onTimerFinish()
            {
                view.setText("ur dead");

            }
        };
    }

    private static String convertMillisIntoDisplayableTime(Long millisUntilFinished)
    {
        return String.format(DISPLAY_TIME_FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
        );
    }

    public PlayerClock showStartTime()
    {
        this.view.setText(convertMillisIntoDisplayableTime(this.startTimeInMillis));
        return this;
    }

    public PlayerClock start()
    {
        this.countDownTimer.start();
        return this;
    }

    public PlayerClock restart()
    {
        this.countDownTimer.restart();
        return this;
    }

    public PlayerClock pause()
    {
        this.countDownTimer.pause();
        return this;
    }
}
