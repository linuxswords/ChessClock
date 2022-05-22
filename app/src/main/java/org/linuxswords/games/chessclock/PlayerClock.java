package org.linuxswords.games.chessclock;

import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class PlayerClock
{ private final PausableCountDownTimer countDownTimer;
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
                String timeText = TimeFormatter.convertMillisIntoDisplayableTime(millisUntilFinished);
                view.setText(timeText);
            }

            @Override
            public void onTimerFinish()
            {
                view.setText("ur dead");

            }
        };
    }


    public PlayerClock showStartTime()
    {
        this.view.setText(TimeFormatter.convertMillisIntoDisplayableTime(this.startTimeInMillis));
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
