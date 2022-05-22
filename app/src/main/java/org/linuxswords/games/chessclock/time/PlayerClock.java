package org.linuxswords.games.chessclock.time;

import android.graphics.Color;
import android.widget.TextView;

public class PlayerClock
{
    public static final int BACKGROUND_COLOR = Color.parseColor("#403E3E");
    private final PausableCountDownTimer countDownTimer;
    private final TextView view;
    private final long startTimeInMillis;

    public PlayerClock(long initialMilliseconds, TextView view)
    {
        this.startTimeInMillis = initialMilliseconds;
        this.view = view;
        countDownTimer = new PausableCountDownTimer(initialMilliseconds)
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
        this.view.setBackgroundColor(Color.WHITE);
        this.view.setTextColor(BACKGROUND_COLOR);
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
        this.view.setBackgroundColor(BACKGROUND_COLOR);
        this.view.setTextColor(Color.WHITE);
        return this;
    }
}
